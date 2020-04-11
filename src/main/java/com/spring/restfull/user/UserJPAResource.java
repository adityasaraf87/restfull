package com.spring.restfull.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJPAResource {
	
	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private PostRepository postDao;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return userDao.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public Resource<User> findUserById(@PathVariable int id) {
		Optional<User> user = userDao.findById(id);
		if(!user.isPresent())
			throw new UserNotFoundException("id "+id);
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllUserPost(@PathVariable int id) {
		Optional<User> user = userDao.findById(id);
		if(!user.isPresent())
			throw new UserNotFoundException("id "+id);
		
		return user.get().getPost();
	}
	
	
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User u=userDao.save(user);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest()
					 .path("/{id}")
					 .buildAndExpand(u.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id,@RequestBody Post post) {
		Optional<User> user = userDao.findById(id);
		if(!user.isPresent())
			throw new UserNotFoundException("id "+id);
		post.setUser(user.get());
		Post p=postDao.save(post);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest()
					 .path("/{id}")
					 .buildAndExpand(p.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userDao.deleteById(id);
	}

}
