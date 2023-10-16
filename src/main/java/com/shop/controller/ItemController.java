package com.shop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shop.converter.Converter;
import com.shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ItemDTO;
import com.shop.entity.Category;
import com.shop.entity.Image;
import com.shop.entity.Item;
import com.shop.service.CategoryService;
import com.shop.service.ImageService;
import com.shop.service.ItemService;
import com.shop.utils.FileUtils;

@RestController
@RequestMapping("api/item")
public class ItemController {

	@Autowired
	ItemService itemService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ImageService imageService;
	@Autowired
	FileUtils fileUtils;

	@Autowired
	BrandService brandService;

	@Autowired
	Converter converter;



	@GetMapping("")
	public ResponseEntity<List<ItemDTO>> getAll(
			@RequestParam(name = "page", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "size", defaultValue = "2147483647" ) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = "name-asc") String sortBy) {
		
		return new ResponseEntity<>(itemService.getAll(pageNo, pageSize, sortBy), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemDTO> getItemById(@PathVariable Integer id){
		if(itemService.getItemById(id) == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(itemService.getItemById(id), HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Item> save(@RequestParam("name") String name,
									 @RequestParam("description") String description,
									@RequestParam("price") Integer price,
									 @RequestParam("brandName") String brandName,
									@RequestParam("categoryName") String categoryName,
									@RequestParam("inventoryQuantity") Integer inventoryQuantity,
									@RequestParam("images") List<MultipartFile> images) {

		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setName(name);
		itemDTO.setDescription(description);
		itemDTO.setPrice(price);
		itemDTO.setBrandName(brandName);
		itemDTO.setCategoryName(categoryName);
		itemDTO.setInventoryQuantity(inventoryQuantity);

		Item savedItem = itemService.save(itemDTO);
		
		List<Image> imagesToSave = new ArrayList<Image>();
		
		images.forEach(t -> {
			try {
				imagesToSave.add(new Image(fileUtils.uploadFile(t), savedItem));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		//add full element to return responseEntity
		if(savedItem.getImages() == null) {
			savedItem.setImages(imagesToSave);
		}
		else {
			savedItem.getImages().addAll(imagesToSave);
		}

		imagesToSave.forEach(i -> {
			imageService.save(i);
		});

		return new ResponseEntity<>(savedItem, HttpStatus.OK);
	}


	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id,
									@RequestParam("name") String name,
									@RequestParam("description") String description,
									@RequestParam("price") Integer price,
									@RequestParam("brandName") String brandName,
									@RequestParam("categoryName") String categoryName,
									@RequestParam("inventoryQuantity") Integer inventoryQuantity,
									@RequestParam("images") List<MultipartFile> images){

		Optional<Item> oldItemOpt = itemService.findItemById(id);
		if(oldItemOpt.isPresent()){
			Item oldItem = oldItemOpt.get();
			oldItem.setName(name);
			oldItem.setDescription(description);
			oldItem.setPrice(price);
			oldItem.setBrand(brandService.findOneByName(brandName).get());
			oldItem.setCategory(categoryService.findOneByName(categoryName).get());
			oldItem.setInventoryQuantity(inventoryQuantity);

			List<Image> imagesToSave = new ArrayList<Image>();
			images.forEach(t -> {
				try {
					imagesToSave.add(new Image(fileUtils.uploadFile(t), oldItem));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			oldItem.setImages(imagesToSave);

			itemService.save(converter.toItemDTO(oldItem));
			return ResponseEntity.status(HttpStatus.OK).body(oldItem);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}


	}

	@GetMapping("/")
	public ResponseEntity<List<ItemDTO>> getItemSByCategoryName(@RequestParam(value = "category", required = true) String categoryName,
			@RequestParam(name = "page", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "size", defaultValue = "5") Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = "name-asc") String sortBy) {
		
		
		Optional<Category> categoryOpt = categoryService.findOneByName(categoryName);
		if (categoryOpt.isPresent()) {
			List<ItemDTO> items = new ArrayList<ItemDTO>(itemService.findByCategory(categoryOpt.get(), pageNo, pageSize, sortBy));
			return new ResponseEntity<>(items, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteItemById(@PathVariable Integer id){
		if(itemService.existedById(id)){
			itemService.deleteItemById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
