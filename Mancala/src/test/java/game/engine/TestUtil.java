package game.engine;

import static org.junit.Assert.assertEquals;

import game.engine.Board.BoardSide;
import game.model.User;

public class TestUtil
{
	public static void assertSeedsCountsEqual(int[] seedsCounts, Board board, BoardSide boardSide)
	{
		for (int i=0 ; i < seedsCounts.length-1 ; i++)
		{
			int actual = board.getSeedsCount(boardSide, i);
			assertEquals(
					String.format("Expected %d for pit %d of side %s but actual is %d", seedsCounts[i], i, boardSide, actual),
					seedsCounts[i], 
					actual);
		}
		int actual = board.getKalahSeedsCount(boardSide);
		assertEquals(
				String.format("Expected %d for kalah of side %s but actual is %d", seedsCounts[seedsCounts.length-1], boardSide, actual),  
				seedsCounts[seedsCounts.length-1], 
				actual);
	}
	
	public static Player createPlayer(int id, BoardSide boardSide)
	{
		User user = new User(id, "U" + id);
		return new Player(user, boardSide);
	}
	
}
