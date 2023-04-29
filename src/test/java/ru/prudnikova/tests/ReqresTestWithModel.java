package ru.prudnikova.tests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.prudnikova.generators.CreateUserModelGenerator;
import ru.prudnikova.models.CreateUserModel;
import ru.prudnikova.models.User;
import ru.prudnikova.models.UserData;

import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static ru.prudnikova.specs.Specs.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReqresTestWithModel {

    @Test
    @Tag("Api")
    void getUserData() {
        UserData data = step("Make request to get list of users from page 2", () -> given()
                .spec(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpec200).extract().as(UserData.class));
        step("Verify users id ", () ->
                assertThat(data.getUser().stream().map(el -> el.getId()).collect(Collectors.toList()), hasItems(7, 8, 9, 10, 11, 12)));
        step("Verify users email", () ->
                assertThat(data.getUser().stream().map(User::getEmail).collect(Collectors.toList()), everyItem(endsWith("@reqres.in"))));
        step("Verify users firstName", () ->
                assertThat(data.getUser().stream().map(User::getFirstName).collect(Collectors.toList()), hasItems("Michael", "Lindsay", "Tobias", "Byron", "George", "Rachel")));
        step("Verify users lastName", () ->
                assertThat(data.getUser().stream().map(User::getLastName).collect(Collectors.toList()), hasItems("Lawson", "Ferguson", "Funke", "Fields", "Edwards", "Howell")));
        step("Verify users avatar", () ->
                assertThat(data.getUser().stream().map(User::getAvatar).collect(Collectors.toList()), hasItem("https://reqres.in/img/faces/7-image.jpg")));
    }

    @Test
    @Tag("Api")
    void getUnknownUser() {
        step("Make request to to get unknown user", () -> given()
                .spec(requestSpec)
                .when()
                .get("/unknown/23")
                .then()
                .spec(responseSpec)
                .statusCode(404));
    }

    @Test
    @Tag("Api")
    void createUser() {
        CreateUserModel body = CreateUserModelGenerator.generationUserCreateBody();
        CreateUserModel user = step("Make request to create user", () -> given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec)
                .statusCode(201).extract().as(CreateUserModel.class));
        step("Verify user fullName", () ->
                assertThat(user.getFullName(), is(body.getFullName())));
        step("Verify user job", () ->
                assertThat(user.getJob(), is(body.getJob())));
    }

    @Test
    @Tag("Api")
    void updateUserId2() {
        CreateUserModel body = CreateUserModelGenerator.generationUserCreateBody();
        CreateUserModel user = step("Make request to update user", () -> given()
                .spec(requestSpec)
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .spec(responseSpec200).extract().as(CreateUserModel.class));
        step("Verify user fullName", () ->
                assertThat(user.getFullName(), is(body.getFullName())));
        step("Verify user job", () ->
                assertThat(user.getJob(), is(body.getJob())));
    }

    @Test
    @Tag("Api")
    void deleteUser() {
        step("Make request to to delete user with id 2", () -> given()
                .spec(requestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(204)
                .body(Matchers.anything()));

    }

}
