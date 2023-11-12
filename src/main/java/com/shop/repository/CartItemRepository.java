package com.shop.repository;

import java.util.List;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartId(Integer cartId);

    @Query("SELECT ci.item FROM CartItem ci WHERE ci.id = :id")
    Item getItemByCartItemId(@Param("id") Integer id);

} 
