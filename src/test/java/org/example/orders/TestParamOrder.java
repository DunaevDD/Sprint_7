package org.example.orders;

import io.restassured.response.ValidatableResponse;
import org.example.orders.Orders;
import org.example.orders.OrdersAssertions;
import org.example.orders.OrdersClient;
import org.example.orders.OrdersGenerator;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestParamOrder {
    private Orders fillOrder;
    private OrdersClient ordersClient;
    private OrdersAssertions checkOrder;
    private int orderId;
    @Parameterized.Parameter()
    public String[] color;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
        checkOrder = new OrdersAssertions();
        fillOrder = OrdersGenerator.fillOrder();
    }



    @Parameterized.Parameters()
    public static Object[] getData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK", "GRAY"}},
                {new String[]{}},
        };
    }

    @Test
    public void checkOrderWithColor() {
        fillOrder.setColor(color);
        ValidatableResponse createResponse = ordersClient.create(fillOrder);
        checkOrder.createdSuccessfully(createResponse);
        orderId = createResponse.extract().path("track");
    }

    @After
    public void cleanUp() {
        if (orderId > 0) {
            ordersClient.cancel(orderId);
        }
    }
}