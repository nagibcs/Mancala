package game.engine;

import static game.engine.TestUtil.assertSeedsCountsEqual;
import static game.engine.TestUtil.createPitFactory;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import game.engine.Board.BoardSide;

public class BoardTest
{
	@Test
	public void testPlayPit()
	{
		Board board = new Board(new PitFactoryImpl(6, 6));
		board.playPit(BoardSide.SOUTH, 0);
		
		assertSeedsCountsEqual(new int[]{0, 7, 7, 7, 7, 7, 1}, board, BoardSide.SOUTH);
		assertSeedsCountsEqual(new int[]{6, 6, 6, 6, 6, 6, 0}, board, BoardSide.NORTH);
	}
	
	@Test
	public void testCapture()
	{
		Board board = new Board(createPitFactory(
				new Integer[]{0, 4, 8, 0, 0, 8, 12}, 
				new Integer[]{2, 0, 0, 0, 0, 6, 20}));
		
		board.playPit(BoardSide.SOUTH, 5);
		
		assertSeedsCountsEqual(new int[]{0, 4, 8, 0, 0, 0, 21}, board, BoardSide.SOUTH);
		assertSeedsCountsEqual(new int[]{3, 1, 1, 1, 1, 0, 20}, board, BoardSide.NORTH);
	}
	
	@Test
	public void testMoveAllSeedsToKalahs()
	{
		Board board = new Board(createPitFactory(
				new Integer[]{0, 4, 8, 0, 0, 8, 12}, 
				new Integer[]{2, 0, 0, 0, 0, 6, 20}));
		
		board.moveAllSeedsToKalahs();
		
		assertSeedsCountsEqual(new int[]{0, 0, 0, 0, 0, 0, 32}, board, BoardSide.SOUTH);
		assertSeedsCountsEqual(new int[]{0, 0, 0, 0, 0, 0, 28}, board, BoardSide.NORTH);
	}
	
	@Test
	public void testHasSeeds()
	{
		Board board = new Board(createPitFactory(
				new Integer[]{0, 0, 0, 0, 0, 0, 12}, 
				new Integer[]{0, 0, 0, 0, 0, 4, 20}));
		
		assertFalse(board.hasSeeds(BoardSide.SOUTH));
		assertTrue(board.hasSeeds(BoardSide.NORTH));
		
		Board board2 = new Board(createPitFactory(
				new Integer[]{0, 0, 0, 5, 0, 0, 12}, 
				new Integer[]{0, 0, 0, 0, 0, 0, 20}));
		
		assertTrue(board2.hasSeeds(BoardSide.SOUTH));
		assertFalse(board2.hasSeeds(BoardSide.NORTH));
	}

}
