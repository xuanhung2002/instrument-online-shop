package com.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.shop.converter.Converter;
import com.shop.dto.CartItemDTO;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.repository.CartRepository;
import com.shop.service.CartService;

public class CartServiceImpl implements CartService {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	Converter converter;
	
	@Override
	public List<CartItemDTO> findCartItemsByUserId(Integer userId) {
		List<CartItem> cartItems = new ArrayList<CartItem>();
		cartItems = cartRepository.findCartItemSByUserId(userId);
		return cartItems.stream().map(c -> converter.toCartItemDTO(c)).toList();
	}
	
	@Override
	public Cart findCartByUserId(Integer userId) {
		return cartRepository.findFirstByUserId(userId);
	}
	
}
