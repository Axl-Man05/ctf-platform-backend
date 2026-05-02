package com.ctf.platform.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RegisterResponseDTO {
    private UUID id;
    private String username;
    private String email;
    private boolean isVerified;
}
