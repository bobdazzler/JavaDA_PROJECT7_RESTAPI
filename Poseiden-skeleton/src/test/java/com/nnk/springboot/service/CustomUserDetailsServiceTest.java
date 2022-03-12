package com.nnk.springboot.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomUserDetailsServiceTest {
	@InjectMocks
	CustomUserDetailsService customUserDetailsService;
	@Mock
	UserRepository userRepository;
	@Test
	public void testLoadUserByUsernameWhenUserExist() {
		User user = new User(1,"TestUser","User","Test123@","ADMIN");
		when(userRepository.findByUserName(Mockito.anyString())).thenReturn(user);
		UserDetails userDetails = customUserDetailsService.loadUserByUsername("User");
		assertEquals(userDetails.getUsername(),user.getUsername());
	}
	@Test
	public void testLoadUserByUsernameWhenUserDoesNotExist() {
		User user = null;
		when(userRepository.findByUserName(Mockito.anyString())).thenReturn(user);
		assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("User"));
	}

}
