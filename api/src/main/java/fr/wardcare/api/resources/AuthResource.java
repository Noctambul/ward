package fr.wardcare.api.resources;

import fr.wardcare.api.dto.LoginRequest;
import fr.wardcare.api.dto.LoginResponse;
import fr.wardcare.api.services.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @PermitAll
    public LoginResponse login(LoginRequest req) {
        String token = this.authService.login(req.email(), req.password());
        return new LoginResponse(token);
    }
}
