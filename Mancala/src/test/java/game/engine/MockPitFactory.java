package game.engine;

import java.util.Map;

import game.engine.Board.BoardSide;

public class MockPitFactory implements PitFactory
{
	private final int pitsPerSide;
	private final Map<BoardSide, Integer[]> initialSeeds;
	
	public MockPitFactory(int pitsPerSide, Map<BoardSide, Integer[]> initialSeeds)
	{
		this.pitsPerSide = pitsPerSide;
		this.initialSeeds = initialSeeds;
	}

	@Override
	public AbstractPit createPit(int index, BoardSide boardSide)
	{
		return new Pit(boardSide, initialSeeds.get(boardSide)[index]);
	}

	@Override
	public AbstractPit createKalah(BoardSide boardSide)
	{
		return new Kalah(boardSide, initialSeeds.get(boardSide)[pitsPerSide]);
	}

	@Override
	public int getPitsCountPerSide()
	{
		return pitsPerSide;
	}

}
