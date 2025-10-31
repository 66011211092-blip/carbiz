package com.example.carbiz.config;

import com.example.carbiz.entity.AppUser;
import com.example.carbiz.entity.Role;
import com.example.carbiz.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeedUsersConfig {

    @Bean
    CommandLineRunner seedUsers(AppUserRepository repo, PasswordEncoder enc) {
        return args -> {
            System.out.println("🔧 [Seed] Checking initial users...");

            int attempts = 0;
            while (attempts < 3) {
                try {
                    if (repo.count() > 0) {
                        System.out.println("ℹ️ [Seed] Users already exist, skipping seeding.");
                        return;
                    }

                    // ✅ Create default users (seed only once)
                    create(repo, enc, "admin", "admin123", Role.ADMIN, "System Admin", "090-000-0000");
                    create(repo, enc, "sales", "sales123", Role.SALES, "Sales User", "090-111-1111");
                    create(repo, enc, "maint", "maint123", Role.MAINTENANCE, "Maintenance", "090-222-2222");
                    create(repo, enc, "manager", "manager123", Role.MANAGER, "Manager User", "090-333-3333");

                    System.out.println("✅ [Seed] User seeding completed successfully.");
                    return;

                } catch (Exception e) {
                    attempts++;
                    System.err.println("⚠️ [Seed] Attempt " + attempts + " failed: " + e.getMessage());
                    Thread.sleep(2000);
                }
            }
            System.err.println("❌ [Seed] Failed to seed users after 3 attempts.");
        };
    }

    private void create(AppUserRepository repo, PasswordEncoder enc,
                        String username, String rawPassword, Role role,
                        String fullName, String contact) {
        if (repo.findByUsername(username).isPresent()) {
            System.out.println("ℹ️ [Seed] User already exists: " + username);
            return;
        }

        AppUser u = new AppUser();
        u.setUsername(username);
        u.setPassword(enc.encode(rawPassword));
        u.setRole(role);
        u.setFullName(fullName);
        u.setContact(contact);
        repo.save(u);

        System.out.println("✅ [Seed] Created user: " + username + " (" + role + ")");
    }
}
