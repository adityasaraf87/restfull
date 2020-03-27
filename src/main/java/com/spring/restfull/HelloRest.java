package com.spring.restfull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRest {
	
	@GetMapping(path="hello/{msg}")
	public HelloBean hello(@PathVariable String msg) {
		
		HelloBean hello=new HelloBean();
		hello.setMessage(String.format("%s Welcome to the Restful world", msg));
		return hello;
	}

}
