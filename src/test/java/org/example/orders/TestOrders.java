package org.example.orders;

import io.restassured.response.ValidatableResponse;
import org.example.orders.Orders;
import org.example.orders.OrdersAssertions;
import org.example.orders.OrdersClient;
import org.example.orders.OrdersGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestOrders {
    private Orders defaultOrder;
    private OrdersClient ordersClient;
    private OrdersAssertions check;
    private int orderId;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
        check = new OrdersAssertions();
        defaultOrder = OrdersGenerator.fillOrder();
    }


    @Test
    public void checkListOfOrdersCanBeGot() {
        ValidatableResponse createResponse = ordersClient.create(defaultOrder);
        ValidatableResponse getResponse = ordersClient.returnListOfOrders();
        check.gettingSuccessfully(getResponse);
        orderId = createResponse.extract().path("track");
    }

    @After
    public void cleanUp() {
        ordersClient.cancel(orderId);
    }

}