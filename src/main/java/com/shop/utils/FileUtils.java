package com.shop.utils;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Component
public class FileUtils {

    @Autowired
    private Cloudinary cloudinary;

    @SuppressWarnings("rawtypes")
	public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url");
    }

    public void deleteImageInCloudinary(String url) throws IOException {
        int lastSlashIndex = url.lastIndexOf("/");
        int lastDotIndex = url.lastIndexOf(".");
        String publicId = url.substring(lastSlashIndex + 1, lastDotIndex);
        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        System.out.println("Kết quả xóa hình ảnh: " + result + " url: " + url);
    }

}
