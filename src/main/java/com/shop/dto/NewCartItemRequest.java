package com.shop.dto;

public class NewCartItemRequest {
	private Integer itemId;
    private int quantity;
    
    public NewCartItemRequest() {
		// TODO Auto-generated constructor stub
	}

	public NewCartItemRequest(Integer itemId, int quantity) {
		super();
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
}
