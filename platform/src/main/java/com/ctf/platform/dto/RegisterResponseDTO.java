package com.ctf.platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDTO {
    private Long id;
    private String username;
    private String email;
    private boolean isVerified;
}
