package game.engine;

import game.engine.Board.BoardSide;
import game.exceptions.IllegalMoveException;

public class Game
{
	private final Player firstPlayer;
	private final Player secondPlayer;
	private final Board board;
	
	private Player turn;
	private Player winner;
	private boolean tie;
	
	public Game(Player firstPlayer, Player secondPlayer, Board board)
	{
		if (firstPlayer == null || secondPlayer == null)
			throw new IllegalArgumentException("firstPlayer and secondPlayer can not be null");
		
		this.board = board;
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.turn = firstPlayer;
	}

	public void playPit(Player player, int pit) throws IllegalMoveException
	{
		validatePlayerTurn(player);
		
		BoardSide lastPitBoardSide = board.playPit(player.getBoardSide(), pit);
		
		if (isGameOver())
		{
			turn = null;
			calculateWinner();
		}
		else
		{
			changeTurn(lastPitBoardSide);
		}
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	public Player getFirstPlayer()
	{
		return firstPlayer;
	}
	
	public Player getSecondPlayer()
	{
		return secondPlayer;
	}
	
	public boolean isTie()
	{
		return tie;
	}
	
	public Player getWinner()
	{
		return winner;
	}
	
	public Player getTurn()
	{
		return turn;
	}
	
	public void setTurn(Player turn)
	{
		this.turn = turn;
	}
	
	public boolean isGameOver()
	{
		return !board.hasSeeds(firstPlayer.getBoardSide()) || !board.hasSeeds(secondPlayer.getBoardSide());
	}
	
	public int getScore(Player player)
	{
		if (!isGameOver())
			return 0;
		return board.getKalahSeedsCount(player.getBoardSide());
	}

	private void calculateWinner()
	{
		board.moveAllSeedsToKalahs();
		int score1 = getScore(firstPlayer);
		int score2 = getScore(secondPlayer);
		if (score1 > score2)
			winner = firstPlayer;
		else if (score2 > score1)
			winner = secondPlayer;
		else
			tie = true;
	}
	
	private void changeTurn(BoardSide boardSide)
	{
		if (boardSide != turn.getBoardSide())
			turn = firstPlayer.equals(turn) ? secondPlayer : firstPlayer;
	}
	
	private void validatePlayerTurn(Player player) throws IllegalMoveException
	{
		if (!player.equals(turn))
			throw new IllegalMoveException("This should be turn of " + player.getUser().getUsername());
	}
}
