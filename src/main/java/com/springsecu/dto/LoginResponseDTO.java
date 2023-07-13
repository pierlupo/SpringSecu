package com.springsecu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private String token;

    private Integer id;

    private String name;

    private String role;


}
