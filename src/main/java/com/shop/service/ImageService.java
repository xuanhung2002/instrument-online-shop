package com.shop.service;

import java.util.List;

import com.shop.entity.Image;

public interface ImageService {
	List<Image> findAllByItemId(Integer itemId);
	Image save(Image image);
}
