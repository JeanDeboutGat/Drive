package fr.eservices.drive.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.eservices.drive.repository.OrderRepository;
import fr.eservices.drive.repository.UserRepository;

@Controller
@RequestMapping(path="/data")
public class DataController {
	
	OrderRepository orderREpo;
	UserRepository userRepo;

	@ResponseBody
	@PostMapping(path="/init.json",consumes="application/json")
	public void  init() {
	
	}
	
		

}
