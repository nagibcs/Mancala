package game.engine;

import game.engine.Board.BoardSide;

public abstract class AbstractPit
{
	private final BoardSide owner;
	private AbstractPit oppositePit;
	protected int seeds;
	
	public AbstractPit(BoardSide owner, int seeds)
	{
		this.owner = owner;
		this.seeds = seeds;
	}

	public int getSeeds()
	{
		return seeds;
	}
	
	public BoardSide getOwner()
	{
		return owner;
	}
	
	public void setOppositePit(AbstractPit oppositePit)
	{
		this.oppositePit = oppositePit;
	}
	
	public AbstractPit getOppositePit()
	{
		return oppositePit;
	}
	
	public int takeSeed(int seeds, BoardSide seeder)
	{
		if (seeds > 0)
		{
			this.seeds++;
			seeds--;
		}
		return seeds;
	}

	public abstract void takeAllSeeds(int seeds, BoardSide seeder);
	
	public abstract int empty();
	
	@Override
	public String toString()
	{
		return new StringBuilder().append("{seeds:").append(seeds).append(", owner: ").append(owner).append('}').toString();
	}

}
