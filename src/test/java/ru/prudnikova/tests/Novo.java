package ru.prudnikova.tests;

import com.codeborne.selenide.Selenide;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.sql.Timestamp;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static ru.prudnikova.helpers.CustomAllureListener.withCustomTemplates;

public class Novo {
    @Test
    void test() {
        String body = "{ \"phone\": \"79085040701\" }";
        given()
                .filter(withCustomTemplates())
                .baseUri("https://novo-estate.ru/")
                .basePath("/api")
                .log().all()
                .contentType(ContentType.JSON)
                .cookie("developer_mode", "LadcZWB8a15L")
                .body(body)
                .when()
                .post("/auth/register")
                .then()
                .log().all()
                .statusCode(204);

        String body1 = "{ \"phone\": \"79085040701\", \"password\": \"909090\" }";

        String refreshToken = given()
                .filter(withCustomTemplates())
                .baseUri("https://novo-estate.ru/")
                .basePath("/api")
                .log().all()
                .contentType(ContentType.JSON)
                .cookie("developer_mode", "LadcZWB8a15L")
                .body(body1)
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(200).extract().cookie("refresh_token");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long timestampTime = timestamp.getTime();
        long session = timestampTime + 2592000;
        String sessionExpiresAt = String.valueOf(session).substring(0, 10);


       open("https://novo-estate.ru");
       Cookie cookie = new Cookie("refresh_token", refreshToken);
       getWebDriver().manage().addCookie(cookie);
       Selenide.localStorage().setItem("session_expires_at", sessionExpiresAt);
       Selenide.refresh();
       sleep(3000);

    }

//        given()
//                .filter(withCustomTemplates())
//                .baseUri("https://novo-estate.ru")
//                .basePath("/api")
//                .log().all()
//                .contentType(ContentType.JSON)
//                .header("Authorization", "Bearer " + token)
//                .when()
//                .post("/auth/me")
//                .then()
//                .log().all()
//                .statusCode(200);

//        Configuration.proxyEnabled = true;
//        SelenideProxyServer proxyServer= getSelenideProxy();
//        proxyServer.addRequestFilter("proxy-usages.request", ((request, contents, messageInfo) -> {
//            request.headers().add("Authorization", "Bearer "+token);
//        return null;
//        }));
//



}

//    @Test
//    void op() {
//        Configuration.proxyEnabled = true;
//        if (!WebDriverRunner.hasWebDriverStarted()) {
//            open();
//        }
//        SelenideProxyServer proxyServer = getSelenideProxy();
//        proxyServer.addRequestFilter("proxy-usages.request", (request, contents, messageInfo) -> {
//            request.headers().add("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzUxMiJ9.eyJpc3MiOiJodHRwczovL25vdm8tZXN0YXRlLnJ1L2FwaS9hdXRoL3JlZnJlc2giLCJpYXQiOjE2ODMyMzI5NTIsImV4cCI6MTY4MzIzNTg4OCwibmJmIjoxNjgzMjM0MDg4LCJqdGkiOiI3OHRpOGZyek11ZmdtZHR1IiwieHR5cGUiOiJhdXRoIiwicm9sZSI6MSwic3ViIjoiMTcyIn0.HncigvySdEbbEWLl69vv4Svns7zOt8QsFmuLXwJF9hq_dYKa2bPhklQ4KD8hMi1BIYbgpt7pykSF2q0Jme3ejnVPFafbl52F4kXyuUlayGzZwW7M3fV11G0VG46_R663zt3bFnJiWBVO_Lz0dyM_31zH07sXQZz2mZrJvCVZspqnALTasv-Wdqhm0qOAB2xiUuuVfs8uEPU7Zb7ZXtN948D7FoPkzWh3sgLfP9WPhYr6i7ai6LXuStedyxw_q47tuqwTAmhAcDHbADy5IMIURDm1dh9gbRs5I9scz1VwoVk7E5E2fM9Z4vj5QMNCluYHIgI_OKqilQGYKFRaGY1AbyYUKS0v7kr2i3GIm8PMWIEyxVAwAggqD_tQfLpu0gkju-jvlCWQxtzj8j0ouz-twZfVd7Oe0zod94CF8AhlzbAcddEHkD0A1ujZZh647uOslE51Y7fgmSphUWX9gGBG4eCEAiFLtMyeT2bMNtJjkCtck0RBrD1JKXXk8-dWlZ8GCM5LjFlzVx19iVEdacBaun7r9XyNwUg3u5-XklfEew4w-Y-1jBV9Ve7Fy0MYfHnHRjEqh_H6iJH31U4tixTi1rSX4QJSnqBMgNzSQLXFty6OcZhR1xQg6BBEXpfzQ6tdnY29XTuQ3HFaWr5muSBIevcbmliOWn20rJbB2MB_VDA");
//            return null;
//        });
//        Selenide.localStorage().setItem("session_expires_at", "1684932222");
//        open("https://novo-estate.ru/favorites");
//    }
//

