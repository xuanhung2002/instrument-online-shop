package com.shop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

}
