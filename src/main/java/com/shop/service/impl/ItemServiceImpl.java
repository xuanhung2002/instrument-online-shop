package com.shop.service.impl;

import com.shop.converter.Converter;
import com.shop.dto.ItemRequestDTO;
import com.shop.entity.Brand;
import com.shop.entity.Category;
import com.shop.entity.Image;
import com.shop.entity.Item;
import com.shop.repository.ItemRepository;
import com.shop.service.BrandService;
import com.shop.service.CategoryService;
import com.shop.service.ImageService;
import com.shop.service.ItemService;
import com.shop.utils.FileUtils;
import com.shop.utils.PageableUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

  @Autowired
  ItemRepository itemRepository;
  @Autowired
  Converter converter;

  @Autowired
  BrandService brandService;

  @Autowired
  ImageService imageService;

  @Autowired
  FileUtils fileUtils;

  @Autowired
  CategoryService categoryService;

  @Override
  public List<Item> getAll(Integer pageNo, Integer pageSize, String sortBy) {

    Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy);

    Page<Item> pageResult = itemRepository.findAll(pageable);
    if (pageResult.getContent() != null) {
      return pageResult.getContent().stream().collect(Collectors.toList());
    } else {
      return null;
    }
  }

  @Override
  public Item getItemById(Integer id) {
    Optional<Item> itemOpt = itemRepository.findById(id);
    return itemOpt.orElse(null);
  }

  @Override
  public Optional<Item> findItemById(Integer id) {
    return itemRepository.findById(id);
  }

  @Override
  public Item save(Item item) {
    return itemRepository.save(item);
  }

  @Override
  public Optional<Item> getItemByBrandName(String name) {
    return Optional.empty();
  }


  @Override
  public List<Item> getItemsByCategory(String categoryName, Integer pageNo, Integer pageSize, String sortBy) {
    Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy);
    Optional<Category> categoryOpt = categoryService.getCategoryByCategoryName(categoryName);
    if (categoryOpt.isPresent()) {
          return itemRepository.getItemsByCategory(categoryOpt.get(), pageable);
    }
    else {
      throw new EntityNotFoundException("This category is not existed");
    }
  }

  @Override
  public Integer getItemInventoryQuantityById(Integer idItem) {
    return itemRepository.getItemInventoryQuantityById(idItem);
  }

  @Transactional
  @Override
  public void deleteItemById(Integer id) {
    itemRepository.deleteItemById(id);
  }

  @Override
  public boolean existedById(Integer id) {
    return itemRepository.existsById(id);
  }

  @Override
  public List<Item> searchItemsByName(String searchKey) {
    List<Item> items = itemRepository.searchItems(searchKey);
    if (items.isEmpty()) {
      return null;
    } else {
      return items;
    }
  }

  @Transactional
  @Override
  public Item addNewItem(ItemRequestDTO itemRequestDTO) {
    Brand brand = getBrand(itemRequestDTO.getBrandName());
    Category category = getCategory(itemRequestDTO.getCategoryName());

    Item item = new Item();
    item.setName(itemRequestDTO.getName());
    item.setDescription(itemRequestDTO.getDescription());
    item.setPrice(itemRequestDTO.getPrice());
    item.setBrand(brand);
    item.setCategory(category);
    item.setInventoryQuantity(itemRequestDTO.getInventoryQuantity());
    Item savedItem = itemRepository.save(item);

    List<Image> imagesToSave = imageService.uploadImages(itemRequestDTO.getImages(), item);

    imagesToSave.forEach(i -> {
      imageService.save(i);
    });

    return savedItem;
  }

  @Transactional
  @Override
  public Item updateItem(Integer id,ItemRequestDTO itemRequestDTO) {
    Brand brand = getBrand(itemRequestDTO.getBrandName());
    Category category = getCategory(itemRequestDTO.getCategoryName());

    Optional<Item> oldItemOpt = findItemById(id);
    if (oldItemOpt.isPresent()) {
      Item oldItem = oldItemOpt.get();
      oldItem.setName(itemRequestDTO.getName());
      oldItem.setDescription(itemRequestDTO.getDescription());
      oldItem.setPrice(itemRequestDTO.getPrice());
      oldItem.setBrand(brand);
      oldItem.setCategory(category);
      oldItem.setInventoryQuantity(itemRequestDTO.getInventoryQuantity());

      imageService.deleteAndSaveImages(itemRequestDTO.getImages(), oldItem);
      return itemRepository.save(oldItem);
    } else {
      throw new EntityNotFoundException("This item is not existed");
    }
  }

  private Brand getBrand(String brandName) {
    return brandService.getBrandByBrandName(brandName)
            .orElseThrow(() -> new EntityNotFoundException("This brand is not existed"));
  }

  private Category getCategory(String categoryName) {
    return categoryService.getCategoryByCategoryName(categoryName)
            .orElseThrow(() -> new EntityNotFoundException("This category is not existed"));
  }
}
