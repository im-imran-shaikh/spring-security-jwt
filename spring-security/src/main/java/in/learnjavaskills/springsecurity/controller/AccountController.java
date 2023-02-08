package in.learnjavaskills.springsecurity.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/")
public class AccountController 
{

	private int hitCount = 0;
	
	@GetMapping("my-account")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> myAccount()
	{
		return ResponseEntity.ok()
			.cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
			.body("<h1>Hello _____, this is your account details. with api hit count : " + ++hitCount + " </h1>");
	}
	
}
