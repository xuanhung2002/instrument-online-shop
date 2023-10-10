package com.shop.service;

import java.util.List;
import java.util.Optional;

import com.shop.dto.ItemDTO;
import com.shop.entity.Category;
import com.shop.entity.Item;

public interface ItemService {
	List<ItemDTO> getAll(Integer pageNo, Integer pageSize, String sortBy);
	ItemDTO getItemById(Integer id);

	Optional<Item> findItemById(Integer id);
	Item save(ItemDTO item);
	Optional<Item> findOneByName(String name);
	List<ItemDTO> findByCategory(Category category, Integer pageNo, Integer pageSize, String sortBy);
	
	Integer getItemInventoryQuantityById(Integer idItem);

	void deleteItemById(Integer id);
	boolean existedById(Integer id);
}
