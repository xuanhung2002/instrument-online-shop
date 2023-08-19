package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
	
	@Query("Select c.cartItems from Cart c where c.user.id = :userId")
	List<CartItem> findCartItemSByUserId(@Param("userId") Integer userId);
	
	Cart findFirstByUserId(Integer userId);
}
