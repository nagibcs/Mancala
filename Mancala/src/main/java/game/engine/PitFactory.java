package game.engine;

import game.engine.Board.BoardSide;

public interface PitFactory
{
	AbstractPit createPit(int index, BoardSide boardSide);
	AbstractPit createKalah(BoardSide boardSide);
	int getPitsCountPerSide();
}
