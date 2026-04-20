package com.ctf.platform.service;

import com.ctf.platform.dto.RegisterDTO;
import com.ctf.platform.dto.RegisterResponseDTO;
import com.ctf.platform.entity.User;
import com.ctf.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public RegisterResponseDTO registerUser(RegisterDTO registerDTO){

        User user = User.builder()
                .userName(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .isVerified(false)
                .build();

        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("this Email is already in use");
        }
        User savedUser = userRepository.save(user);

        return RegisterResponseDTO.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .isVerified(savedUser.getIsVerified())
                .build();
    }

    public User deleteUser(Long id){
       User user = userRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("User not found"));
       userRepository.delete(user);
       return user;
    }

    public User verifyAccount(String code){
        // orElseThrow pregunta si el valor es null, y si no lo es lo hace un get
        //ejemplo con orElseThrow()
        //busca, valida y extrae
        User user = userRepository.findByVerificationCode(code).
                orElseThrow(() -> new IllegalArgumentException("Wrong verification code"));

        user.setIsVerified(true);
        user.setVerificationCode(null);
        return userRepository.save(user);


    //ejemplo sin orElseThrow (hacen lo mismo pero uno es mas eficiente)
/*
        Optional<User> userOptional = userRepository.findByVerificationCode(code);

        if(userOptional.isEmpty()){
               throw new IllegalArgumentException("wrong verification code");
        }
        User user = userOptional.get();
        user.setIsVerified(true);
        user.setVerificationCode(null);

        return userRepository.save(user);
 */
    }
}
