package com.ctf.platform.controller;

import com.ctf.platform.dto.RegisterDTO;
import com.ctf.platform.dto.RegisterResponseDTO;
import com.ctf.platform.entity.User;
import com.ctf.platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> signUp(@Valid @RequestBody RegisterDTO registerDTO){
        RegisterResponseDTO responseDTO = userService.registerUser(registerDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/api/users/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(responseDTO);

    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String code){
        userService.verifyAccount(code);
        return ResponseEntity.ok("Account verified successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("The account has been successfully deleted");
    }
}
