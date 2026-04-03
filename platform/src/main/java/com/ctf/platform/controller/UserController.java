package com.ctf.platform.controller;

import com.ctf.platform.entity.User;
import com.ctf.platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> signUp(@RequestBody User user){
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
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
