package org.example.userstest;

import io.restassured.response.Response;
import org.example.entities.Order;
import org.example.entities.enums.OrderStatus;
import org.example.steps.StoreServiceSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class StoreServiceTest {
    Order expectedOrder = createOrder();

    @Test
    public void createOrderTest(){
        Response responseCreatedOrder = StoreServiceSteps.createOrder(expectedOrder);
        Assert.assertEquals(responseCreatedOrder.getStatusCode(), 200,
                "Incorrect order status code");
        Assert.assertEquals(responseCreatedOrder.as(Order.class).getId(), expectedOrder.getId());
    }

    @Test
    public void getOrderByPetIdTest(){
        Response responseCreatedOrder = StoreServiceSteps.createOrder(expectedOrder);
        int createdOrderPetId = StoreServiceSteps.getOrder(expectedOrder.getPetId()).jsonPath().getInt("code");
        Assert.assertEquals(createdOrderPetId, expectedOrder.getPetId());
    }

    @Test
    public void deleteOrderTest(){
        Response responseCreatedOrder = StoreServiceSteps.createOrder(expectedOrder);
        Assert.assertEquals(responseCreatedOrder.getStatusCode(), 200,
                "Incorrect status code of response of user creation");
        StoreServiceSteps.deleteOrder(expectedOrder.getId());
        Response responseDeletedOrder = StoreServiceSteps.getOrder(expectedOrder.getPetId());
        Assert.assertEquals(responseDeletedOrder.getStatusCode(), 404,
                "Order not deleted or invalid Id");

    }

    private Order createOrder() {
        Random random = new Random();
        return new Order()
                .setId(random.nextInt(100)+1)
                .setPetId(1)
                .setQuantity(random.nextInt(10)+1)
                .setShipDate("2022-04-02T14:38:15.358Z")
                .setStatus(OrderStatus.PLACED.toString())
                .setComplete(true);
    }
}
