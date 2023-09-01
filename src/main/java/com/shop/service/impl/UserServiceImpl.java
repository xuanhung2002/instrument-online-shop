package com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shop.entity.CustomUserDetails;
import com.shop.entity.User;
import com.shop.repository.UserRepository;
import com.shop.service.UserService;

import exception.UserIdNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserById(Integer id) throws UsernameNotFoundException {
		User user = userRepository.findById(id).get();
		if (user == null) {
			throw new UserIdNotFoundException("User not found with id: " + id.toString());
		}

		return new CustomUserDetails(user);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
}
