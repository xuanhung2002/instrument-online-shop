package com.shop.initalization;

import com.shop.entity.Account;
import com.shop.entity.Role;
import com.shop.entity.User;
import com.shop.repository.AccountRepository;
import com.shop.repository.RoleRepository;
import com.shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Initializer(UserRepository userRepository, RoleRepository roleRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("USER");
        createRootUser();
    }

    private void createRoleIfNotFound(String roleName) {
        if (roleRepository.findByName(roleName) == null) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }

    private void createRootUser() {
        User user = new User();

        user.setName("ROOT ADMIN");
        user.setRole(roleRepository.findByName("ADMIN"));
        if (!accountRepository.existsByUsername("admin")) {
            Account account = new Account();
            account.setUser(user);
            account.setUsername("admin");
            account.setPassword(passwordEncoder.encode("admin12345"));
            accountRepository.save(account);
            user.setAccount(accountRepository.findByUsername("admin"));
            userRepository.save(user);
        }

    }
}
