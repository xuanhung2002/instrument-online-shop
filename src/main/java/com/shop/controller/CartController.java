package com.shop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

	private final CartItemService cartItemService;
	private final CartService cartService;
	private final ItemService itemService;
	@Autowired
	public CartController(CartItemService cartItemService, CartService cartService, ItemService itemService) {
		this.cartItemService = cartItemService;
		this.cartService = cartService;
		this.itemService = itemService;
	}

	@GetMapping("")
	@ResponseBody
	public ResponseEntity<List<CartItemDTO>> getCartItemsByUsername(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();

		if(cartService.findCartByUsername(username) == null){
			cartService.createCartForUser(username);
		}

		return new ResponseEntity<>(cartService.findCartItemsDTOByUsername(username), HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addCartItemToCart(@RequestBody NewCartItemRequest cartItemRequest,
													Authentication authentication) {
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();

		if(cartService.findCartByUsername(username) == null){
			cartService.createCartForUser(username);
		}
		
//		if(cartItemRequest.getQuantity() > itemService.getItemInventoryQuantityById(cartItemRequest.getItemId())) {
//			return new ResponseEntity<String>("Not enough quantity of this item in inventory", HttpStatus.OK);
//		}
		if(!availableQuantity(cartItemRequest.getQuantity(), itemService.getItemInventoryQuantityById(cartItemRequest.getItemId()))){
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

		return new ResponseEntity<>("Add cart item to cart success", HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<String> updateCartItemQuantityInCart(
			@RequestBody UpdateCartItemRequest updateCartItemRequest,
			Authentication authentication) {

		if(updateCartItemRequest.getNewQuantity() > cartItemService.
																	findItemByCartItemId(
																	updateCartItemRequest.getCartItemId())
																	.getInventoryQuantity()){
			return new ResponseEntity<>("Not enough item quantity", HttpStatus.OK);
		}

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		cartService.updateItemCartQuantity(username, updateCartItemRequest.getCartItemId(),
				updateCartItemRequest.getNewQuantity());

		return new ResponseEntity<>("Update success", HttpStatus.OK);
	}

//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<Void> deleteCartItemById(@PathVariable Integer id, Authentication authentication){
//
//		if(cartItemService.existById(id)){
//			cartItemService.deleteById(id);
//			return new ResponseEntity<>(HttpStatus.OK);
//		}
//		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteCartItemById(@PathVariable Integer id, Authentication authentication){
		if(authentication == null || !authentication.isAuthenticated()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		//
		if(cartItemService.existById(id)){
			cartItemService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}


	private static boolean availableQuantity(Integer quantityRequest, Integer quantityAvailable){
		if(quantityRequest > quantityAvailable) {
			return false;
		}else {
			return true;
		}
	}
}
