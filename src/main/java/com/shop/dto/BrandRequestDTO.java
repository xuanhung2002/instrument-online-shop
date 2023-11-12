package com.shop.dto;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandRequestDTO {
  private String name;
  private String country;
  private MultipartFile logo;
}
