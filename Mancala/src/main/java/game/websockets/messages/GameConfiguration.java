package game.websockets.messages;

public class GameConfiguration implements Pushable
{
	private final long gameId;
	private final boolean startPlayer;
	private final int initialSeeds;
	private final int pitsPerPlayer;

	public GameConfiguration(long gameId, int initialSeeds, int pitsPerPlayer, boolean startPlayer)
	{
		this.gameId = gameId;
		this.initialSeeds = initialSeeds;
		this.pitsPerPlayer = pitsPerPlayer;
		this.startPlayer = startPlayer;
	}

	public long getGameId()
	{
		return gameId;
	}

	public boolean isStartPlayer()
	{
		return startPlayer;
	}

	public int getInitialSeeds()
	{
		return initialSeeds;
	}

	public int getPitsPerPlayer()
	{
		return pitsPerPlayer;
	}

	@Override
	public String getType()
	{
		return "GameConfig";
	}
}
