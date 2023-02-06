package in.learnjavaskills.springsecurity.security;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import in.learnjavaskills.springsecurity.entity.UserdetailsEntity;
import in.learnjavaskills.springsecurity.repository.UserdetailsRepository;

@Component
public class UsernamePasswordAuthenticator implements AuthenticationProvider 
{

	private final UserdetailsRepository userdetailsRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UsernamePasswordAuthenticator(UserdetailsRepository userdetailsRepository, PasswordEncoder passwordEncoder)
	{
		this.userdetailsRepository = userdetailsRepository;
		this.passwordEncoder = passwordEncoder; 
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		System.out.println("under username password authenticator");
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		return authenticateUser(username, password);
	}

	@Override
	public boolean supports(Class<?> authentication) 
	{
		return UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication);
	}
	
	
	private Authentication authenticateUser(String username, String password)
	{
		if (Objects.isNull(username) && username.isEmpty())
			throw new BadCredentialsException("username can't be empty or null");
		
		List<UserdetailsEntity> userdetailList = userdetailsRepository.findByUseremail(username);
		if (userdetailList.isEmpty())
			throw new UsernameNotFoundException("username " + username + " not found in the system");
		
		UserdetailsEntity userdetailsEntity = userdetailList.get(0);
		 if( passwordEncoder.matches(password, userdetailsEntity.getPassword()))
		 {
			 List<SimpleGrantedAuthority> role = Collections.singletonList(new SimpleGrantedAuthority(userdetailsEntity.getRole().toString()));
			 return new UsernamePasswordAuthenticationToken(username, password, role);
		 }
		 throw new UsernameNotFoundException("username not found in the system");
	}
	
}
