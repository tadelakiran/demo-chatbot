package com.example.demochatbot.controller;

import com.example.demochatbot.service.ChatwithAI;
import com.example.demochatbot.service.OrderSupportAIAgent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("https://hoppscotch.io/")

public class AiController {

    private ChatwithAI chatwithAI;
    private final OrderSupportAIAgent orderSupportAIAgent;

    public AiController(ChatwithAI chatwithAI, OrderSupportAIAgent orderSupportAIAgent){
        this.chatwithAI=chatwithAI;
        this.orderSupportAIAgent = orderSupportAIAgent;
    }
    @GetMapping
    public String chat(@RequestParam String message){
        return chatwithAI.chat(message);
    }

    @GetMapping("/order")
    public String orderSupport(@RequestParam String message) {
        return orderSupportAIAgent.chat(message);
    }
}
