package br.com.memo.controllers;

import br.com.memo.domain.user.User;
import br.com.memo.dto.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserResponse(user.getUsername()));
    }

}
