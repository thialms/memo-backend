package br.com.memo.controllers;

import br.com.memo.domain.user.User;
import br.com.memo.dto.requests.LoginRequest;
import br.com.memo.dto.requests.RegisterRequest;
import br.com.memo.dto.responses.LoginAndRegisterResponse;
import br.com.memo.infra.security.TokenService;
import br.com.memo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        User user = this.userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(loginRequest.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginAndRegisterResponse(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        Optional<User> optionalUser = this.userRepository.findByEmail(registerRequest.email());
        if(optionalUser.isEmpty()){
            User user = new User();
            user.setUsername(registerRequest.name());
            user.setEmail(registerRequest.email());
            user.setPassword(passwordEncoder.encode(registerRequest.password()));
            this.userRepository.save(user);
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginAndRegisterResponse(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
