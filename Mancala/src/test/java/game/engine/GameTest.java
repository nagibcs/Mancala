package game.engine;

import static game.engine.TestUtil.createPitFactory;
import static game.engine.TestUtil.createPlayer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.engine.Board.BoardSide;
import game.exceptions.IllegalMoveException;

public class GameTest
{
	private Player player1;
	private Player player2;
	
	@Before
	public void setup()
	{
		player1 = createPlayer(1, BoardSide.SOUTH);
		player2 = createPlayer(2, BoardSide.NORTH);
	}
	
	@Test
	public void testPlayPit() throws IllegalMoveException {
		Game game = new Game(player1, player2, new PitFactoryImpl(6, 6));
		
		game.playPit(player1, 0);
		assertEquals(player1, game.getTurn());
		assertNull(game.getWinner());
		assertFalse(game.isGameOver());
		assertFalse(game.isTie());
		assertEquals(0, game.getScore(player1));
		assertEquals(0, game.getScore(player2));
		
		
		game.playPit(player1, 1);
		assertEquals(player2, game.getTurn());
		assertNull(game.getWinner());
		assertFalse(game.isGameOver());
		assertFalse(game.isTie());
		assertEquals(0, game.getScore(player1));
		assertEquals(0, game.getScore(player2));
		
		game.playPit(player2, 5);
		assertEquals(player1, game.getTurn());
		assertNull(game.getWinner());
		assertFalse(game.isGameOver());
		assertFalse(game.isTie());
		assertEquals(0, game.getScore(player1));
		assertEquals(0, game.getScore(player2));
	}
	
	@Test
	public void testPlayEmptyPit() throws IllegalMoveException 
	{
		Game game = createGame(new Integer[]{0, 0, 0, 0, 0, 2, 40}, new Integer[]{4, 4, 8, 0, 0, 2, 12});
		game.playPit(player1, 4);
		
		assertEquals(player1, game.getTurn());
		assertNull(game.getWinner());
		assertFalse(game.isGameOver());
		assertFalse(game.isTie());
		assertEquals(0, game.getScore(player1));
		assertEquals(0, game.getScore(player2));
	}
	
	
	@Test
	public void testWin_player1() throws IllegalMoveException 
	{
		Game game = createGame(new Integer[]{0, 0, 0, 0, 0, 2, 40}, new Integer[]{4, 4, 8, 0, 0, 2, 12});
		game.playPit(player1, 5);
		
		assertNull(game.getTurn());
		assertTrue(game.isGameOver());
		assertFalse(game.isTie());
		assertEquals(player1, game.getWinner());
		assertEquals(41, game.getScore(player1));
		assertEquals(31, game.getScore(player2));
	}
	
	@Test
	public void testWin_player2() throws IllegalMoveException 
	{
		Game game = createGame(new Integer[]{4, 4, 8, 0, 0, 2, 12}, new Integer[]{0, 0, 0, 0, 0, 2, 40});
		game.setTurn(player2);
		game.playPit(player2, 5);
		
		assertNull(game.getTurn());
		assertTrue(game.isGameOver());
		assertFalse(game.isTie());
		assertEquals(player2, game.getWinner());
		assertEquals(31, game.getScore(player1));
		assertEquals(41, game.getScore(player2));
	}
	
	@Test
	public void testTie() throws IllegalMoveException 
	{
		Game game = createGame(new Integer[]{0, 0, 0, 0, 0, 1, 10}, new Integer[]{1, 0, 2, 0, 2, 0, 6});
		game.playPit(player1, 5);
		
		assertNull(game.getTurn());
		assertTrue(game.isGameOver());
		assertNull(game.getWinner());
		assertTrue(game.isTie());
		assertEquals(11, game.getScore(player1));
		assertEquals(11, game.getScore(player2));
	}
	
	@Test(expected = IllegalMoveException.class)
	public void testInvalidMove_invalidTurn() throws IllegalMoveException
	{
		Game game = new Game(player1, player2, new PitFactoryImpl(6, 6));
		game.playPit(player2, 0);
	}
	
	private Game createGame(Integer[] downSidePits, Integer[] upSidePits)
	{
		return new Game(player1, player2, createPitFactory(downSidePits, upSidePits));
	}
}
