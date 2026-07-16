package dev.akashakp.portfolio.config;


import dev.akashakp.portfolio.user.User;
import dev.akashakp.portfolio.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    @Value("${app.admin.username}")
            private String adminUsername;

            @Value("${app.admin.password}")
                    private String adminPassword;

                    public DataSeeder(UserRepository users, PasswordEncoder encoder) {
            this.users = users;
            this.encoder = encoder;
            }

            @Override
            public void run(String... args) {
        if (!users.existsByUsername(adminUsername)) {
            users.save(User.builder()
                    .username(adminUsername)
                    .password(encoder.encode(adminPassword))
                    .role("ADMIN")
                            .build());
            System.out.println("[Seeder] Created admin user: " + adminUsername);
        }
    }
}
