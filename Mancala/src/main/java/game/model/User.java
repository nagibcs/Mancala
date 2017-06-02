package game.model;

import java.util.Objects;

public class User
{

	private int id;

	private String username;
	
	public User(int id, String username)
	{
		this.id = id;
		this.username = username;
	}
	
	public User(String username)
	{
		this.username = username;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;

		if (!(obj instanceof User))
			return false;

		User other = (User) obj;

		return id == other.id && Objects.equals(username, other.username);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, username);
	}

	@Override
	public String toString()
	{
		return new StringBuilder().append("{id: ").append(id).append(", name: ").append(username).append('}')
				.toString();
	}
}
