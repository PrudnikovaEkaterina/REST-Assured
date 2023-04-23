package ru.prudnikova;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

import static io.restassured.RestAssured.given;

public class ReqresTest {

    @BeforeEach
    public void before() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    @Tag("Api")
    void checkUserWithsId7() {
        given()
                .when()
                .get("/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is(2))
                .body("per_page", is(6))
                .body("data[0].id", is(7))
                .body("data[0].email", is("michael.lawson@reqres.in"))
                .body("data[0].first_name", is("Michael"))
                .body("data[0].last_name", is("Lawson"))
                .body("data[0].avatar", is("https://reqres.in/img/faces/7-image.jpg"))
                .body(matchesJsonSchemaInClasspath("usersListResponseSchema.json"));

    }

    @Test
    @Tag("Api")
    void checkSingleResourceNotFound() {
        given()
                .log().uri()
                .when()
                .get("/unknown/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);

    }

    @Test
    @Tag("Api")
    void createUser() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .log().all()
                .body(body)
                .contentType(JSON)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"),"job", is("leader"));
    }

    @Test
    @Tag("Api")
    void updateUser2() {
        String body = "{ \"name\": \"kate\", \"job\": \"qa\" }";
        given()
                .log().all()
                .body(body)
                .contentType(JSON)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("kate"),"job", is("qa"));
    }

    @Test
    @Tag("Api")
    void deleteUser2() {
        given()
                .log().all()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204)
                .body(Matchers.anything());

    }

}
