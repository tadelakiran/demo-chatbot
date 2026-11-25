package com.example.demochatbot.service;


import com.example.demochatbot.model.OrderDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class OrderService {

    private final RestClient restClient;

    private static final String API_URL =
            "https://6b056e5ddb01cf46360885f4.mockapi.io/agent/orderDetails";

    public OrderService() {
        this.restClient = RestClient.create();
    }

    // Get all orders from MockAPI
    public List<OrderDetails> getAllOrders() {

        return restClient.get()
                .uri(API_URL)
                .retrieve()
                .body(new ParameterizedTypeReference<List<OrderDetails>>() {});
    }

    // Find order using orderId
    public OrderDetails getOrderStatus(String orderId) {

        return getAllOrders()
                .stream()
                .filter(order -> order.orderId().equalsIgnoreCase(orderId))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Order not found: " + orderId)
                );
    }

    // Cancel an order
    public OrderDetails cancelOrder(String orderId) {

        OrderDetails order = getOrderStatus(orderId);

        if (order.orderStatus().equalsIgnoreCase("Shipped")
                || order.orderStatus().equalsIgnoreCase("Delivered")) {

            throw new RuntimeException(
                    "Cannot cancel an order that has already been shipped or delivered."
            );
        }

        OrderDetails updatedOrder = new OrderDetails(
                order.id(),
                order.currentAddresss(),
                order.customerName(),
                order.phoneNumber(),
                order.orderId(),
                "Cancelled",
                order.payementStatus(),
                order.product()
        );

        return restClient.put()
                .uri(API_URL + "/" + order.id())
                .body(updatedOrder)
                .retrieve()
                .body(OrderDetails.class);
    }

    public OrderDetails updateCustomerAddress(String orderId , String newAddress){
        OrderDetails  order = getOrderStatus(orderId);
        if(order.orderStatus().equalsIgnoreCase("Shipped") ||
        order.orderStatus().equalsIgnoreCase("Cancelled") ||
        order.orderStatus().equalsIgnoreCase("Delivered")){
            throw new RuntimeException("Delivery Address can not be changes once the order is Shipped or Cancelled or Delivered");
        }

        OrderDetails updatedAddress = new OrderDetails(
                order.id(),
                newAddress,
                order.customerName(),
                order.phoneNumber(),
                order.orderId(),
                order.orderStatus(),
                order.payementStatus(),
                order.product()
        );

        return restClient.put().uri(API_URL+"/"+order.id())
                .body(updatedAddress)
                .retrieve()
                .body(OrderDetails.class);

    }
}
