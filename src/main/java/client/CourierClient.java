package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierLogin;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given().baseUri(BASE_URI)
                .header("Content-type", "application/json")
                .body(courier)
                .when().post("/api/v1/courier")
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse login(CourierLogin login) {
        return given().baseUri(BASE_URI)
                .header("Content-type", "application/json")
                .body(login)
                .when().post("/api/v1/courier/login")
                .then();
    }

    @Step("Удаление курьера по id")
    public void delete(int courierId) {
        given().baseUri(BASE_URI)
                .when().delete("/api/v1/courier/" + courierId)
                .then().statusCode(200);
    }
}

