package game.engine;

import game.engine.Board.BoardSide;

public class Pit extends AbstractPit
{
	public Pit(BoardSide owner, int seeds)
	{
		super(owner, seeds);
	}

	@Override
	public int empty()
	{
		int result = this.seeds;
		this.seeds = 0;
		return result;
	}

	@Override
	public void takeAllSeeds(int seeds, BoardSide seeder)
	{
	}
	
}
