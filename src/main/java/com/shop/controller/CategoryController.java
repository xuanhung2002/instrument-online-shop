package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.shop.entity.Category;
import com.shop.service.CategoryService;


@Controller
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Category>> getAll() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody Category category) {
        Category c = categoryService.save(category);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This category name has existed");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(c);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Integer id) {
        if (categoryService.existedById(id)) {
            categoryService.deteleById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Delete success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This category is not existed");
    }
}
