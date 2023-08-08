package com.shop.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shop.dto.ItemDTO;
import com.shop.entity.Item;
import com.shop.service.BrandService;
import com.shop.service.CategoryService;
import com.shop.service.ImageService;

@Component
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
		return itemDTO;
	}
}
