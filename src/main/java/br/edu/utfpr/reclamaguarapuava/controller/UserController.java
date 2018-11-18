package br.edu.utfpr.reclamaguarapuava.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@RequestMapping("/users")
	@ResponseBody
	public String getUsers() {

		
		//test to authentication
		return "{\"users\":[{\"name\":\"Peterson\", \"country\":\"Brazil\"}," +
		           "{\"name\":\"John\",\"country\":\"E.U.A\"}]}";
	}
}
