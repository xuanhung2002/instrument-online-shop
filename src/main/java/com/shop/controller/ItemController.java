package com.shop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.converter.Converter;
import com.shop.dto.ItemDTO;
import com.shop.entity.Category;
import com.shop.entity.Image;
import com.shop.entity.Item;
import com.shop.service.CategoryService;
import com.shop.service.ImageService;
import com.shop.service.ItemService;
import com.shop.utils.FileUtils;
import com.shop.utils.SortingUtils;

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
	Converter converter;
	@Autowired
	FileUtils fileUtils;

	@GetMapping("/")
	public ResponseEntity<List<ItemDTO>> getAll() {
		return new ResponseEntity<>(itemService.getAll(), HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Item> save(@RequestParam("name") String name, @RequestParam("description") String description,
			@RequestParam("price") Integer price, @RequestParam("brandName") String brandName,
			@RequestParam("categoryName") String categoryName, @RequestParam("images") List<MultipartFile> images) {

		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setName(name);
		itemDTO.setDescription(description);
		itemDTO.setPrice(price);
		itemDTO.setBrandName(brandName);
		itemDTO.setCategoryName(categoryName);

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

//	@GetMapping("/{name}")
//	public ResponseEntity<Item> getItemByName(@PathVariable String name){
////		 String convertedName = StringUtil.removeVietnameseAccents(name);
//		Optional<Item> itemOpt = itemService.findOneByName(name);
//		if(itemOpt.isPresent()) {
//			return new ResponseEntity<>(itemOpt.get(), HttpStatus.OK);
//		}
//		else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}

	@GetMapping("/{categoryName}")
	public ResponseEntity<List<ItemDTO>> getItemSByCategoryName(@PathVariable String categoryName,
			@RequestParam(name = "sortBy", required = false) String sortBy) {
		Optional<Category> categoryOpt = categoryService.findOneByName(categoryName);
		if (categoryOpt.isPresent()) {
			List<ItemDTO> items = new ArrayList<ItemDTO>(itemService.findByCategory(categoryOpt.get()));
			if (sortBy != null) {
				items.sort(SortingUtils.getComparatorForSorting(sortBy));
			}
			return new ResponseEntity<>(items, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
