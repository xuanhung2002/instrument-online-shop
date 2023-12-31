package com.shop.service;

import java.util.List;

import com.shop.dto.CartItemDTO;
import com.shop.entity.CartItem;
import com.shop.entity.Item;

public interface CartItemService {
	List<CartItemDTO> findCartItemsByCartId(Integer cartId);

	Item getItemByCartItemId(Integer id);
	CartItem getCartItemById(Integer id);

	boolean existById(Integer id);
	void deleteById(Integer id);

	void save(CartItem cartItem);

}
