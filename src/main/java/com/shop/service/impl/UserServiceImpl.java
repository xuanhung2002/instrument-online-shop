package com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entity.User;
import com.shop.repository.UserRepository;
import com.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;


	public User getUserByUsername(String username){
		return userRepository.findByAccount_Username(username);
    }
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
}
