package game.util;

public class Pair<T, E>
{

	private final T first;
	private final E secod;

	public Pair(T first, E secod)
	{
		this.first = first;
		this.secod = secod;
	}

	public T getFirst()
	{
		return first;
	}

	public E getSecod()
	{
		return secod;
	}
}
