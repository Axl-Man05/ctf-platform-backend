package com.ctf.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "the username is required")
    @Size(min = 3, max = 20, message = "The username must be between 3 & 20 characters")
    private String username;
    @NotBlank(message = "The Email is required")
    @Size(message = "the email must be a valid format")
    private String email;
    @NotBlank(message = "The password is required")
    @Size(min = 6, message = "The password must be 6 characters at least")
    private String password;
}
