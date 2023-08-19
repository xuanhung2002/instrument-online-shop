package com.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.entity.Category;
import com.shop.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
	Optional<Item> findFirstByName(String name);
	List<Item> findByCategory(Category category, Pageable pageable);
}
