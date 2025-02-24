package com.greetingapp.controller;


import com.greetingapp.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingService greetingService;

    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    // curl.exe -X GET http://localhost:8080/greeting/simple
    @GetMapping("/simple")
    public Map<String, String> getSimpleService() {
        String message = greetingService.getSimpleGreeting();
        return Map.of("message", message);
    }

    // curl.exe -X GET http://localhost:8080/greeting
    @GetMapping
    public Map<String, String> getGreeting() {
        return Map.of("message", "Hello from GET");
    }

    // curl.exe -X POST http://localhost:8080/greeting -H "Content-Type: application/json" -d "{\"name\":\"John\"}"
    @PostMapping
    public Map<String, String> postGreeting(@RequestBody Map<String, String> payload) {
        return Map.of("message", "Hello from POST", "received", payload.getOrDefault("name", "unknown"));
    }

    // curl.exe -X PUT http://localhost:8080/greeting -H "Content-Type: application/json" -d "{\"name\":\"Jane\"}"
    @PutMapping
    public Map<String, String> putGreeting(@RequestBody Map<String, String> payload) {
        return Map.of("message", "Hello from PUT", "updated", payload.getOrDefault("name", "unknown"));
    }
    // curl.exe -X DELETE "http://localhost:8080/greeting?id=123"
    @DeleteMapping
    public Map<String, String> deleteGreeting(@RequestParam(value = "id", required = false) String id) {
        return Map.of("message", "Hello from DELETE", "deletedId", id != null ? id : "none");
    }
}
