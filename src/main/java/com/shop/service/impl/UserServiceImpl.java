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
	UserRepository userRepository;

	@Override
	public User findByAccount_Username(String username) {
		return userRepository.findByAccount_Username(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByAccount_Username(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new CustomUserDetails(user);
	}

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
