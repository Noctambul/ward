package fr.wardcare.api.services;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class JwtTokenService {

    public String createAccessToken(UUID medicId, String email) {
        return Jwt.upn(email).subject(medicId.toString()).sign();
    }
}
