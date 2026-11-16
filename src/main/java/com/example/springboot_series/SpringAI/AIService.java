package com.example.springboot_series.SpringAI;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    private final ChatClient chatClient;
    private final EmployeeTools employeeTools;

    public AIService(ChatClient chatClient, EmployeeTools employeeTools) {
        this.chatClient = chatClient;
        this.employeeTools = employeeTools;
    }

    public String generateResponse(String message){
        return chatClient.prompt()
                .user(message)
                .tools(employeeTools)
                .call()
                .content();
    }
}
