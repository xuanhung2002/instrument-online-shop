package com.shop.service;

import com.shop.dto.ItemRequestDTO;
import com.shop.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
	List<Item> getAll(Integer pageNo, Integer pageSize, String sortBy);
	Item getItemById(Integer id);

	Optional<Item> findItemById(Integer id);
	Item save(Item item);
	Optional<Item> getItemByBrandName(String name);
	List<Item> getItemsByCategory(String categoryName, Integer pageNo, Integer pageSize, String sortBy);
	
	Integer getItemInventoryQuantityById(Integer idItem);

	void deleteItemById(Integer id);
	boolean existedById(Integer id);

	List<Item> searchItemsByName(String searchKey);

	Item addNewItem(ItemRequestDTO itemRequestDTO);

	Item updateItem(Integer id, ItemRequestDTO itemRequestDTO);
}
