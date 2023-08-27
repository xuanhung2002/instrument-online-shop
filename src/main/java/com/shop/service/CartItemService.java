package com.shop.service;

import java.util.List;

import com.shop.dto.CartItemDTO;
import com.shop.entity.Item;

public interface CartItemService {
	List<CartItemDTO> findCartItemsByCartId(Integer cartId);

	Item findItemByCartItemId(Integer id);
}
