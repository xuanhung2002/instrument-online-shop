package com.shop.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.shop.entity.User;


public interface UserService{
	UserDetails loadUserById(Integer id);
	User save(User user);
	User getUserByUsername(String username);
}
