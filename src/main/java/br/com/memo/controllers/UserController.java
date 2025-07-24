package br.com.memo.controllers;

import br.com.memo.dto.responses.LoginAndRegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("User information retrieved successfully");
    }
}
