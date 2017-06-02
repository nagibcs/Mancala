app.controller('BoardController', [
		'$scope',
		'$window',
		function($scope, $window) {
			$scope.SocketConfig = {
					SOCKET_PATH: '/game',
					PLAY_QUEUE: '/user/topic/play',
					SEND_PATH: '/app/game'
			}
			
			$scope.MessageType = {
					CONFIG_MESSAGE_TYPE: 'GameConfig',
					MOVE_MESSAGE_TYPE: 'Move'
			}
			
			$scope.board = {};
			$scope.board.pits = new Array(2);
			$scope.initialized = false;
			$scope.score = 0;
			
			$scope.restart = function() {
				$window.location.reload();
			}
			
			$scope.socket = new SockJS($scope.SocketConfig.SOCKET_PATH);
			$scope.stompClient = Stomp.over($scope.socket);
			$scope.stompClient.connect({}, function(frame) {
				console.log('Connected: ' + frame);
				$scope.stompClient.subscribe($scope.SocketConfig.PLAY_QUEUE, function(response) {
					var obj =JSON.parse(response.body);
					if (obj.type == $scope.MessageType.CONFIG_MESSAGE_TYPE)
						$scope.initBoard(obj);
					else if (obj.type == $scope.MessageType.MOVE_MESSAGE_TYPE) {
						if (obj.pitId > -1)
							$scope.sow($scope.board.pits[1][obj.pitId], false);
						$scope.updateStatus(obj);
					}
					$scope.$apply();
				});
			});

			$scope.initBoard = function(boardConfig) {
				$scope.gameId = boardConfig.gameId;
				$scope.pitsPerPlayer = boardConfig.pitsPerPlayer+1;
				$scope.bigPitIndex = $scope.pitsPerPlayer - 1;
				$scope.initialSeeds = boardConfig.initialSeeds;
				$scope.turn = boardConfig.startPlayer ? 0 : 1;
				var pits = $scope.board.pits;
				for (var i = 0; i < $scope.board.pits.length; i++) {
					pits[i] = new Array($scope.pitsPerPlayer);
					for (var j = 0; j < pits[i].length-1; j++)
						pits[i][j] = new Pit(j, i, $scope.initialSeeds);
					pits[i][$scope.bigPitIndex] = new Pit(j, i, 0);
				}
				$scope.initialized = true;
			}
			
			$scope.getCurrentPlayerPits = function() {
				return $scope.board.pits[0];
			}
			
			$scope.getOpponentPlayerPits = function() {
				return $scope.board.pits[1];
			}
			
			$scope.updateStatus = function(message) {
				
				if (message.score > 0) {
					$scope.finished = true;
					$scope.score = message.score;
					$scope.isWinner = message.winner;
					$scope.tie = message.tie;
					$scope.locked = true;
					return;
				}
				
				if (message.canPlay == true)
					$scope.turn = 0;
				else
					$scope.turn = 1;
				
				if ($scope.locked == true && message.pitId < 0)
					$scope.locked = false;
				
			}
			
			$scope.locked = false;
			$scope.sow = function(startPit, remoteUpdate) {
				
				if ($scope.locked == true)
					return;
				
				if ($scope.turn != startPit.player)
					return;
				
				if (!$scope.isValidPit(startPit))
					return;
				
				var startPlayer = startPit.player;
				var oppPlayer = (startPit.player + 1) % 2;
				var seeds = startPit.empty();

				var currentPlayer = startPlayer;
				var currentPitIndex = startPit.index + 1;
				var currentPits = $scope.board.pits[startPit.player];
				var candidateForCapture = -1;
				while (seeds > 0) {
					// sow seeds to the right one by one
					while (currentPitIndex < currentPits.length && seeds > 0) {
						var currentPit = currentPits[currentPitIndex];
						if (currentPit.player == startPlayer || currentPitIndex != $scope.bigPitIndex) {
							currentPit.addSeeds(1);
							--seeds;

							if (currentPit.player == startPlayer && currentPit.seeds == 1 && currentPitIndex != $scope.bigPitIndex)
								candidateForCapture = currentPitIndex;
							else
								candidateForCapture = -1;
						}
						++currentPitIndex;
					}

					currentPlayer = (currentPlayer + 1) % 2;
					currentPits = $scope.board.pits[currentPlayer];
					currentPitIndex = 0;
				}

				$scope.capture(candidateForCapture, startPlayer, oppPlayer);
				
				if (remoteUpdate == true) {
					$scope.stompClient.send($scope.SocketConfig.SEND_PATH, {}, JSON.stringify(new Move($scope.gameId, startPit.index)));
					$scope.locked = true;
				}
			}
			
			$scope.capture = function(pitIndexToCapture, startPlayer, oppPlayer) {
				if (pitIndexToCapture < 0)
					return;
				var startPlayerPits = $scope.board.pits[startPlayer]; 
				var seeds = startPlayerPits[pitIndexToCapture].empty();

				var oppPits = $scope.board.pits[oppPlayer];
				var oppPitIndex = oppPits.length - 2 - pitIndexToCapture;
				seeds += oppPits[oppPitIndex].empty();

				startPlayerPits[$scope.bigPitIndex].addSeeds(seeds);
			}
			
			$scope.isValidPit = function(pit) {
				if (pit.index != $scope.bigPitIndex && pit.seeds > 0)
					return true;
				return false;
			}
			
			$scope.play = function(pit, remoteUpdate) {
				$scope.sow(pit, remoteUpdate);
			}
			
			$scope.getMessage = function() {
				
				if ($scope.finished == true) {
					if ($scope.isWinner == true)
						return 'Congrats! you scored ' + $scope.score;
					if ($scope.tie == true)
						return 'It is a tie!';
					return 'Game Over! you scored ' + $scope.score;
				}
				
				if ($scope.turn == 0)
					return 'Your turn!';
				else
					return "Opponent's turn";
			}

		} ]);

var Pit = function(index, player, initialSeeds) {
	this.index = index;
	this.player = player;
	this.seeds = initialSeeds;
};

Pit.prototype = {
	getId : function() {
		return (this.player * 10 + this.index);
	},
	getIndex: function() {
		return this.index;
	},
	addSeeds : function(seeds) {
		this.seeds += seeds;
	},
	empty : function() {
		var seeds = this.seeds;
		this.seeds = 0;
		return seeds;
	}
};

var Move = function(gameId, pitId) {
	this.gameId = gameId;
	this.pitId = pitId;
}