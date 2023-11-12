package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByUsername(String username);

    Account findByUsername(String username);
}
