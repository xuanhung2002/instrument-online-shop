package com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shop.entity.CustomUserDetails;
import com.shop.entity.User;
import com.shop.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByAccount_Username(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new CustomUserDetails(user);
	}
}
