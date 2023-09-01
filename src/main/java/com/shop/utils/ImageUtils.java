package com.shop.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

public class ImageUtils {
	 public byte[] compressImage(byte[] imageData) throws Exception {
	        // Read the original image
	        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));

	        // Create a ByteArrayOutputStream to hold the compressed image data
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();

	        // Compress the image to JPEG format with a default quality
	        ImageIO.write(originalImage, "jpg", baos);

	        // Return the compressed image data as byte array
	        return baos.toByteArray();
	    }
}
