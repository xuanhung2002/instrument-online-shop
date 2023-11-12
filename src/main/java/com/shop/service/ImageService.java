package com.shop.service;

import java.util.List;
import java.util.Optional;

import com.shop.entity.Image;
import com.shop.entity.Item;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	List<Image> getAllByItemId(Integer itemId);
	Image save(Image image);
	Optional<Image> getFirstByItemId(Integer itemId);
	void delete(Image image);
	List<Image> uploadImages(List<MultipartFile> images, Item item);

	void deleteAndSaveImages(List<MultipartFile> images, Item oldItem);

}
