package com.shop.dto;

public class CartItemDTO {
	private Integer id;
	
	private String itemImage;
	
	private String nameItem;
	
	private Integer unitPrice;
	
	private Integer quantity;
	
	private Integer totalPrice;
	
	public CartItemDTO() {
		// TODO Auto-generated constructor stub
	}

	public CartItemDTO(Integer id, String itemImage, String nameItem, Integer unitPrice, Integer quantity,
			Integer totalPrice) {
		super();
		this.id = id;
		this.itemImage = itemImage;
		this.nameItem = nameItem;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public String getNameItem() {
		return nameItem;
	}

	public void setNameItem(String nameItem) {
		this.nameItem = nameItem;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
