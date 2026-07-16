package dev.akashakp.portfolio.auth;


import dev.akashakp.portfolio.security.JwtService;
import dev.akashakp.portfolio.user.User;
import dev.akashakp.portfolio.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
        public class AuthController {

        public record RegisterRequest(String username, String password) {}
        public record LoginRequest(String username, String password) {}

        private final UserRepository users;
        private final PasswordEncoder encoder;
        private final AuthenticationManager authManager;
        private final JwtService jwtService;

        public AuthController(UserRepository users, PasswordEncoder encoder,
        AuthenticationManager authManager, JwtService jwtService) {
    this.users = users;
    this.encoder = encoder;
    this.authManager = authManager;
    this.jwtService = jwtService;
}

@PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
    if (req.username() == null || req.password() == null || req.username().isBlank() || req.password().isBlank()) {
        return ResponseEntity.badRequest().body(Map.of("error", "username and password required"));
    }
    if (users.existsByUsername(req.username())) {
        return ResponseEntity.status(409).body(Map.of("error", "user already exists"));
    }
    User u = User.builder()
            .username(req.username())
            .password(encoder.encode(req.password()))
            .role("ADMIN")
                    .build();
    users.save(u);
    return ResponseEntity.ok(Map.of("username", u.getUsername(), "role", u.getRole()));
}

@PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest req) {
    var authToken = new UsernamePasswordAuthenticationToken(req.username(), req.password());
    var auth = authManager.authenticate(authToken);
    UserDetails details = (UserDetails) auth.getPrincipal();
    String token = jwtService.generate(details);
    return ResponseEntity.ok(Map.of("token", token, "username", details.getUsername()));
}

@GetMapping("/me")
        public ResponseEntity<?> me(org.springframework.security.core.Authentication auth) {
    if (auth == null || !auth.isAuthenticated()) return ResponseEntity.status(401).build();
    return ResponseEntity.ok(Map.of("username", auth.getName(), "roles", auth.getAuthorities().toString()));
}
}
