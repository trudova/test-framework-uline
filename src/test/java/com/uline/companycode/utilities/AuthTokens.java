package com.uline.companycode.utilities;

import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthTokens {
    public static String getToken(String username, String password) {
        Map<String, String> loginCred = new HashMap<>();
        loginCred.put("email", username);
        loginCred.put("password", password);
        return given()
                .contentType(ContentType.JSON).body(loginCred)
                .when()
                .post("/auth/login")
                .path("token");

    }

    public static String jobToken;

    static {
        jobToken = getToken(ConfigurationReader.getProperty("user1Email"), ConfigurationReader.getProperty("user1Password"));

    }
}
