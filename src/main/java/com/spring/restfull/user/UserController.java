package com.spring.restfull.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public Resource<User> findUserById(@PathVariable int id) {
		User user = userDao.findOne(id);
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User u=userDao.save(user);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest()
					 .path("/{id}")
					 .buildAndExpand(u.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userDao.deleteById(id);
		
		if(user==null)
			throw new UserNotFoundException("id "+id);
	}

}
