package courier;

import client.CourierClient;
import io.qameta.allure.Step;
import model.Courier;
import model.CourierLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier(generateLogin(), "password123", "John");
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @Step("Проверка успешного создания курьера")
    public void createCourierSuccess() {
        courierClient.create(courier)
                .statusCode(201)
                .body("ok", equalTo(true));

        // Авторизуемся, чтобы получить id курьера для удаления
        courierId = courierClient.login(new CourierLogin(courier.getLogin(), courier.getPassword()))
                .statusCode(200)
                .extract().path("id");
    }

    @Test
    @Step("Нельзя создать двух одинаковых курьеров")
    public void createDuplicateCourier() {
        courierClient.create(courier)
                .statusCode(201);

        courierClient.create(courier)
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        courierId = courierClient.login(new CourierLogin(courier.getLogin(), courier.getPassword()))
                .extract().path("id");
    }

    @Test
    @Step("Нельзя создать курьера без обязательных полей")
    public void createCourierWithoutLogin() {
        Courier badCourier = new Courier(null, "password123", "John");

        courierClient.create(badCourier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    private String generateLogin() {
        return "login" + System.currentTimeMillis();
    }
}


