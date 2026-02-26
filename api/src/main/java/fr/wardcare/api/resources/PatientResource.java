package fr.wardcare.api.resources;

import fr.wardcare.api.dto.PatientResponse;
import fr.wardcare.api.services.PatientService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/v1/patients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class PatientResource {

    @Inject
    PatientService patientService;

    @GET
    public List<PatientResponse> getPatients() {
        return patientService.listPatients();
    }


}