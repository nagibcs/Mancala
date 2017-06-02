package game.engine;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Board
{
	public static enum BoardSide
	{
		NORTH, SOUTH
	}
	
	private final int pitsPerSide;
	private final List<AbstractPit> pits = new ArrayList<>();
	private final Map<BoardSide, AbstractPit> kalahs = new EnumMap<>(BoardSide.class);
	
	public Board(PitFactory pitFactory)
	{
		this.pitsPerSide = pitFactory.getPitsCountPerSide();
		addPitsForBoardSide(BoardSide.SOUTH, pitFactory);
		addPitsForBoardSide(BoardSide.NORTH, pitFactory);
		setOppositePits();
	}
	
	private void addPitsForBoardSide(BoardSide boardSide, PitFactory pitFactory)
	{
		for(int i=0 ; i < pitsPerSide ; i++)
			pits.add(pitFactory.createPit(i, boardSide));
		AbstractPit kalah = pitFactory.createKalah(boardSide);
		pits.add(kalah);
		kalahs.put(boardSide, kalah);
	}
	
	private void setOppositePits()
	{
		for (int i=0 ; i < pitsPerSide ; i++)
		{
			AbstractPit downPit = pits.get(i);
			AbstractPit upPit = pits.get(pits.size() - i - 2);
			downPit.setOppositePit(upPit);
			upPit.setOppositePit(downPit);
		}
	}
	
	public boolean hasSeeds(BoardSide boardSide)
	{
		CircularIterator it = new CircularIterator(boardSide, 0);
		for (int i=0 ; i < pitsPerSide ; i++)
		{
			if (it.next().getSeeds() > 0)
				return true;
		}
		return false;
	}
	
	public BoardSide playPit(BoardSide seeder, int pit)
	{
		validatePit(pit);
		AbstractPit lastPit = sow(seeder, pit);
		capture(lastPit, seeder);
		return lastPit.getOwner();
	}

	private AbstractPit sow(BoardSide boardSide, int pit)
	{
		Iterator<AbstractPit> pitsIterator = new CircularIterator(boardSide, pit);
		AbstractPit nextPit = pitsIterator.next();
		int seeds = nextPit.empty();
		while(seeds > 0) 
		{
			nextPit = pitsIterator.next();
			seeds = nextPit.takeSeed(seeds, boardSide);
		}
		return nextPit;
	}
	
	private void capture(AbstractPit pit, BoardSide seeder)
	{
		if (pit.getOwner() != seeder || pit.getSeeds() > 1)
			return;
		
		AbstractPit oppositePit = pit.getOppositePit();
		if (oppositePit != null)
		{
			int seeds = oppositePit.empty() + pit.empty();
			kalahs.get(seeder).takeAllSeeds(seeds, seeder);
		}
	}
	
	public void moveAllSeedsToKalahs()
	{
		for (AbstractPit pit : pits)
		{
			AbstractPit kalah = kalahs.get(pit.getOwner());
			kalah.takeAllSeeds(pit.empty(), pit.getOwner());
		}
	}
	
	public int getKalahSeedsCount(BoardSide boardSide)
	{
		return kalahs.get(boardSide).getSeeds();
	}
	
	public int getSeedsCount(BoardSide boardSide, int pit)
	{
		validatePit(pit);
		return pits.get(getOffset(boardSide) + pit).getSeeds();
	}
	
	private void validatePit(int pit)
	{
		if (pit < 0 || pit > pitsPerSide)
			throw new IllegalArgumentException(String.format("Invalid pit %d", pit));
	}
	
	private int getOffset(BoardSide boardSide)
	{
		if (boardSide == BoardSide.SOUTH)
			return 0;
		return pitsPerSide + 1;
	}
	
	private class CircularIterator implements Iterator<AbstractPit>
	{
		private Iterator<AbstractPit> iterator;
		
		public CircularIterator(BoardSide boardSide, int startPit)
		{
			iterator = pits.listIterator(getOffset(boardSide) + startPit);
		}

		@Override
		public boolean hasNext()
		{
			return true;
		}

		@Override
		public AbstractPit next()
		{
			if (!iterator.hasNext())
				iterator = pits.iterator();
			return iterator.next();
		}
	}
	
}
