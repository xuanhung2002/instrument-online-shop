package com.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entity.Brand;
import com.shop.repository.BrandRepository;
import com.shop.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	BrandRepository brandRepository;

	@Override
	public List<Brand> getAll() {
		return brandRepository.findAll();
	}

	@Override
	public Brand save(Brand brand) {
		return brandRepository.save(brand);
	}

	@Override
	public Optional<Brand> findOneByName(String brandName) {
        return brandRepository.findFirstByName(brandName);
    }

	@Override
	public Optional<Brand> findById(Integer id) {
		return brandRepository.findById(id);
    }

}
