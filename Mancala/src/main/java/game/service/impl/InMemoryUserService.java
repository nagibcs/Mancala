package game.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;

import org.springframework.stereotype.Service;

import game.model.User;
import game.service.UserService;

@Service
@Singleton
public class InMemoryUserService implements UserService
{

	private final AtomicInteger id = new AtomicInteger(0);
	private final Map<String, User> map = new ConcurrentHashMap<>();

	@Override
	public User findUser(String username)
	{
		return map.get(username);
	}

	@Override
	public void registerUser(User user)
	{
		if (!map.containsKey(user.getUsername()))
		{
			int nextId = id.incrementAndGet();
			user.setId(nextId);
			map.put(user.getUsername(), user);
		}
	}
}
