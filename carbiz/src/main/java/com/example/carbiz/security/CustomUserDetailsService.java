package com.example.carbiz.security;

import com.example.carbiz.entity.AppUser;
import com.example.carbiz.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AppUserRepository repo;

    public CustomUserDetailsService(AppUserRepository repo){ this.repo = repo; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("[AUTH] Loading user: {}", username);

        AppUser u = repo.findByUsername(username).orElseThrow(() -> {
            log.warn("[AUTH] User not found: {}", username);
            return new UsernameNotFoundException("User not found");
        });

        String hash = u.getPassword();
        String prefix = (hash != null && hash.length() >= 4) ? hash.substring(0, 4) : "null";
        int len = (hash == null) ? 0 : hash.length();

        log.debug("[AUTH] Found user: {}, role={}, hashPrefix={}, hashLen={}",
                u.getUsername(), u.getRole(), prefix, len);

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()))
        );
    }
}
