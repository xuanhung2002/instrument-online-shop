package com.shop.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shop.dto.OrderRequestDTO;
import com.shop.dto.OrderResponseDTO;
import com.shop.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.shop.dto.CartItemDTO;
import com.shop.dto.ItemDTO;
import com.shop.service.BrandService;
import com.shop.service.CategoryService;
import com.shop.service.ImageService;

@Component
@Lazy
public class Converter {
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImageService imageService;


    public Item toItemEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        Optional<Brand> brandOpt = brandService.findOneByName(itemDTO.getBrandName());
        if(brandOpt.isPresent()){
            item.setBrand(brandOpt.get());
        }

        Optional<Category> categoryOPT = categoryService.findOneByName(itemDTO.getCategoryName());
        if(categoryOPT.isPresent()){
        item.setCategory(categoryOPT.get());
        }
        item.setInventoryQuantity(itemDTO.getInventoryQuantity());
        ;
        return item;
    }

    public ItemDTO toItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setBrandName(item.getBrand().getName());
        itemDTO.setCategoryName(item.getCategory().getName());
        itemDTO.setImages(imageService.findAllByItemId(item.getId()));
        itemDTO.setInventoryQuantity(item.getInventoryQuantity());
        return itemDTO;
    }


    public CartItemDTO toCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setItemId(cartItem.getId());
        Optional<Image> imageOptional = imageService.findFirstByItemId(cartItem.getItem().getId());
        if (imageOptional.isPresent()) {
            cartItemDTO.setItemImage(imageOptional.get().getImageUrl());
        } else {
            // Xử lý trường hợp không tìm thấy hình ảnh
            cartItemDTO.setItemImage(null);
        }

//		cartItemDTO.setItemImage(imageService.findFirstByItemId(cartItem.getItem().getId())
//																.get()
//																.getImageUrl());
        cartItemDTO.setNameItem(cartItem.getItem().getName());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setUnitPrice(cartItem.getItem().getPrice());
        cartItemDTO.setTotalPrice(cartItem.getQuantity() * cartItem.getItem().getPrice());

        return cartItemDTO;
    }


    public OrderResponseDTO toOrderResponseDTO(Order order) {

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setCustomerName(order.getCustomerName());
        orderResponseDTO.setCustomerPhone(order.getCustomerPhone());
        orderResponseDTO.setAddress(orderResponseDTO.getAddress());
        orderResponseDTO.setOrderDate(order.getOrderDate());
        orderResponseDTO.setTotalAmount(order.getTotalAmount());
        orderResponseDTO.setPaymentMethod(order.getPaymentMethod());
        orderResponseDTO.setPaymentStatus(order.getPaymentStatus());
        orderResponseDTO.setOrderStatus(order.getOrderStatus());
        orderResponseDTO.setDetailItemOrders(order.getDetailItemOrders());
        return orderResponseDTO;
    }

}
