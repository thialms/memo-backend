package br.com.memo.controllers;

import br.com.memo.domain.user.User;
import br.com.memo.dto.requests.LoginRequest;
import br.com.memo.dto.requests.RegisterRequest;
import br.com.memo.dto.responses.LoginAndRegisterResponse;
import br.com.memo.infra.security.TokenService;
import br.com.memo.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Autenticação", description = "Endpoints de login e registro")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Operation(summary = "Login do usuário")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Credenciais inválidas")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        User user = this.userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(loginRequest.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginAndRegisterResponse(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @Operation(summary = "Registrar novo usuário")
    @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Usuário já existe")
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
