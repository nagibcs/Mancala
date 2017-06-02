package game.engine;

import java.util.Objects;

import game.engine.Board.BoardSide;
import game.model.User;

public class Player
{
	private final User user;
	private final BoardSide boardSide;
	
	public Player(User user, BoardSide boardSide)
	{
		this.user = user;
		this.boardSide = boardSide;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public BoardSide getBoardSide()
	{
		return boardSide;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		
		if (!(obj instanceof Player))
			return false;
		
		Player other = (Player) obj;
		
		return Objects.equals(user, other.user) && Objects.equals(boardSide, other.boardSide);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(user, boardSide.toString());
	}
}
