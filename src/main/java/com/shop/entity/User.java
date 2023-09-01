package com.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "address")
	private String address;
	
	@OneToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	private Cart cart;
	
	public User() {
	}

	public User(Integer id, String name, String email, String phoneNumber, String address, Account account, Role role,
			Cart cart) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.account = account;
		this.role = role;
		this.cart = cart;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}
