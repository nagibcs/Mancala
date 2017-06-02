package game.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import game.model.User;
import game.service.UserService;

/**
 * 
 * This class does not implement a real authentication logic. If the user is found
 * then he/she is authenticated, otherwise the user is registered and considered authenticated.
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
{

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String username = authentication.getName();
		User authenticateUser = userService.findUser(username);

		if (authenticateUser == null)
		{
			authenticateUser = new User(username);
			userService.registerUser(authenticateUser);
		}

		List<SimpleGrantedAuthority> grantedAuth = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, grantedAuth);

		return token;
	}

	@Override
	public boolean supports(Class<?> clazz)
	{
		return clazz.equals(UsernamePasswordAuthenticationToken.class);
	}
}
