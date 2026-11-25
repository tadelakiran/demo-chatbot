package com.example.demochatbot.service;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface ChatwithAI {
    String chat(String message);
}
