package com.shop.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.shop.entity.Image;
import com.shop.service.ImageService;
import com.shop.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shop.entity.Brand;
import com.shop.service.BrandService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

	@Autowired	
	BrandService brandService;

	@Autowired
	FileUtils fileUtils;
	@Autowired
	ImageService imageService;
	
	@GetMapping("")
	@ResponseBody
	public ResponseEntity<List<Brand>> getAll(){
		return new ResponseEntity<>(brandService.getAll(), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> save(@RequestParam("name") String name,
									  @RequestParam("country") String country,
									  @RequestParam("logo") MultipartFile logo) throws IOException {
	Optional<Brand> brandOpt = brandService.findOneByName(name);
	if(!brandOpt.isPresent()){
		Brand brand = new Brand();
		brand.setName(name);
		brand.setCountry(country);
		brandService.save(brand);
		Image logoToSave = new Image(fileUtils.uploadFile(logo), brand);
		imageService.save(logoToSave);

		brand.setLogo(logoToSave);

		return new ResponseEntity<>(brand, HttpStatus.OK);
	}
	else {
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Name brand is existed");
	}


	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateBrand(@PathVariable Integer id,
										 @RequestParam("name") String name,
										 @RequestParam("country") String country,
										 @RequestParam("logo") MultipartFile logo) throws IOException {
		Optional<Brand> brandOpt = brandService.findById(id);
		if(brandOpt.isPresent()){
			Brand oldBrand = brandOpt.get();
			oldBrand.setName(name);
			oldBrand.setCountry(country);

			if(oldBrand.getLogo() != null){
				Image existedImage = oldBrand.getLogo();
				fileUtils.deleteImageInCloudinary(existedImage.getImageUrl());
				existedImage.setImageUrl(fileUtils.uploadFile(logo));
				imageService.save(existedImage);
			}
			else {
				Image imageToSave = new Image(fileUtils.uploadFile(logo), oldBrand);
				oldBrand.setLogo(imageToSave);
				imageService.save(imageToSave);

			}
			return ResponseEntity.status(HttpStatus.OK).body(oldBrand);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
