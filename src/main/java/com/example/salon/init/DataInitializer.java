package com.example.salon.init;

import com.example.salon.entity.Role;
import com.example.salon.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        createRole("ROLE_ADMIN");
        createRole("ROLE_EMPLOYEE");
        createRole("ROLE_CUSTOMER");
    }

    private void createRole(String name) {
        roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(new Role(name)));
    }
}
