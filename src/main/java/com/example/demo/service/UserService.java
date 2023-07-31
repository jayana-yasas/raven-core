package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public void processOAuthPostLogin(String email) {
		Boolean existUser = repo.existsByEmail(email);
		
		if (!existUser) {
			User newUser = new User();
			newUser.setFirstName(email);
			newUser.setEmail(email);

			repo.save(newUser);
			
			System.out.println("Created new user: " + email);
		}
		
	}
	
}
