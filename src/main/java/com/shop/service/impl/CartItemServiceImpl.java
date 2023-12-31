package com.shop.service.impl;

import java.util.List;
import java.util.Optional;

import com.shop.entity.CartItem;
import com.shop.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.converter.Converter;
import com.shop.dto.CartItemDTO;
import com.shop.repository.CartItemRepository;
import com.shop.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired(required = false)
    Converter converter;

    //sau sua thanh getCartItemsForLoggedInUser
    @Override
    public List<CartItemDTO> findCartItemsByCartId(Integer cartId) {
        return cartItemRepository.findByCartId(cartId).stream().map(c -> converter.toCartItemDTO(c)).toList();
    }


    @Override
    public Item getItemByCartItemId(Integer id) {
        return cartItemRepository.getItemByCartItemId(id);
    }

    @Override

    public CartItem getCartItemById(Integer id) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(id);
        return cartItemOpt.orElse(null);
    }

    @Override
    public boolean existById(Integer id) {
        return cartItemRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

}
