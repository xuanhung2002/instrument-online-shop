package com.shop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.dto.CartItemDTO;
import com.shop.dto.NewCartItemRequest;
import com.shop.dto.UpdateCartItemRequest;
import com.shop.entity.CartItem;
import com.shop.service.CartItemService;
import com.shop.service.CartService;
import com.shop.service.ItemService;

@Controller
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	CartItemService cartItemService;

	@Autowired
	CartService cartService;
	
	@Autowired
	ItemService itemService;

	@GetMapping("")
	@ResponseBody
	public ResponseEntity<List<CartItemDTO>> getCartItemsByUsername() {
		return new ResponseEntity<List<CartItemDTO>>(cartService.findCartItemsDTOByUsername("test1"), HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addCartItemToCart(@RequestBody NewCartItemRequest cartItemRequest) {
		
//		String username =??  ==>> user đang đăng nhập ==> thêm param ở trên 
		String username = "test1"; // hard code to test without authentication
		
		if(cartItemRequest.getQuantity() > itemService.getItemInventoryQuantityById(cartItemRequest.getItemId())) {
			return new ResponseEntity<String>("Not enough quantity of this item in inventory", HttpStatus.OK);
		}
				
		// id cuủa các item trong cart
		List<Integer> IdOfItemsCurrent = new ArrayList<Integer>();

		IdOfItemsCurrent = cartService.findCartItemsByUsername(username).stream().map(ci -> ci.getItem().getId())
				.collect(Collectors.toList());

		if (IdOfItemsCurrent.contains(cartItemRequest.getItemId())) {

			// get cartItem là item đó của user đó, + số lượng của cartItem đó lên
			List<CartItem> cartItemsOfUser = cartService.findCartItemsByUsername(username);

			Optional<CartItem> cartItemToUpdateOptional = cartItemsOfUser.stream()
					.filter(c -> c.getItem().getId() == cartItemRequest.getItemId()).findFirst();

			if (cartItemToUpdateOptional.isPresent()) {
				CartItem cartItemToUpdate = cartItemToUpdateOptional.get();

				Integer newQuantity = cartItemToUpdate.getQuantity() + cartItemRequest.getQuantity();

				cartService.updateItemCartQuantity(username, cartItemToUpdate.getId(), newQuantity);
				return new ResponseEntity<String>(
						"Update cart item quantity success (because this item exist in this cart)", HttpStatus.OK);
			}
		} else {
			cartService.addItemCartToCart(username, cartItemRequest.getItemId(), cartItemRequest.getQuantity());
		}

		return new ResponseEntity<String>("Add cart item to cart success", HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<String> updateCartItemQuantityInCart(
			@RequestBody UpdateCartItemRequest updateCartItemRequest) {

//		String username =??  ==>> user đang đăng nhập ==> thêm param ở trên 
		String username = "test1"; // hard code to test without authentication
		cartService.updateItemCartQuantity(username, updateCartItemRequest.getCartItemId(),
				updateCartItemRequest.getNewQuantity());

		return new ResponseEntity<String>("Update success", HttpStatus.OK);
	}
}
