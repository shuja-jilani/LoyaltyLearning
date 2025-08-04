package com.loyaltyProgramPOC.loyaltyProgram.controller;

import com.loyaltyProgramPOC.loyaltyProgram.service.HelloService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam String name) {
        return helloService.sayHello(name);
    }
}