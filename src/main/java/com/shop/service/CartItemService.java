package com.shop.service;

import java.util.List;

import com.shop.dto.CartItemDTO;

public interface CartItemService {
	List<CartItemDTO> findCartItemsByCartId(Integer cartId);
}
