package com.shop.service;

import java.util.List;
import java.util.Optional;

import com.shop.entity.Image;

public interface ImageService {
	List<Image> findAllByItemId(Integer itemId);
	Image save(Image image);
	Optional<Image> findFirstByItemId(Integer itemId);
}
