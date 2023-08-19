package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.dto.CartItemDTO;
import com.shop.service.CartItemService;

@Controller
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	CartItemService cartItemService;

	@GetMapping("")
	@ResponseBody									
	public ResponseEntity<List<CartItemDTO>> getCartItemsByCartId() {
		return new ResponseEntity<List<CartItemDTO>>(cartItemService.findCartItemsByCartId(1), HttpStatus.OK);
	}
}
