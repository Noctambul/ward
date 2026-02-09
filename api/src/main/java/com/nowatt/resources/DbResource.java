package com.nowatt.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import javax.sql.DataSource;
import java.sql.SQLException;

@Path("/db")
public class DbResource {

    @Inject
    DataSource dataSource;

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() throws SQLException {
        try (var c = dataSource.getConnection();
             var st = c.createStatement();
             var rs = st.executeQuery("SELECT 1")) {
            rs.next();
            return "ok " + rs.getInt(1);
        }
    }
}
