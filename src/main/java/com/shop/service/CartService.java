package com.shop.service;

import java.util.List;
import java.util.Optional;

import com.shop.dto.CartItemDTO;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;

public interface CartService {
	Cart findCartByUsername(String username);
	List<CartItemDTO> findCartItemsDTOByUsername(String username);
	List<CartItem> findCartItemsByUsername(String username);
	Cart findCartByUserId(Integer userId);
	
	void addItemCartToCart(String username, Integer itemId, Integer quantity);
	
	void updateItemCartQuantity(String username, Integer cartItemId, Integer quantity);

	void createCartForUser(String username);
}
