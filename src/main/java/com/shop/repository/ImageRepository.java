package com.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByItemId(Integer itemId);

    Optional<Image> getFirstByItemId(Integer itemId);
}
