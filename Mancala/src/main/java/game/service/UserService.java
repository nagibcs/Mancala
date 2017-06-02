package game.service;

import game.model.User;

public interface UserService
{

	public User findUser(String username);

	public void registerUser(User user);

}
