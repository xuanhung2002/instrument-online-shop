package com.shop.dto;

import java.util.List;

import com.shop.entity.Image;

import lombok.Builder;
@Builder
public class ItemDTO {
	private Integer id;

	private String name;

	private String description;

	private Integer price;

	private String brandName;

	private String categoryName;
	
	private List<Image> images;
	
	private Integer inventoryQuantity;

	public ItemDTO() {
	}


	public ItemDTO(Integer id, String name, String description, Integer price, String brandName, String categoryName,
			List<Image> images, Integer inventoryQuantity) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.brandName = brandName;
		this.categoryName = categoryName;
		this.images = images;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}


	public Integer getInventoryQuantity() {
		return inventoryQuantity;
	}


	public void setInventoryQuantity(Integer inventoryQuantity) {
		if(inventoryQuantity == null) {
			this.inventoryQuantity = 0;
		}
		this.inventoryQuantity = inventoryQuantity;
	}
	
	
}
