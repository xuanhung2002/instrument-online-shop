package com.shop.dto;

import java.util.List;

import com.shop.entity.Image;

import jakarta.persistence.Lob;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {
    private Integer id;

    private String name;

    private String description;

    private Integer price;

    private String brandName;

    private String categoryName;

    private List<Image> images;

    private Integer inventoryQuantity;


    public void setInventoryQuantity(Integer inventoryQuantity) {
        if (inventoryQuantity == null) {
            this.inventoryQuantity = 0;
        }
        this.inventoryQuantity = inventoryQuantity;
    }


}
