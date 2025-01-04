package com.deep.quarkus.exercise.exception;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class FactNotFoundExceptionTest {

    @Test
    public void testFactNotFoundException() {
        String message = "Fact not found";
        FactNotFoundException exception = new FactNotFoundException(message);

        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getResponse().getStatus());
        Assertions.assertEquals(message, exception.getResponse().getEntity());
    }
}
