package com.shop.service;

import java.util.List;
import java.util.Optional;

import com.shop.entity.Brand;

public interface BrandService {
	List<Brand> getAll();
	Brand save(Brand brand);
	Optional<Brand> findOneByName(String brandName);

	Optional<Brand> findById(Integer id);
}
