package com.shop.service;

import com.shop.entity.Role;

public interface RoleService {
	Role getRoleByName(String name);
	Role getRoleByUsername(String username);
}
