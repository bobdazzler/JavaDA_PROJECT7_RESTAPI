package com.nnk.springboot.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new CustomUserDetails(user);	
	}
	
	}

