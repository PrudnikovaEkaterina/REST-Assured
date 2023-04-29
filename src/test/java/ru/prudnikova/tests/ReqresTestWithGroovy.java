package ru.prudnikova.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItems;
import static ru.prudnikova.specs.Specs.*;

public class ReqresTestWithGroovy {

    @Test
    @Tag("Api")
    void getListUsersFromPage2() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpec200)
                .body("page", is(2))
                .body("per_page", is(6))
                .body("data.id.flatten()", hasItems(7, 8, 9, 10, 11, 12))
                .body("data.findAll{it.id>11}.id.flatten()", hasItems(12))
                .body("data.id.flatten().first()", is(7))
                .body("data.id.flatten().getAt(2)", is(9))
                .body("data.email.flatten()", hasItems("michael.lawson@reqres.in", "lindsay.ferguson@reqres.in", "tobias.funke@reqres.in",
                        "byron.fields@reqres.in", "george.edwards@reqres.in", "rachel.howell@reqres.in"))
                .body("data.email.flatten().", everyItem(endsWith("@reqres.in")))
                .body("data.first_name.flatten().", hasItems("Michael", "Lindsay", "Tobias", "Byron", "George", "Rachel"))
                .body("data.last_name.flatten().", hasItems("Lawson", "Ferguson", "Funke", "Fields", "Edwards", "Howell"))
                .body("data.avatar.flatten().", hasItem("https://reqres.in/img/faces/7-image.jpg"))
                .body(matchesJsonSchemaInClasspath("shemes/usersListResponseSchema.json"));
    }

}
