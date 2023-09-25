package com.shop.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id", insertable = false, updatable = false)
	private Integer id;

	@Column(name = "item_name")
	private String name;

	@Column(name = "description", columnDefinition = "CLOB")
	private String description;

	@Column(name = "price")
	private Integer price;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@Column(name = "inventory_quantity")
	private Integer inventoryQuantity;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "item")
	private List<CartItem> cartItems;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "item")
	private List<Image> images;


	public Item() {
	}

	public Item(Integer id, String name, String description, Integer price, Brand brand, Category category, Integer inventoryQuantity) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.brand = brand;
		this.category = category;
		this.inventoryQuantity = inventoryQuantity;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}	
	
	public Integer getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(Integer inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	

}
