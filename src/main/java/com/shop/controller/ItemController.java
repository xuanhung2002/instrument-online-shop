package com.shop.controller;

import com.shop.converter.Converter;
import com.shop.dto.ItemRequestDTO;
import com.shop.entity.Item;
import com.shop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    Converter converter;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "size", defaultValue = "2147483647") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "default") String sortBy) {

        return new ResponseEntity<>(itemService.getAll(pageNo, pageSize, sortBy).stream().map(converter::toItemDTO),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Integer id) {
        if (itemService.getItemById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This item is not existed");
        }
        return new ResponseEntity<>(converter.toItemDTO(itemService.getItemById(id)), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchItem(@RequestParam("searchKey") String searchKey){
        if(itemService.searchItemsByName(searchKey) != null){
            return ResponseEntity.status(HttpStatus.OK).body(itemService.searchItemsByName(searchKey));
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No have item with this search key");
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> save(@ModelAttribute ItemRequestDTO itemRequestDTO) {
        try {
            Item savedItem = itemService.addNewItem(itemRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(converter.toItemDTO(savedItem));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @ModelAttribute ItemRequestDTO itemRequestDTO) {
        try {
            Item updatedItem = itemService.updateItem(id, itemRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(converter.toItemDTO(updatedItem));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/")
    public ResponseEntity<?> getItemSByCategoryName(@RequestParam(value = "categoryName", required = true) String categoryName,
                                                    @RequestParam(name = "page", defaultValue = "0") Integer pageNo,
                                                    @RequestParam(name = "size", defaultValue = "5") Integer pageSize,
                                                    @RequestParam(name = "sortBy", defaultValue = "name-asc") String sortBy) {

        try {
            List<Item> items = itemService.getItemsByCategory(categoryName, pageNo, pageSize,sortBy);
            return ResponseEntity.status(HttpStatus.OK).body(items);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItemById(@PathVariable Integer id) {
        if (itemService.existedById(id)) {
            itemService.deleteItemById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Delete success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This item is not existed");
    }



}
