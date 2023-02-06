package in.learnjavaskills.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/interest-rate/")
public class InterestRateController {

	@GetMapping("fd-rate")
	public Double getFDRate(@RequestParam int periodsInMonths)
	{
		return periodsInMonths * 0.5;
	}
	
	@GetMapping("saving")
	public Double SavingRate(@RequestParam long amount)
	{
		if (amount > 1000)
			return 5.2;
		return 4.2;
	}
	
}
