package ru.prudnikova.tests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.models.User;
import ru.prudnikova.models.UserData;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static ru.prudnikova.specs.Specs.*;

public class ReqresTestWithModel {

    @Test
    @Tag("Api")
    void checkUserWithsId7() {
      UserData data = given()
                .spec(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpec200).extract().as(UserData.class);

        int [] mas = data.getUser().stream().map(el->el.getId()).collect(Arrays.toString());
    }

    @Test
    @Tag("Api")
    void checkSingleResourceNotFound() {
        given()
                .spec(requestSpec)
                .when()
                .get("/unknown/23")
                .then()
                .spec(responseSpec)
                .statusCode(404);
    }

    @Test
    @Tag("Api")
    void createUser() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .body("name", is("morpheus"),"job", is("leader"));
    }

    @Test
    @Tag("Api")
    void updateUser2() {
        String body = "{ \"name\": \"kate\", \"job\": \"qa\" }";
        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .spec(responseSpec200)
                .body("name", is("kate"),"job", is("qa"));
    }

    @Test
    @Tag("Api")
    void deleteUser2() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(204)
                .body(Matchers.anything());

    }

}
