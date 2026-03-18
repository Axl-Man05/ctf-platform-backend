package com.ctf.platform.service;

import com.ctf.platform.entity.User;
import com.ctf.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User registerUser(User newUser){

        if(userRepository.existsByEmail(newUser.getEmail())){
            throw new IllegalArgumentException("this Email is already in use");
        }

        newUser.setIsVerified(false);
        newUser.setVerificationCode(randomUUID().toString());


        return userRepository.save(newUser);
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
