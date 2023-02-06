package in.learnjavaskills.springsecurity.service;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.learnjavaskills.springsecurity.entity.UserdetailsEntity;
import in.learnjavaskills.springsecurity.exception.UserAlreadyExists;
import in.learnjavaskills.springsecurity.repository.UserdetailsRepository;

@Service
public class RegistrationService {
	
	private final UserdetailsRepository userdetailRepository;
	
	public RegistrationService(UserdetailsRepository userdetailRepository)
	{
		this.userdetailRepository = userdetailRepository;
	}
	
	public String register(UserdetailsEntity userdetailEntity)
	{
		System.out.println("request received to register user " + userdetailEntity.toString());
		if (Objects.isNull(userdetailEntity) || Objects.isNull(userdetailEntity.getUseremail())
				|| userdetailEntity.getUseremail().isEmpty())
			throw new UsernameNotFoundException("useremail can't be null or empty");
		
		if (userdetailRepository.existsByuseremail(userdetailEntity.getUseremail()))
			throw new UserAlreadyExists("User found with useremail " + userdetailEntity.getUseremail(), HttpStatus.FORBIDDEN);
		
		userdetailRepository.save(userdetailEntity);
		return "user register succesfully";
	}

}
