package com.shop.converter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.shop.dto.CartItemDTO;
import com.shop.dto.ItemDTO;
import com.shop.entity.CartItem;
import com.shop.entity.Image;
import com.shop.entity.Item;
import com.shop.service.BrandService;
import com.shop.service.CategoryService;
import com.shop.service.ImageService;

@Component
@Lazy
public class Converter {
	@Autowired
	BrandService brandService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ImageService imageService;

	
	public Item toItemEntity(ItemDTO itemDTO) {
		Item item = new Item();
		item.setId(itemDTO.getId());
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setPrice(itemDTO.getPrice());
		item.setBrand(brandService.findOneByName(itemDTO.getBrandName()).get());
		item.setCategory(categoryService.findOneByName(itemDTO.getCategoryName()).get());
		item.setInventoryQuantity(itemDTO.getInventoryQuantity());;
		return item;
	}
	
	public ItemDTO toItemDTO(Item item) {
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setId(item.getId());
		itemDTO.setName(item.getName());
		itemDTO.setPrice(item.getPrice());
		itemDTO.setDescription(item.getDescription());
		itemDTO.setBrandName(item.getBrand().getName());
		itemDTO.setCategoryName(item.getCategory().getName());
		itemDTO.setImages(imageService.findAllByItemId(item.getId()));
		itemDTO.setInventoryQuantity(item.getInventoryQuantity());
		return itemDTO;
	}
	
	
	public CartItemDTO toCartItemDTO(CartItem cartItem) {
		CartItemDTO cartItemDTO = new CartItemDTO();
		cartItemDTO.setId(cartItem.getId());
		cartItemDTO.setItemId(cartItem.getId());
		Optional<Image> imageOptional = imageService.findFirstByItemId(cartItem.getItem().getId());
		if (imageOptional.isPresent()) {
		    cartItemDTO.setItemImage(imageOptional.get().getImageUrl());
		} else {
		    // Xử lý trường hợp không tìm thấy hình ảnh
			cartItemDTO.setItemImage(null);
		}
		
//		cartItemDTO.setItemImage(imageService.findFirstByItemId(cartItem.getItem().getId())
//																.get()
//																.getImageUrl());
		cartItemDTO.setNameItem(cartItem.getItem().getName());
		cartItemDTO.setQuantity(cartItem.getQuantity());
		cartItemDTO.setUnitPrice(cartItem.getItem().getPrice());
		cartItemDTO.setTotalPrice(cartItem.getQuantity() * cartItem.getItem().getPrice());
		
		return cartItemDTO;
	}
	
//	public CartItem toCartItem(CartItemDTO cartItemDTO) {
//		CartItem cartItem = new CartItem();	
//		return null;
//	}
}
