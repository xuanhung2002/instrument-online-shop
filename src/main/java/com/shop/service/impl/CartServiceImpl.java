package com.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.converter.Converter;
import com.shop.dto.CartItemDTO;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ItemRepository;
import com.shop.service.CartService;
import com.shop.service.ItemService;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	Converter converter;
	@Autowired CartItemRepository cartItemRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Override
	public List<CartItemDTO> findCartItemsDTOByUsername(String username) {
		List<CartItem> cartItems = new ArrayList<CartItem>();
		cartItems = cartRepository.findCartItemsByUsername(username);
		return cartItems.stream().map(c -> converter.toCartItemDTO(c)).toList();
	}
	
	@Override
	public List<CartItem> findCartItemsByUsername(String username) {
		return cartRepository.findCartItemsByUsername(username);
	}
	
	@Override
	public Cart findCartByUserId(Integer userId) {
		return cartRepository.findFirstByUserId(userId);
	}
	
	@Override
	public void addItemCartToCart(String username, Integer itemId, Integer quantity) {
		CartItem cartItem = new CartItem();
		cartItem.setCart(cartRepository.findCartByUsername(username));
		cartItem.setItem(itemRepository.findById(itemId).get());
		cartItem.setQuantity(quantity);
		
		cartItemRepository.save(cartItem);
	}
	
	@Override
	public void updateItemCartQuantity(String username, Integer cartItemId, Integer newQuantity) {
		Optional<CartItem> existingCartItemOptional = cartItemRepository.findById(cartItemId);
		if(existingCartItemOptional.isPresent()) {
			CartItem existingCartItem = existingCartItemOptional.get();
			if(newQuantity > 0) {
				existingCartItem.setQuantity(newQuantity);
				cartItemRepository.save(existingCartItem);
				System.out.println("Huhu");
			}
			else {
				cartItemRepository.delete(existingCartItem);
			}
		}
		
	}
	
}
