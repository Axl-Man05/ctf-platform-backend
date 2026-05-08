package com.ctf.platform.controller;

import com.ctf.platform.dto.AuthResponseDTO;
import com.ctf.platform.dto.LoginDTO;
import com.ctf.platform.entity.Role;
import com.ctf.platform.entity.User;
import com.ctf.platform.service.CustomUserDetailsService;
import com.ctf.platform.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        User user = (User) customUserDetailsService.loadUserByUsername(loginDTO.getEmail());
        String jwt = jwtService.generateJwtToken(user);


        return ResponseEntity.ok(AuthResponseDTO.builder()
                .token(jwt)
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(Role::getRoleName).toList())
                .build());
    }
}
