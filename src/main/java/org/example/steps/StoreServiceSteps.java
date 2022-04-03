package org.example.steps;

import io.restassured.response.Response;
import org.example.entities.Order;
import org.example.service.StoreService;

import static org.example.service.uritemplate.StoreServiceUri.*;


public class StoreServiceSteps {
    private static final StoreService STORE_SERVICE = StoreService.getInstance();


    public static Response createOrder(Order order) {
        return STORE_SERVICE.postRequest(STORE_ORDER, order);
    }

    public static Response getOrderByPetId(int petId) {
        return STORE_SERVICE.getRequest(STORE_ORDER_BY_ID, petId);
    }

    public static void deleteOrderById(int orderId) {
        STORE_SERVICE.deleteRequest(STORE_ORDER_BY_ID, orderId);
    }

}
