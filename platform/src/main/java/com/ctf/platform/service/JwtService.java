package com.ctf.platform.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;
    private final int jwtExpirationMs = 86400000;

    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(UserDetails userDetails){

        return Jwts.builder()
                .subject((userDetails.getUsername()))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token){
        try{
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload().getSubject();

        }catch (ExpiredJwtException e){
            System.out.println("El token ha expirado");
        }catch (SignatureException e){
            System.out.println("Firma del token invalida");
        }catch (MalformedJwtException e){
           System.out.println("formato del token incorrecto");
        }catch (JwtException | IllegalArgumentException e){
            System.out.println("Error al procesar el token: " + e.getMessage());
        }
        return null;
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String email = extractEmail(token);
        return userDetails.getUsername().equals(email);
    }


}
