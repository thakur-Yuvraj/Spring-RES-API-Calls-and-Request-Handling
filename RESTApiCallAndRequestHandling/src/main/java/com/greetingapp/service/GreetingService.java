package com.greetingapp.service;
import com.greetingapp.model.Greeting;
import com.greetingapp.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GreetingService {

    @Autowired
    GreetingRepository greetingRepository;

    public String getSimpleGreeting() {
            return "Hello Spring Boot greeting";
    }

    public String getGreetingByName(String firstName, String lastName) {
        String msg;
        if (firstName != null && lastName != null) {
            msg = "Hello, "+ firstName + " " + lastName;
        }else
        if (firstName != null) {
            msg = "Hello, "+ firstName;
        }else
        if (lastName != null) {
            msg = "Hello, "+ lastName;
        }else msg = "Hello, nameless";

        // save the greeting repository
        greetingRepository.save(new Greeting(msg));
        return msg;
    }

    public Greeting findGreetingById(long id) {
        return greetingRepository.findById(id).orElseThrow(() -> new RuntimeException("Greeting not found by id : "+ id));
    }

    public List<Greeting> getAllGreeting() {
        return greetingRepository.findAll();
    }

    public Greeting update(long id, String newMsg) {
        Greeting greeting = greetingRepository.findById(id).orElseThrow(() -> new RuntimeException("Greeting not found by id : "+ id));
        greeting.setMessage(newMsg);
        return greetingRepository.save(greeting);
    }

    public void deleteGreeting(long id) {
        if (!greetingRepository.existsById(id)) {
            throw new RuntimeException("Greeting with id : " + id + " not found");
        }
        greetingRepository.deleteById(id);
    }
}

