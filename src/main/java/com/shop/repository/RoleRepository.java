package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role findByName(@Param("name") String name);

    @Query("SELECT r FROM Role r JOIN r.users u ON u.account.username = :username")
    Role findByUsername(@Param("username") String username);
}
