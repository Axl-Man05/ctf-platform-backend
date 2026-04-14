package com.ctf.platform.controller;

import com.ctf.platform.dto.UserProfileDTO;
import com.ctf.platform.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;
    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> showProfile(){
        return ResponseEntity.ok(profileService.getMyProfile());
    }
}
