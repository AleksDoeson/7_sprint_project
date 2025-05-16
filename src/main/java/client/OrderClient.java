package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given().baseUri(BASE_URI)
                .header("Content-type", "application/json")
                .body(order)
                .when().post("/api/v1/orders")
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrders() {
        return given().baseUri(BASE_URI)
                .when().get("/api/v1/orders")
                .then();
    }
}

