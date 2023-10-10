package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class Image {
	@Id
	@GeneratedValue
	@Column(name = "image_id")
	private Integer id;	
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	public Image() {
	}
	
	public Image(Integer id, String imageUrl) {
		this.id = id;
		this.imageUrl = imageUrl;
	}
	public Image(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Image(String imageUrl, Item item) {
		super();
		this.imageUrl = imageUrl;
		this.item = item;
	}
	public Image(String imageUrl, Brand brand) {
		super();
		this.imageUrl = imageUrl;
		this.brand = brand;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
}
