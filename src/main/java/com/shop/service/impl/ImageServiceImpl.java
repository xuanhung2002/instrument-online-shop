package com.shop.service.impl;

import com.shop.entity.Image;
import com.shop.entity.Item;
import com.shop.repository.ImageRepository;
import com.shop.service.ImageService;
import com.shop.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

  @Autowired
  ImageRepository imageRepository;

  @Autowired
  FileUtils fileUtils;

  @Override
  public List<Image> getAllByItemId(Integer itemId) {
    return imageRepository.findByItemId(itemId);
  }

  @Override
  public Image save(Image image) {
    return imageRepository.save(image);
  }

  @Override
  public Optional<Image> getFirstByItemId(Integer itemId) {
    return imageRepository.getFirstByItemId(itemId);
  }

  @Override
  public void delete(Image image) {
    imageRepository.delete(image);
  }

  @Override
  public List<Image> uploadImages(List<MultipartFile> images, Item item) {
    return images.stream().map(image -> {
              try {
                return new Image(fileUtils.uploadFile(image), item);
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            })
            .collect(Collectors.toList());
  }

  @Override
  public void deleteAndSaveImages(List<MultipartFile> images, Item oldItem) {
    List<Image> oldImage = oldItem.getImages();
    if (oldImage != null) {
      oldImage.forEach(t -> {
        try {
          fileUtils.deleteImageInCloudinary(t.getImageUrl());
          delete(t);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    }
    List<Image> newImages = uploadImages(images, oldItem);
    oldItem.setImages(newImages);
  }
}
