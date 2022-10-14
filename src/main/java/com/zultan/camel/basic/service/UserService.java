package com.zultan.camel.basic.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.zultan.camel.basic.dto.User;

@Service
public class UserService {

	private ArrayList<User> list = new ArrayList<User>();
	
	public UserService() {
		initDB();
	}
	
	//@PostConstruct
	public void initDB() {
		list.add(new User(1, "Zul", "Tan", 50));
		list.add(new User(2, "Pai", "Jal", 40));
		list.add(new User(3, "No", "Yo", 39));
		list.add(new User(4, "Jug", "Ger", 39));
		list.add(new User(5, "Wak", "Ndok", 41));
	}
	
	public ArrayList<User> getUsers() {
		//initDB();
		return list;
	}
	
	public User addUsers(User user) {
		list.add(user);
		return user;
	}
	
}
