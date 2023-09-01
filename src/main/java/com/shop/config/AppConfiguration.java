package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class AppConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }
  
    @Bean
    public Cloudinary cloudinary() {
    	Cloudinary c = new Cloudinary(ObjectUtils.asMap(
    			  "cloud_name", "dyk0chowa",
    			  "api_key", "213193632662326",
    			  "api_secret", "vara_B0ObMArr0JZxhueec020Gk"));
    	
    	return c;
    	}

    
}
