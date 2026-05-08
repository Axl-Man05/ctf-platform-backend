package com.ctf.platform.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Component
public class FlagHasher {
    public String hashFlag(String plainTextFlag){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(plainTextFlag.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(encodedhash);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Error al generar el hash de la flag ");
        }
    }

}
