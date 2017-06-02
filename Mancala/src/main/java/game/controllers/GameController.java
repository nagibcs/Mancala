package game.controllers;

import java.security.Principal;

import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import game.engine.Game;
import game.engine.Player;
import game.model.User;
import game.service.GameService;
import game.service.UserService;
import game.util.Pair;
import game.websockets.messages.GameConfiguration;
import game.websockets.messages.GameMoveMessage;
import game.websockets.messages.PersonalizedMoveMessageBuilder;
import game.websockets.messages.Pushable;

@Controller
@Singleton
public class GameController
{
	public static final String CHANNEL = "/game";
	public static final String PLAY_QUEUE = "/topic/play";

	@Autowired
	private GameService gameService;

	@Autowired
	private UserService userService;

	private SimpMessageSendingOperations messagingTemplate;

	@Autowired
	public GameController(SimpMessageSendingOperations messagingTemplate)
	{
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping(GameController.CHANNEL)
	@SendTo(PLAY_QUEUE)
	public void play(Message<Object> message, @Payload GameMoveMessage request) throws Exception
	{
		Principal user = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);

		Game game = gameService.getGame(request.getGameId());
		
		Pair<Player, Player> players = classifyPlayers(user.getName(), game.getFirstPlayer(), game.getSecondPlayer());
		
		Player current = players.getFirst();
		Player opponent = players.getSecod();
		
		game.playPit(current, request.getPitId());

		sendPlayMessage(
				current.getUser().getUsername(),
				PersonalizedMoveMessageBuilder
				.replyTo(request.getGameId(), current)
				.turn(game.getTurn())
				.winner(game.getWinner())
				.score(game.getScore(current))
				.build());

		sendPlayMessage(
				opponent.getUser().getUsername(),
				PersonalizedMoveMessageBuilder
				.pushTo(request.getGameId(), opponent, request.getPitId())
				.turn(game.getTurn())
				.winner(game.getWinner())
				.score(game.getScore(opponent))
				.build());
	}

	public void startGameForUser(String username)
	{
		User user = userService.findUser(username);
		GameConfiguration gameConfiguration = gameService.findGameForUser(user);
		sendPlayMessage(user.getUsername(), gameConfiguration);
	}

	public void sendPlayMessage(String user, Pushable message)
	{
		send(user, PLAY_QUEUE, message);
	}

	public void send(String user, String queue, Pushable message)
	{
		messagingTemplate.convertAndSendToUser(user, queue, message);
	}
	
	private Pair<Player, Player> classifyPlayers(String username, Player player1, Player player2)
	{
		Player firstPlayer = null;
		Player secondPlayer = null;
		if (username.equals(player1.getUser().getUsername()))
		{
			firstPlayer = player1;
			secondPlayer = player2;
		}
		else
		{
			firstPlayer = player2;
			secondPlayer = player1;
		}
		
		return new Pair<>(firstPlayer, secondPlayer);
	}
}
