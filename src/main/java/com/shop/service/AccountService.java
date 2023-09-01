package com.shop.service;

import com.shop.entity.Account;

public interface AccountService {
	boolean existsByUsername(String username);
	Account save(Account account);
}
