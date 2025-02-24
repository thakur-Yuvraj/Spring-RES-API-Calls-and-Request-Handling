package com.greetingapp.controller;


import com.greetingapp.model.Greeting;
import com.greetingapp.repository.GreetingRepository;
import com.greetingapp.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @Autowired
    private GreetingRepository greetingRepository;
    private final GreetingService greetingService;

    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
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

    // curl.exe -X GET http://localhost:8080/greeting
    @GetMapping
    public Map<String, String> getGreeting() {
        return Map.of("message", "Hello from GET");
    }

    // curl.exe -X GET http://localhost:8080/greeting/simple
    @GetMapping("/simple")
    public Map<String, String> getSimpleService() {
        String greet = greetingService.getSimpleGreeting();
        return Map.of("message", greet);
    }

    // curl.exe -X GET "http://localhost:8080/greeting/personalized?firstName=John&lastName=Doe"
    @GetMapping("/name")
    public Map<String, String> getGreetByNameService(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        String msg = greetingService.getGreetingByName(firstName, lastName);
        return Map.of("message", msg);
    }

    // curl.exe -X GET "http://localhost:8080/greeting/all"
    @GetMapping("/all")
    public List<Greeting> getAllGreeting() {

        return greetingService.getAllGreeting();
    }

    // curl.exe -X GET "http://localhost:8080/greeting/1"
    @GetMapping("/{id}")
    public ResponseEntity<Greeting> getGreeting(@PathVariable long id) {
        try {
            Greeting greeting = greetingService.findGreetingById(id);
            return ResponseEntity.ok(greeting);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // curl -X PUT -H "Content-Type: application/json" -d "{\"message\":\"Updated Hello World\"}" http://localhost:8080/api/greetings/1
    @PutMapping("/change/{id}")
    public ResponseEntity<Greeting> updateGreeting(@PathVariable long id, @RequestBody Greeting updatedGreet) {
        try {
            Greeting greeting = greetingService.update(id, updatedGreet.getMessage());
            return ResponseEntity.ok(greeting);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
