package com.example.banking;

import com.example.banking.entity.Role;
import com.example.banking.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BankingApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            Role role = new Role();
            role.setRoleId(501);
            role.setRoleName("ROLE_ADMIN");

            Role role1 = new Role();
            role1.setRoleId(502);
            role1.setRoleName("ROLE_USER");

            List<Role> roles = List.of(role, role1);
            List<Role> roles1 = this.roleRepository.saveAll(roles);

            roles1.forEach(r -> {
                System.out.println(r.getRoleName());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
