package in.learnjavaskills.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.learnjavaskills.springsecurity.entity.UserdetailsEntity;
import in.learnjavaskills.springsecurity.service.RegistrationService;

@RestController
@RequestMapping("/")
public class RegisterController 
{
	private final PasswordEncoder passwordEncoder;
	private final RegistrationService registrationService;
	
	@Autowired
	public RegisterController(PasswordEncoder passwordEncoder, RegistrationService registrationService)
	{
		this.passwordEncoder = passwordEncoder;
		this.registrationService = registrationService;
	}
	
	@PostMapping("register")
	public ResponseEntity<String> register(@RequestBody UserdetailsEntity userdetailEntity)
	{
		userdetailEntity.setPassword(passwordEncoder.encode(userdetailEntity.getPassword()));
		return new ResponseEntity<String>(registrationService.register(userdetailEntity), HttpStatus.OK);
	}
	
}
