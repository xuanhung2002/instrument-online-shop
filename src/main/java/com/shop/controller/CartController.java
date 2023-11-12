package com.shop.controller;

import com.shop.converter.Converter;
import com.shop.dto.CartItemDTO;
import com.shop.dto.CartItemRequest;
import com.shop.dto.UpdateCartItemRequest;
import com.shop.service.CartItemService;
import com.shop.service.CartService;
import com.shop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/cart")
public class CartController {


    @Autowired
    CartService cartService;
    @Autowired
    Converter converter;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<CartItemDTO>> getCartItemsByUsername(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        if (cartService.getCartByUsername(username) == null) {
            cartService.createCartForUser(username);
        }

        List<CartItemDTO> cartItemDTOS = cartService.getCartItemsByUsername(username).stream().map(converter::toCartItemDTO).collect(Collectors.toList());

        return new ResponseEntity<>(cartItemDTOS, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCartItemToCart(@RequestBody CartItemRequest cartItemRequest,
                                                    Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        try {
            cartService.handleAddCartItemToCart(username, cartItemRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Add cart item to cart success");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @PostMapping("/update")
    public ResponseEntity<String> updateCartItemQuantityInCart(
            @RequestBody UpdateCartItemRequest updateCartItemRequest,
            Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        try {
            cartService.updateCartItemQuantity(username, updateCartItemRequest.getCartItemId(),
                    updateCartItemRequest.getNewQuantity());
            return new ResponseEntity<>("Update success", HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCartItemById(@PathVariable Integer id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        try {
            cartService.deleteCartItem(id, username);
            return ResponseEntity.status(HttpStatus.OK).body("Delete cart item succesfully!!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

}
