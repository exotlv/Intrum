package com.intrum.api;

import com.intrum.models.UserData;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiClient {

    private static final String BASE_URL = "https://gorest.co.in/public/v2/users";
    private static final String USERS_ENDPOINT = "/users";
    private static final String BEARER = "c8e5b47a0c7da8bf102572a70cb05283b07c69fe28663ddfef8238a41da62daa";


    public Response createUser(UserData user) {
            String userJson = String.format("{"
                    + "\"name\": \"%s\","
                    + "\"gender\": \"%s\","
                    + "\"email\": \"%s\","
                    + "\"status\": \"%s\""
                    + "}", user.getName(), user.getGender(), user.getEmail(), user.getStatus());

        return RestAssured.given()
                .header("Authorization", "Bearer " + BEARER)
                .header("Content-Type", "application/json")
                .body(userJson)
                .post(BASE_URL);
    }

    public Response getUser(String userId) {

        return RestAssured.given()
                .header("Authorization", "Bearer " + BEARER)
                .header("Content-Type", "application/json")
                .get(BASE_URL + "/" + userId);
    }

    public Response updateUser(String userId, UserData user) {
        String userJson = String.format("{"
                + "\"id\": ,"
                + "\"name\": \"%s\","
                + "\"gender\": \"%s\","
                + "\"email\": \"%s\","
                + "\"status\": \"%s\""
                + "}", user.getName(), user.getGender(), user.getEmail(), user.getStatus());

        return RestAssured.given()
                .header("Authorization", "Bearer " + BEARER)
                .header("Content-Type", "application/json")
//                .body(userJson)
                .put(BASE_URL + "/" + userId);
    }

    public Response deleteUser(String userId) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + BEARER)
                .header("Content-Type", "application/json")
                .delete(BASE_URL + "/" + userId);
    }
}
