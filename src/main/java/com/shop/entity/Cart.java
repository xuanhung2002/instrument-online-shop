package com.shop.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
	private List<CartItem> cartItems = new ArrayList<CartItem>();
	
	public Cart() {
	}

	public Cart(Integer id, User user, List<CartItem> cartItems) {
		super();
		this.id = id;
		this.user = user;
		this.cartItems = cartItems;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
}
