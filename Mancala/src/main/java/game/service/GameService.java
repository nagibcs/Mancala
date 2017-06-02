package game.service;

import game.engine.Game;
import game.model.User;
import game.websockets.messages.GameConfiguration;

public interface GameService
{

	public GameConfiguration findGameForUser(User user);

	public Game getGame(long gameId);

	public void endGame(long gameId);

}
