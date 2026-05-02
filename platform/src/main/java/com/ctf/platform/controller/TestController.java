package com.ctf.platform.controller;


import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Profile("dev")
public class TestController {

    @GetMapping("/ping")
    public String ping(){
        return "Acceso autorizado";
    }

}
