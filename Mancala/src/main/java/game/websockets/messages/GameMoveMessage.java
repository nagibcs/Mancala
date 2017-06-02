package game.websockets.messages;

public class GameMoveMessage implements Pushable
{

	private long gameId;
	private int pitId;
	private boolean canPlay;
	private int score;
	private boolean winner;
	private boolean tie;

	public long getGameId()
	{
		return gameId;
	}

	public void setGameId(long gameId)
	{
		this.gameId = gameId;
	}

	public int getPitId()
	{
		return pitId;
	}

	public void setPitId(int pitId)
	{
		this.pitId = pitId;
	}

	public boolean isCanPlay()
	{
		return canPlay;
	}

	public void setCanPlay(boolean canPlay)
	{
		this.canPlay = canPlay;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public boolean isWinner()
	{
		return winner;
	}

	public void setWinner(boolean winner)
	{
		this.winner = winner;
	}

	public boolean isTie()
	{
		return tie;
	}

	public void setTie(boolean tie)
	{
		this.tie = tie;
	}

	@Override
	public String getType()
	{
		return "Move";
	}
}
