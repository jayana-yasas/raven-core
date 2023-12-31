package com.example.demo.service;


import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		Boolean user = userRepository.existsByEmail(email);
		
		if (!user) {
			throw new UsernameNotFoundException("Could not find user");
		}
		
		return new MyUserDetails(userRepository.findByEmail(email));
	}

}
