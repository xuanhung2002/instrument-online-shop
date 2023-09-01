package com.shop.dto;

public class UpdateCartItemRequest {
	private Integer cartItemId;
	private Integer newQuantity;
    
    public UpdateCartItemRequest() {
	}

	public UpdateCartItemRequest(Integer cartItemId, Integer newQuantity) {
		super();
		this.cartItemId = cartItemId;
		this.newQuantity = newQuantity;
	}

	public Integer getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Integer cartItemId) {
		this.cartItemId = cartItemId;
	}

	public int getNewQuantity() {
		return newQuantity;
	}

	public void setNewQuantity(Integer newQuantity) {
		this.newQuantity = newQuantity;
	}
    
}

