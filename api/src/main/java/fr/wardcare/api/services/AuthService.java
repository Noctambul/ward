package fr.wardcare.api.services;

import fr.wardcare.api.entities.Medic;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

@ApplicationScoped
public class AuthService {

    @Inject
    JwtTokenService jwtTokenService;

    public String login(String email, String password) {
        Medic m = Medic.findByEmail(email);

        if (m == null)
            throw new WebApplicationException(401);

        if (!BcryptUtil.matches(password, m.password))
            throw new WebApplicationException(401);

        return jwtTokenService.createAccessToken(m.id, m.email);
    }
}
