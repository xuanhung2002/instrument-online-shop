package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
