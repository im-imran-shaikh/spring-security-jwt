package in.learnjavaskills.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact/")
public class ContactController {

	public String getEmailAddress()
	{
		return "<h1>Contact us at imran@learnjavaskills.in</h1>";
	}
	
	
	
}
