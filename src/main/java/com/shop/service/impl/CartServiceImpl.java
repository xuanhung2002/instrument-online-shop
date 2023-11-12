package com.shop.service.impl;

import com.shop.dto.CartItemRequest;
import com.shop.dto.UpdateCartItemRequest;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ItemRepository;
import com.shop.service.CartItemService;
import com.shop.service.CartService;
import com.shop.service.ItemService;
import com.shop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private CartItemRepository cartItemRepository;
  @Autowired
  private ItemService itemService;
  @Autowired
  private UserService userService;
  @Autowired
  CartItemService cartItemService;


  @Override
  public Cart getCartByUsername(String username) {
    return cartRepository.getCartByUsername(username);
  }


  @Override
  public List<CartItem> getCartItemsByUsername(String username) {
    return cartRepository.getCartItemsByUsername(username);
  }


  @Transactional
  @Override
  public void addCartItemToCart(String username, CartItemRequest cartItemRequest) {

    Optional<Item> itemOpt = itemService.findItemById(cartItemRequest.getItemId());
    if (itemOpt.isPresent()) {
      CartItem cartItem = new CartItem();
      cartItem.setCart(cartRepository.getCartByUsername(username));
      cartItem.setItem(itemOpt.get());
      cartItem.setQuantity(cartItemRequest.getQuantity());
      cartItemRepository.save(cartItem);
    }

  }

  @Transactional
  @Override
  public void updateCartItemQuantity(String username, Integer cartItemId, Integer newQuantity) {
    Optional<CartItem> existingCartItemOptional = cartItemRepository.findById(cartItemId);
    if (existingCartItemOptional.isPresent()) {
      CartItem existingCartItem = existingCartItemOptional.get();
      if (newQuantity > 0) {
        existingCartItem.setQuantity(newQuantity);
        cartItemRepository.save(existingCartItem);
      } else {
        cartItemRepository.delete(existingCartItem);
      }
    }
  }

  @Override
  public void createCartForUser(String username) {
    if (getCartByUsername(username) == null) {
      Cart cartToSave = new Cart();
      cartToSave.setUser(userService.getUserByUsername(username));
      cartRepository.save(cartToSave);
    }
  }

  @Override
  public boolean availableQuantity(Integer quantityRequest, Integer quantityAvailable) {
    return quantityRequest <= quantityAvailable;
  }

  @Transactional
  @Override
  public void handleAddCartItemToCart(String username, CartItemRequest cartItemRequest) {
    //create if user have no cart
    createCartForUser(username);

    if (!availableQuantity(cartItemRequest.getQuantity(), itemService.getItemInventoryQuantityById(cartItemRequest.getItemId()))) {
      throw new IllegalArgumentException("Not enough quantity of this item in inventory");
    }
    // id cuủa các item trong cart
    List<Integer> IdOfItemsCurrent = new ArrayList<Integer>();
    IdOfItemsCurrent = getCartItemsByUsername(username).stream().map(ci -> ci.getItem().getId())
            .collect(Collectors.toList());

    if (IdOfItemsCurrent.contains(cartItemRequest.getItemId())) {

      // get cartItem là item đó của user đó, + số lượng của cartItem đó
      List<CartItem> cartItemsOfUser = getCartItemsByUsername(username);

      Optional<CartItem> cartItemToUpdateOptional = cartItemsOfUser.stream()
              .filter(c -> Objects.equals(c.getItem().getId(), cartItemRequest.getItemId())).findFirst();

      if (cartItemToUpdateOptional.isPresent()) {
        CartItem cartItemToUpdate = cartItemToUpdateOptional.get();

        Integer newQuantity = cartItemToUpdate.getQuantity() + cartItemRequest.getQuantity();

        updateCartItemQuantity(username, cartItemToUpdate.getId(), newQuantity);
      }
    } else {
      addCartItemToCart(username, cartItemRequest);
    }
  }

  @Transactional
  @Override
  public void handleUpdateCartItemQuantity(String username, UpdateCartItemRequest updateCartItemRequest) {
    if (updateCartItemRequest.getNewQuantity() > cartItemService.
            getItemByCartItemId(
                    updateCartItemRequest.getCartItemId())
            .getInventoryQuantity()) {
     throw new IllegalArgumentException("Not enough item quantity");
    }
    updateCartItemQuantity(username, updateCartItemRequest.getCartItemId(),
            updateCartItemRequest.getNewQuantity());
  }

  @Override
  public void deleteCartItem(Integer id, String username) {
    if (cartItemService.existById(id) && getCartItemsByUsername(username).contains(cartItemService.getCartItemById(id))) {
      cartItemService.deleteById(id);
    } else {
      throw new EntityNotFoundException("This cart item is not existed");
    }
  }
}
