package game.service.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;

import org.springframework.stereotype.Service;

import game.engine.Board;
import game.engine.Board.BoardSide;
import game.engine.Game;
import game.engine.Player;
import game.model.User;
import game.service.GameService;
import game.websockets.messages.GameConfiguration;

@Service
@Singleton
public class InMemoryGameService implements GameService
{
	private static final int INITIAL_SEEDS = 6;
	private static final int PITS_PER_PLAYER = 6;
	
	private final Map<Long, Game> runningGames = new ConcurrentHashMap<>();
	
	private User waitingUser;
	private long lastGameId = 0;

	@Override
	public GameConfiguration findGameForUser(User user)
	{
		GameConfiguration result = findGameConfiguration(user);
		if (result != null)
			return result;
		
		return createConfiguration(user);
	}

	@Override
	public Game getGame(long gameId)
	{
		return runningGames.get(gameId);
	}

	@Override
	public void endGame(long gameId)
	{
		runningGames.remove(gameId);
	}
	
	private GameConfiguration findGameConfiguration(User user)
	{
		GameConfiguration result = null;
		long runningGameId = searchRunningGames(user);
		if (runningGameId > 0)
		{
			Player currentPlayer = runningGames.get(runningGameId).getTurn();
			boolean isCurrentPlayer = currentPlayer != null && currentPlayer.getUser().equals(user);
			result = new GameConfiguration(runningGameId, INITIAL_SEEDS, PITS_PER_PLAYER, isCurrentPlayer);
		}
		return result;
	}
	
	private synchronized GameConfiguration createConfiguration(User user)
	{
		Game game = null;
		if (waitingUser == null) //first player
		{
			waitingUser = user;
			lastGameId++;
		}
		else //second player
		{
			game = new Game(
					new Player(waitingUser, BoardSide.SOUTH), 
					new Player(user, BoardSide.NORTH), 
					Board.create(PITS_PER_PLAYER, INITIAL_SEEDS));
			runningGames.put(lastGameId, game);
			
			waitingUser = null;
		}
		
		return new GameConfiguration(lastGameId, PITS_PER_PLAYER, INITIAL_SEEDS, game == null);
	}
	
	private long searchRunningGames(User user)
	{
		for (Entry<Long, Game> entry : runningGames.entrySet()) 
		{
			Game game = entry.getValue();
			if (game.getFirstPlayer().getUser().getId() == user.getId() 
					|| game.getSecondPlayer().getUser().getId() == user.getId()) 
			{
				return entry.getKey();
			}
		}
		return 0;
	}
}
