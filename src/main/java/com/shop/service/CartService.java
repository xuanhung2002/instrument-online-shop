package com.shop.service;

import java.util.List;

import com.shop.dto.CartItemDTO;
import com.shop.entity.Cart;

public interface CartService {
	List<CartItemDTO> findCartItemsByUserId(Integer userId);
	Cart findCartByUserId(Integer userId);

}
