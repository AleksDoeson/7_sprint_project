package order;

import client.OrderClient;
import model.Order;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class OrderCreateTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    public void createOrderWithOneColor() {
        Order order = new Order(
                "Иван", "Иванов", "ул. Ленина, 10", "4", "+79998887766",
                3, "2025-05-20", "Позвонить за час", List.of("BLACK")
        );

        orderClient.create(order)
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    public void createOrderWithTwoColors() {
        Order order = new Order(
                "Ольга", "Петрова", "пр-т Мира, 55", "12", "+79995554433",
                2, "2025-05-18", "Оставить у двери", List.of("BLACK", "GREY")
        );

        orderClient.create(order)
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    public void createOrderWithoutColor() {
        Order order = new Order(
                "Николай", "Сидоров", "ул. Гагарина, 1", "1", "+79991112233",
                1, "2025-05-19", "Без звонка", Collections.emptyList()
        );

        orderClient.create(order)
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    public void createOrderWithMissingRequiredFields() {
        Order order = new Order(
                "", "", "", "", "",
                0, "", "", List.of("BLACK")
        );

        orderClient.create(order)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания заказа"));
    }
}

