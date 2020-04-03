package com.spring.restfull.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	private static List<User> users = new ArrayList<>();
	private static int count=3;
	static {
		users.add(new User(1,"Aditya", new Date()));
		users.add(new User(2,"Megha", new Date()));
		users.add(new User(3,"Papa", new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		user.setId(count++);
		users.add(user);
		return user;
	}

	public User findOne(int id) {
		for(User user:users) {
			if(user.getId()==id)
				return user;
		}
		throw new UserNotFoundException("User does not exists");
	}
	
	public User deleteById(int id) {
		
		Iterator<User> listUser = users.iterator();
		User user = null;
		while(listUser.hasNext()) {
			user = listUser.next();
			if(user.getId()==id)
				return user;
		}
		return null;
	}

}
