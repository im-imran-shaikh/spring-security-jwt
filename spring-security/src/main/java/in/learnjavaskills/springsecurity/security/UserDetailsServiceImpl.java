package in.learnjavaskills.springsecurity.security;

import java.util.List;
import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import in.learnjavaskills.springsecurity.entity.UserdetailsEntity;
import in.learnjavaskills.springsecurity.repository.UserdetailsRepository;

//@Component //commented out becasue spring boot will not scan this class and defaul DaoAutheticatorProvider will not use
// because we have created our own usernamepassword authenticator provider
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserdetailsRepository userdetailsRepository;
	
	public UserDetailsServiceImpl(UserdetailsRepository userdetailsRepository) {
		this.userdetailsRepository = userdetailsRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (Objects.isNull(username) || username.isEmpty())
			throw new UsernameNotFoundException("username can't be bull or empty");

		List<UserdetailsEntity> userdetailsList = userdetailsRepository.findByUseremail(username);
		if (Objects.isNull(userdetailsList) || userdetailsList.isEmpty())
			throw new UsernameNotFoundException("username not found");
		
		return new UserDetailsImpl(userdetailsList.get(0));
	}

}
