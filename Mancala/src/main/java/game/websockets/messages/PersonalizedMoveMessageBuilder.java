package game.websockets.messages;

import game.engine.Player;

public class PersonalizedMoveMessageBuilder
{
	private final Player player;

	private GameMoveMessage message = new GameMoveMessage();

	public static PersonalizedMoveMessageBuilder replyTo(long gameId, Player player)
	{
		return new PersonalizedMoveMessageBuilder(gameId, player, -1);
	}

	public static PersonalizedMoveMessageBuilder pushTo(long gameId, Player player, int pitId)
	{
		return new PersonalizedMoveMessageBuilder(gameId, player, pitId);
	}

	private PersonalizedMoveMessageBuilder(long gameId, Player player, int pitId)
	{
		this.player = player;
		message.setGameId(gameId);
		message.setPitId(pitId);
	}

	public PersonalizedMoveMessageBuilder turn(Player user)
	{
		if (checkPlayer(user))
			message.setCanPlay(true);
		return this;
	}

	public PersonalizedMoveMessageBuilder winner(Player user)
	{
		if (checkPlayer(user))
		{
			message.setWinner(true);
			message.setCanPlay(false);
		}
		return this;
	}

	public PersonalizedMoveMessageBuilder score(int score)
	{
		message.setScore(score);
		return this;
	}

	private boolean checkPlayer(Player player)
	{
		return player != null && this.player.equals(player);
	}

	public GameMoveMessage build()
	{
		return message;
	}

}
