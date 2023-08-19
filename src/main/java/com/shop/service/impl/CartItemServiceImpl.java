package com.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.converter.Converter;
import com.shop.dto.CartItemDTO;
import com.shop.repository.CartItemRepository;
import com.shop.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	CartItemRepository cartItemRepository;
	@Autowired 
	Converter converter;
	
	//sau sua thanh getCartItemsForLoggedInUser
	@Override
	public List<CartItemDTO> findCartItemsByCartId(Integer cartId) {
		return cartItemRepository.findByCartId(cartId).stream().map(c -> converter.toCartItemDTO(c)).toList();
	}
}
