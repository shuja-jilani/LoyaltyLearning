package com.loyaltyProgramPOC.loyaltyProgram.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String sayHello(String name) {
        // Simulate some processing time
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Hello, " + name + "!";
    }
}