package com.example.carbiz.controller;

import com.example.carbiz.dto.AuthResponse;
import com.example.carbiz.dto.LoginRequest;
import com.example.carbiz.entity.AppUser;
import com.example.carbiz.repository.AppUserRepository;
import com.example.carbiz.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final AppUserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(AuthenticationManager am, AppUserRepository ur,
                          PasswordEncoder pe, JwtService jwt) {
        this.authManager = am;
        this.userRepo = ur;
        this.encoder = pe;
        this.jwt = jwt;
    }

    /**
     * ✅ LOGIN (เข้าสู่ระบบ)
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.username(), req.password())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("invalid_credentials", "Invalid username or password"));
        }

        AppUser user = userRepo.findByUsername(req.username()).orElseThrow();
        String token = jwt.generateToken(user.getUsername(), user.getRole().name());
        return ResponseEntity.ok(new AuthResponse(token, user.getRole().name(), user.getUsername()));
    }

    /**
     * ✅ ME (ข้อมูลผู้ใช้ปัจจุบัน)
     */
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("unauthenticated", "User not authenticated"));
        }

        var user = userRepo.findByUsername(auth.getName()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("not_found", "User not found"));
        }

        return ResponseEntity.ok(new MeResponse(
                user.getUsername(),
                user.getRole().name(),
                user.getFullName(),
                user.getContact()
        ));
    }

    public record ErrorResponse(String code, String message) {}
    public record MeResponse(String username, String role, String fullname, String contact) {}
}
