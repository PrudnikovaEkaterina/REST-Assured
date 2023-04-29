package ru.prudnikova.tests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.generators.CreateUserModelGenerator;
import ru.prudnikova.models.CreateUserModel;
import ru.prudnikova.models.User;
import ru.prudnikova.models.UserData;

import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static ru.prudnikova.generators.helpers.CustomAllureListener.withCustomTemplates;
import static ru.prudnikova.specs.Specs.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReqresTestWithModel {

    @Test
    @Tag("Api")
    void getUserDataFromPage2() {
        UserData data = given()
                .spec(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpec200).extract().as(UserData.class);
        assertThat(data.getUser().stream().map(el -> el.getId()).collect(Collectors.toList()), hasItems(7, 8, 9, 10, 11, 12));
        assertThat(data.getUser().stream().map(User::getEmail).collect(Collectors.toList()), everyItem(endsWith("@reqres.in")));
        assertThat(data.getUser().stream().map(User::getFirstName).collect(Collectors.toList()), hasItems("Michael", "Lindsay", "Tobias", "Byron", "George", "Rachel"));
        assertThat(data.getUser().stream().map(User::getLastName).collect(Collectors.toList()), hasItems("Lawson", "Ferguson", "Funke", "Fields", "Edwards", "Howell"));
        assertThat(data.getUser().stream().map(User::getAvatar).collect(Collectors.toList()), hasItem("https://reqres.in/img/faces/7-image.jpg"));

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
        CreateUserModel userModel = CreateUserModelGenerator.generationUserCreateBody();
        given()
                .spec(requestSpec)
                .body(userModel)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .body("name", is(userModel.getFullName()), "job", is(userModel.getJob()));
    }

    @Test
    @Tag("Api")
    void updateUserId2() {
        CreateUserModel userModel = CreateUserModelGenerator.generationUserCreateBody();
        given()
                .spec(requestSpec)
                .body(userModel)
                .when()
                .put("/users/2")
                .then()
                .spec(responseSpec200)
                .body("name", is(userModel.getFullName()), "job", is(userModel.getJob()));
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
