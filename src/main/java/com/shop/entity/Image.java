package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
	
}
