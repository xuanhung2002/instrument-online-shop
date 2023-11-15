package com.shop.initalization;

import com.shop.entity.*;
import com.shop.enums.OrderStatusEnum;
import com.shop.enums.PaymentMethodEnum;
import com.shop.enums.PaymentStatusEnum;
import com.shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    @Autowired
    PaymentStatusRepository paymentStatusRepository;

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
        createPaymentMethodIfNotFound();
        createOrderStatusIfNotFound();
        createPaymentStatusIfNotFound();
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
    private void createOrderStatusIfNotFound(){
        for(OrderStatusEnum status : OrderStatusEnum.values()){
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setOrderStatusEnum(status);
            if(!orderStatusRepository.existsByOrderStatusEnum(status)){
                orderStatusRepository.save(orderStatus);
            }
        }
    }

    private void createPaymentStatusIfNotFound(){
        for (PaymentStatusEnum status : PaymentStatusEnum.values()){
            PaymentStatus paymentStatus = new PaymentStatus();
            paymentStatus.setPaymentStatusEnum(status);
            if(!paymentStatusRepository.existsByPaymentStatusEnum(status)){
                paymentStatusRepository.save(paymentStatus);
            }
        }
    }

    private void createPaymentMethodIfNotFound(){
        for (PaymentMethodEnum status : PaymentMethodEnum.values()){
            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setPaymentMethodEnum(status);
            if(!paymentMethodRepository.existsByPaymentMethodEnum(status)){
                paymentMethodRepository.save(paymentMethod);
            }
        }
    }
}
