package org.example.userstest;

import io.restassured.response.Response;
import org.example.entities.Order;
import org.example.entities.enums.OrderStatus;
import org.example.log.Log;
import org.example.steps.StoreServiceSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class StoreServiceTest {
    Order order = createOrder();

    @Test
    public void createOrderTest() {
        Log.info("Order creation test");
        Response responseCreatedOrder = StoreServiceSteps.createOrder(order);
        Assert.assertEquals(responseCreatedOrder.getStatusCode(), 200,
                "Incorrect order status code");
        Assert.assertEquals(responseCreatedOrder.as(Order.class).getId(), order.getId(),
                "Incorrect order id");
        Log.info("Order was successfully created");
    }

    @Test
    public void getOrderByPetIdTest() {
        Log.info("Get order by pet id test");
        Response responseCreatedOrder = StoreServiceSteps.createOrder(order);
        Response responseGetOrderByPetId = StoreServiceSteps.getOrderByPetId(order.getPetId());
        Assert.assertEquals(responseGetOrderByPetId.getStatusCode(), 200,
                "Order not found or invalid id");
        Assert.assertEquals(responseGetOrderByPetId.as(Order.class).getId(), order.getId(),
                "Incorrect order id");
        Log.info("Order was successfully retrieved");
    }

    @Test
    public void deleteOrderTest() {
        Log.info("Delete order test");
        Response responseCreatedOrder = StoreServiceSteps.createOrder(order);
        StoreServiceSteps.deleteOrderById(order.getId());
        Response responseDeletedOrder = StoreServiceSteps.getOrderByPetId(order.getPetId());
        Assert.assertEquals(responseDeletedOrder.getStatusCode(), 404,
                "Order not deleted or invalid id");
        Log.info("Order was successfully deleted");
    }

    private Order createOrder() {
        Random random = new Random();
        return new Order()
                .setId(random.nextInt(10) + 1)
                .setPetId(random.nextInt(10) + 1)
                .setQuantity(1)
                .setShipDate("2022-04-02T14:38:15.358+0000")
                .setStatus(OrderStatus.PLACED.toString())
                .setComplete(true);
    }
}
