package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.entity.Category;
import com.shop.service.CategoryService;


@Controller
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<List<Category>> getAll(){
		System.out.println(categoryService.getAll());
		return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Category> save(@RequestBody Category category){
	return new ResponseEntity<Category>(categoryService.save(category), HttpStatus.OK);	
	}
}
