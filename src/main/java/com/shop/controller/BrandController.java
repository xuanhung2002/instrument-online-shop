package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.entity.Brand;
import com.shop.service.BrandService;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

	@Autowired	
	BrandService brandService;
	
	@GetMapping("")
	@ResponseBody
	public ResponseEntity<List<Brand>> getAll(){
		System.out.println("branch");
		return new ResponseEntity<List<Brand>>(brandService.getAll(), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Brand> save(@RequestBody Brand brand){
	return new ResponseEntity<Brand>(brandService.save(brand), HttpStatus.OK);	
	}
}
