package com.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entity.Image;
import com.shop.repository.ImageRepository;
import com.shop.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageRepository imageRepository;

	@Override
	public List<Image> findAllByItemId(Integer itemId) {
		return imageRepository.findByItemId(itemId);
	}
	@Override
	public Image save(Image image) {
		return imageRepository.save(image);
	}
}
