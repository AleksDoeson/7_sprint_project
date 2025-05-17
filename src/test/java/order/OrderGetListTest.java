package order;

import client.OrderClient;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;

public class OrderListTest {

    private final OrderClient client = new OrderClient();

    @Test
    public void testOrderListNotEmpty() {
        client.getOrders().statusCode(200).body("orders.size()", greaterThan(0));
    }
}

