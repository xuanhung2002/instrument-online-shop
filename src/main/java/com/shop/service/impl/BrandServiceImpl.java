package com.shop.service.impl;

import com.shop.dto.BrandRequestDTO;
import com.shop.entity.Brand;
import com.shop.entity.Image;
import com.shop.repository.BrandRepository;
import com.shop.service.BrandService;
import com.shop.service.ImageService;
import com.shop.utils.FileUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;
    @Autowired
    FileUtils fileUtils;
    @Autowired
    ImageService imageService;

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> getBrandByBrandName(String brandName) {
        return brandRepository.findFirstByName(brandName);
    }

    @Override
    public Optional<Brand> getBrandById(Integer id) {
        return brandRepository.findById(id);
    }

    @Override
    public Brand updateBrand(Integer id, BrandRequestDTO brandRequestDTO) throws IOException {
        Optional<Brand> brandOpt = getBrandById(id);
        if (brandOpt.isPresent()) {
            Brand oldBrand = brandOpt.get();
            oldBrand.setName(brandRequestDTO.getName());
            oldBrand.setCountry(brandRequestDTO.getCountry());

            if (oldBrand.getLogo() != null) {
                Image existedImage = oldBrand.getLogo();
                fileUtils.deleteImageInCloudinary(existedImage.getImageUrl());
                existedImage.setImageUrl(fileUtils.uploadFile(brandRequestDTO.getLogo()));
                imageService.save(existedImage);
            } else {
                Image imageToSave = new Image(fileUtils.uploadFile(brandRequestDTO.getLogo()), oldBrand);
                oldBrand.setLogo(imageToSave);
                imageService.save(imageToSave);
            }
            return oldBrand;
        } else {
            throw new EntityNotFoundException("This brand is not existed");
        }
    }

    @Override
    public Brand addNewBrand(BrandRequestDTO brandRequestDTO) throws IOException {
        Optional<Brand> brandOpt = getBrandByBrandName(brandRequestDTO.getName());
        if (!brandOpt.isPresent()) {
            Brand brand = new Brand();
            brand.setName(brandRequestDTO.getName());
            brand.setCountry(brandRequestDTO.getCountry());
            save(brand);

            Image logoToSave = new Image(fileUtils.uploadFile(brandRequestDTO.getLogo()), brand);
            imageService.save(logoToSave);

            brand.setLogo(logoToSave);

            return brand;
        } else {
            throw new EntityNotFoundException("Name brand have already existed");
        }
    }

}
