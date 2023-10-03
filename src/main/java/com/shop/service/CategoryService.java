package com.shop.service;

import java.util.List;
import java.util.Optional;

import com.shop.entity.Category;

public interface CategoryService {
	List<Category> getAll();
	Category save(Category category);
	Optional<Category> findOneByName(String name);

	boolean existedById(Integer id);
	void deteleById(Integer id);
}
