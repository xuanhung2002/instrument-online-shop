package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
	private Integer id;

	private Integer itemId;

	private String itemImage;

	private String nameItem;

	private Integer unitPrice;

	private Integer quantity;

	private Integer totalPrice;

}
