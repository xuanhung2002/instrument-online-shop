package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {


    @Query("Select c.cartItems from Cart c where c.user.account.username = :username")
    List<CartItem> getCartItemsByUsername(@Param("username") String username);

    @Query("Select c FROM Cart c WHERE c.user.account.username = :username")
    Cart getCartByUsername(@Param("username") String username);

    Cart findFirstByUserId(Integer userId);
}
