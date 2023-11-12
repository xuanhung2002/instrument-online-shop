package com.shop.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequestDTO {
  private String name;
  private String description;
  private Integer price;
  private String brandName;
  private String categoryName;
  private Integer inventoryQuantity;
  private List<MultipartFile> images;
}
