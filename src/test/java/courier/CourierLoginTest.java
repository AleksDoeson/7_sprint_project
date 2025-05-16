package courier;

import client.CourierClient;
import model.Courier;
import model.CourierLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier(generateLogin(), "password123", "John");
        courierClient.create(courier);
        courierId = courierClient.login(new CourierLogin(courier.getLogin(), courier.getPassword())).extract().path("id");
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    public void successfulLogin() {
        courierClient.login(new CourierLogin(courier.getLogin(), courier.getPassword()))
                .statusCode(200)
                .body("id", equalTo(courierId));
    }

    @Test
    public void loginWithWrongPassword() {
        courierClient.login(new CourierLogin(courier.getLogin(), "wrongpassword"))
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithoutPassword() {
        courierClient.login(new CourierLogin(courier.getLogin(), null))
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    private String generateLogin() {
        return "login" + System.currentTimeMillis();
    }
}

