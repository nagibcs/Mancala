package game.engine;

import game.engine.Board.BoardSide;

public class Kalah extends AbstractPit
{
	public Kalah(BoardSide owner, int seeds)
	{
		super(owner, seeds);
	}

	@Override
	public int empty()
	{
		return 0;
	}

	@Override
	public void takeAllSeeds(int seeds, BoardSide seeder)
	{
		if (getOwner() == seeder)
			this.seeds += seeds;
	}

	@Override
	public int takeSeed(int seeds, BoardSide seeder)
	{
		if (getOwner() == seeder)
			return super.takeSeed(seeds, seeder);
		return seeds;
	}
}
