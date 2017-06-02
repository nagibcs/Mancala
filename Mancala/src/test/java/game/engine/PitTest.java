package game.engine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game.engine.Board.BoardSide;

public class PitTest
{
	@Test
	public void testTakeSeedSameOwner()
	{
		Pit pit = new Pit(BoardSide.NORTH, 6);
		
		int remainder = pit.takeSeed(5, BoardSide.NORTH);
		
		assertEquals(4, remainder);
		assertEquals(7, pit.getSeeds());
	}
	
	@Test
	public void testTakeSeedDifferentOwner()
	{
		Pit pit = new Pit(BoardSide.NORTH, 6);
		
		int remainder = pit.takeSeed(5, BoardSide.SOUTH);
		
		assertEquals(4, remainder);
		assertEquals(7, pit.getSeeds());
	}
	
	@Test
	public void testEmpty()
	{
		Pit pit = new Pit(BoardSide.NORTH, 6);
		
		assertEquals(6, pit.empty());
		assertEquals(0, pit.getSeeds());
	}
	
	@Test
	public void testTakeAllSeedsSameOwner()
	{
		Pit pit = new Pit(BoardSide.NORTH, 6);
		
		pit.takeAllSeeds(8, BoardSide.NORTH);
		
		assertEquals(6, pit.getSeeds());
	}
	
	@Test
	public void testTakeAllSeedsDifferentOwner()
	{
		Pit pit = new Pit(BoardSide.NORTH, 6);
		
		pit.takeAllSeeds(8, BoardSide.SOUTH);
		
		assertEquals(6, pit.getSeeds());
	}
}
