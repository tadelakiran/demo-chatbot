package com.example.demochatbot.tools;

import com.example.demochatbot.model.OrderDetails;
import com.example.demochatbot.service.OrderService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class OrderTools {

    private final OrderService orderService;

    public OrderTools(OrderService orderService) {
        this.orderService = orderService;
    }

    @Tool("Get the current order status, customer details, product and payment information using an order ID")
    public OrderDetails getOrderStatus(
            @P("Customer order ID, for example ORD1001") String orderId) {

        System.out.println("TOOL CALLED -> getOrderStatus(" + orderId + ")");

        return orderService.getOrderStatus(orderId);
    }

    @Tool("Cancel an eligible customer order using its order ID. Orders that are shipped or delivered cannot be cancelled.")
    public OrderDetails cancelOrder(
            @P("Customer order ID, for example ORD1001") String orderId) {

        System.out.println("TOOL CALLED -> cancelOrder(" + orderId + ")");

        return orderService.cancelOrder(orderId);
    }

    @Tool("Update the delivery address of an eligible order. Requires the order ID and the new delivery address.")
    public OrderDetails updateDeliveryAddress(
            @P("Customer order ID") String orderId,
            @P("New delivery address provided by the customer") String newAddress) {

        System.out.println(
                "TOOL CALLED -> updateDeliveryAddress(" +
                        orderId + ", " + newAddress + ")"
        );

        return orderService.updateCustomerAddress(orderId, newAddress);
    }

}