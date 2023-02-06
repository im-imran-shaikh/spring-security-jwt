package in.learnjavaskills.springsecurity.controller;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance/")
public class BalanceController {

	@GetMapping("available-balance")
	public String availableBaalance()
	{
		return "<h1>Hello _______, your available balance is 700 rupees.</h1>";
	}
	
	@PostMapping("deposit")
	public ResponseEntity<String> deposit()
	{
		System.out.println("under depost controller " + LocalDateTime.now());
		return ResponseEntity.ok()
			.cacheControl(CacheControl.maxAge(60L, TimeUnit.SECONDS))
			.body("Succesfully deposti amount");
	}
	
}
