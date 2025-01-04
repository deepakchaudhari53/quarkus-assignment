package com.deep.quarkus.exercise.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class FactControllerTest {

    @BeforeAll
    static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void testFetchFact() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when().get("/v1/facts")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetMeFact() {
        String shortenedUrl = "short-Url"; // Replace with a valid shortened URL
        RestAssured.given()
                .accept(ContentType.JSON)
                .when().get("/v1/facts/{shortenedUrl}", shortenedUrl)
                .then()
                .statusCode(404);
    }

    @Test
    public void testFetchAllFacts() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when().get("/v1/facts/getAllFacts")
                .then()
                .statusCode(200);
    }

    @Test
    public void testFactNotFound() {
        String shortenedUrl = "short-url"; // Replace with a valid shortened URL
        RestAssured.given()
                .accept(ContentType.JSON)
                .when().get("/v1/facts/{shortenedUrl}/redirect", shortenedUrl)
                .then()
                .statusCode(404);
    }

    @Test
    public void testStatistics() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when().get("/v1/facts/admin/statistics")
                .then()
                .statusCode(200);
    }
}
