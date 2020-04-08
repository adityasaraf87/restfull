package com.spring.restfull;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRest {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="hello/{msg}")
	public HelloBean hello(@PathVariable String msg) {
		
		HelloBean hello=new HelloBean();
		hello.setMessage(String.format("%s Welcome to the Restful world", msg));
		return hello;
	}
	
	@GetMapping("/hello-world-internalized")
	public String helloWorldInternationalized() {
		return messageSource.getMessage("good.morning.messages",null, LocaleContextHolder.getLocale());
	}
	
	

}
