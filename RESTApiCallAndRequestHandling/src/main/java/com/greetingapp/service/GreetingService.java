package com.greetingapp.service;
import org.springframework.stereotype.Service;


@Service
public class GreetingService {

    public String getSimpleGreeting() {
            return "Hello Spring Boot greeting";
    }
}

