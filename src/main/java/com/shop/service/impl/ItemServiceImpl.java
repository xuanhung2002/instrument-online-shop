package com.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.converter.Converter;
import com.shop.dto.ItemDTO;
import com.shop.entity.Category;
import com.shop.entity.Item;
import com.shop.repository.ItemRepository;
import com.shop.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	Converter converter;

	@Override
	public List<ItemDTO> getAll() {
		return itemRepository.findAll()
				.stream().map(t -> converter.toItemDTO(t)).toList();
	}

	@Override
	public Item save(ItemDTO itemDTO) {
		return itemRepository.save(converter.toItemEntity(itemDTO));
	}

	@Override
	public Optional<Item> findOneByName(String name) {
		Optional<Item> itemOpt = itemRepository.findFirstByName(name);
		if (itemOpt.isPresent()) {
			return itemOpt;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ItemDTO> findByCategory(Category category) {
		List<Item> items = itemRepository.findByCategory(category);
		return items.stream().map(t -> converter.toItemDTO(t)).toList();
	}
}
