package com.example.demochatbot.model;

public record OrderDetails(
        String id,
        String currentAddresss,
        String customerName,
        Long phoneNumber,
        String orderId,
        String orderStatus,
        String payementStatus,
        String product
) {}
