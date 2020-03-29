package com.spring.restfull.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//User Controller
@RestController
public class UserController {
	
	@Autowired
	private UserDaoService userDao;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return userDao.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User findUserById(@PathVariable int id) {
		return userDao.findOne(id);
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		User u=userDao.save(user);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest()
					 .path("/{id}")
					 .buildAndExpand(u.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	}

}
