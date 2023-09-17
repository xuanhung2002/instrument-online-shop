package com.shop.initalization;

import com.shop.entity.Account;
import com.shop.entity.Role;
import com.shop.entity.User;
import com.shop.repository.AccountRepository;
import com.shop.repository.RoleRepository;
import com.shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    public Initializer(UserRepository userRepository, RoleRepository roleRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("USER");
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

        Account account = new Account();
        account.setUser(user);
        account.setUsername("admin");
        account.setPassword("admin");
        accountRepository.save(account);

        user.setAccount(accountRepository.findByUsername("admin"));

        userRepository.save(user);
    }
}
