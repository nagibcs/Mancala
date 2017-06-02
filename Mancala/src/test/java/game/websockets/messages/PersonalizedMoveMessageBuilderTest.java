package game.websockets.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.engine.Board.BoardSide;
import game.engine.Player;
import game.model.User;
import game.websockets.messages.GameMoveMessage;

public class PersonalizedMoveMessageBuilderTest {
	
	private Player player1;
	private Player player2;
	
	@Before
	public void setup() {
		player1 = new Player(new User(100, "user1"), BoardSide.SOUTH);
		player2 = new Player(new User(145, "user2"), BoardSide.NORTH);
	}
	
	@Test
	public void testCreateReplyMessage_noWinners_sameTurn() {
		GameMoveMessage message = PersonalizedMoveMessageBuilder.replyTo(1, player1)
			.turn(player1)
			.winner(null)
			.score(0)
			.build();
		
		assertFalse(message.isWinner());
		assertTrue(message.isCanPlay());
		assertEquals(0, message.getScore());
		assertEquals(-1, message.getPitId());
		assertEquals(1, message.getGameId());
	}
	
	@Test
	public void testCreateReplyMessage_noWinners_changeTurn() {
		GameMoveMessage message = PersonalizedMoveMessageBuilder.replyTo(1, player1)
			.turn(player2)
			.winner(null)
			.score(0)
			.build();
		
		assertFalse(message.isWinner());
		assertFalse(message.isCanPlay());
		assertEquals(0, message.getScore());
		assertEquals(-1, message.getPitId());
		assertEquals(1, message.getGameId());
	}
	
	@Test
	public void testCreatePushMessage_noWinners_sameTurn() {
		GameMoveMessage message = PersonalizedMoveMessageBuilder.pushTo(1, player1, 2)
			.turn(player1)
			.winner(null)
			.score(0)
			.build();
		
		assertFalse(message.isWinner());
		assertTrue(message.isCanPlay());
		assertEquals(0, message.getScore());
		assertEquals(2, message.getPitId());
		assertEquals(1, message.getGameId());
	}
	
	@Test
	public void testCreatePushMessage_noWinners_changeTurn() {
		GameMoveMessage message = PersonalizedMoveMessageBuilder.pushTo(1, player1, 3)
			.turn(player2)
			.winner(null)
			.score(0)
			.build();
		
		assertFalse(message.isWinner());
		assertFalse(message.isCanPlay());
		assertEquals(0, message.getScore());
		assertEquals(3, message.getPitId());
		assertEquals(1, message.getGameId());
	}
	
	@Test
	public void testCreateReplyhMessage_winner() {
		GameMoveMessage message = PersonalizedMoveMessageBuilder.replyTo(1, player1)
			.turn(player1)
			.winner(player1)
			.score(20)
			.build();
		
		assertTrue(message.isWinner());
		assertFalse(message.isCanPlay());
		assertEquals(20, message.getScore());
		assertEquals(-1, message.getPitId());
		assertEquals(1, message.getGameId());
	}
	
	@Test
	public void testCreateReplyhMessage_loser() {
		GameMoveMessage message = PersonalizedMoveMessageBuilder.replyTo(1, player1)
			.turn(null)
			.winner(player2)
			.score(20)
			.build();
		
		assertFalse(message.isWinner());
		assertFalse(message.isCanPlay());
		assertEquals(20, message.getScore());
		assertEquals(-1, message.getPitId());
		assertEquals(1, message.getGameId());
	}
}
