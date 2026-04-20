package com.ctf.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HintRequestDTO {
    @NotBlank(message = "the hint text can't be empty")
    private String hintText;

    @NotNull(message = "The challenge ID is required")
    private Long challengeId;

}
