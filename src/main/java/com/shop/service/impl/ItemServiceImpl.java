package com.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shop.converter.Converter;
import com.shop.dto.ItemDTO;
import com.shop.entity.Category;
import com.shop.entity.Item;
import com.shop.repository.ItemRepository;
import com.shop.service.ItemService;
import com.shop.utils.PageableUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemRepository itemRepository;
	@Autowired
	Converter converter;

	@Override
	public List<ItemDTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {
		
		Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy);

		Page<Item> pageResult = itemRepository.findAll(pageable);
		if(pageResult.getContent() != null) {
			return pageResult.getContent().stream().map(t -> converter.toItemDTO(t)).collect(Collectors.toList());
		}
		else {
			return new ArrayList<ItemDTO>();
		}
//		return itemRepository.findAll()
//				.stream().map(t -> converter.toItemDTO(t)).toList();
	}

	@Override
	public ItemDTO getItemById(Integer id) {
	Optional<Item> itemOpt = itemRepository.findById(id);
        return itemOpt.map(item -> converter.toItemDTO(item)).orElse(null);
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
	public List<ItemDTO> findByCategory(Category category, Integer pageNo, Integer pageSize, String sortBy) {
		Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy); 		
		List<Item> items = itemRepository.findByCategory(category, pageable);
		return items.stream().map(t -> converter.toItemDTO(t)).toList();
	}
	
	@Override
	public Integer getItemInventoryQuantityById(Integer idItem) {
		return itemRepository.getItemInventoryQuantityById(idItem);
	}

	@Override
	public void deleteItemById(Integer id) {
		itemRepository.deleteById(id);
	}

	@Override
	public boolean existedById(Integer id) {
		return itemRepository.existsById(id);
	}
}
