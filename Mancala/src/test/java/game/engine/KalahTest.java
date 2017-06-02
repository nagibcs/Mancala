package game.engine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game.engine.Board.BoardSide;

public class KalahTest
{
	@Test
	public void testTakeSeedSameOwner()
	{
		Kalah pit = new Kalah(BoardSide.NORTH, 6);
		
		int remainder = pit.takeSeed(5, BoardSide.NORTH);
		
		assertEquals(4, remainder);
		assertEquals(7, pit.getSeeds());
	}
	
	@Test
	public void testTakeSeedDifferentOwner()
	{
		Kalah pit = new Kalah(BoardSide.NORTH, 6);
		
		int remainder = pit.takeSeed(5, BoardSide.SOUTH);
		
		assertEquals(5, remainder);
		assertEquals(6, pit.getSeeds());
	}
	
	@Test
	public void testEmpty()
	{
		Kalah pit = new Kalah(BoardSide.NORTH, 6);
		
		assertEquals(0, pit.empty());
		assertEquals(6, pit.getSeeds());
	}
	
	@Test
	public void testTakeAllSeedsSameOwner()
	{
		Kalah pit = new Kalah(BoardSide.NORTH, 6);
		
		pit.takeAllSeeds(8, BoardSide.NORTH);
		
		assertEquals(14, pit.getSeeds());
	}
	
	@Test
	public void testTakeAllSeedsDifferentOwner()
	{
		Kalah pit = new Kalah(BoardSide.NORTH, 6);
		
		pit.takeAllSeeds(8, BoardSide.SOUTH);
		
		assertEquals(6, pit.getSeeds());
	}
}
