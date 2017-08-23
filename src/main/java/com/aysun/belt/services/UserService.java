package com.aysun.belt.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aysun.belt.models.User;
import com.aysun.belt.repositories.UserRepo;


@Service
public class UserService {
	
	private UserRepo userRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public void createUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepo.save(user);
	}
	
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

}