package com.example.carbiz;

import com.example.carbiz.entity.AppUser;
import com.example.carbiz.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CarbizApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbizApplication.class, args);
    }

    /**
     * ✅ ตั้งรหัส admin เป็น 123456 ทุกครั้งที่ start (ล็อกค้างไว้)
     */
    @Bean
    CommandLineRunner lockAdminPassword(AppUserRepository repo, PasswordEncoder enc) {
        return args -> {
            repo.findByUsername("admin").ifPresent(admin -> {
                String newPassword = "123456";
                String encoded = enc.encode(newPassword);

                // ตรวจว่ารหัสเก่าไม่ตรงกับ 123456 แล้วค่อยอัปเดต
                if (!enc.matches(newPassword, admin.getPassword())) {
                    admin.setPassword(encoded);
                    repo.save(admin);
                    System.out.println("🔒 [Admin] Password reset to 123456 (locked)");
                } else {
                    System.out.println("ℹ️ [Admin] Password already locked as 123456");
                }
            });
        };
    }
}
