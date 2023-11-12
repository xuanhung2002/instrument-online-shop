package com.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shop.entity.Category;
import com.shop.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findFirstByName(String name);

    List<Item> getItemsByCategory(Category category, Pageable pageable);

    @Query("SELECT i.inventoryQuantity FROM Item i WHERE i.id = :idItem")
    Integer getItemInventoryQuantityById(Integer idItem);
    

    @Query("SELECT i FROM Item i WHERE LOWER(i.name) like LOWER(CONCAT('%', :searchKey, '%'))")
    List<Item> searchItems(@Param("searchKey") String searchKey);
}
