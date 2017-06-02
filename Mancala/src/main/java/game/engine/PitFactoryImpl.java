package game.engine;

import game.engine.Board.BoardSide;

public class PitFactoryImpl implements PitFactory
{
	private final int pitsCountPerSide;
	private final int initialSeeds;
	
	public PitFactoryImpl(int pitsCountPerSide, int initialSeeds)
	{
		this.pitsCountPerSide = pitsCountPerSide;
		this.initialSeeds = initialSeeds;
	}

	@Override
	public AbstractPit createPit(int index, BoardSide boardSide)
	{
		return new Pit(boardSide, initialSeeds);
	}

	@Override
	public AbstractPit createKalah(BoardSide boardSide)
	{
		return new Kalah(boardSide, 0);
	}

	@Override
	public int getPitsCountPerSide()
	{
		return pitsCountPerSide;
	}

}
