package com.shop.controller;
import java.io.IOException;
import java.util.List;
import com.shop.dto.BrandRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shop.entity.Brand;
import com.shop.service.BrandService;


@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Brand>> getAll() {
        return new ResponseEntity<>(brandService.getAll(), HttpStatus.OK);
    }

    @PostMapping( "/add")
    public ResponseEntity<?> save(@ModelAttribute BrandRequestDTO brandRequestDTO) throws IOException {
        try {
            Brand addedBrand = brandService.addNewBrand(brandRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(addedBrand);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @PutMapping( "/update/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Integer id,
                                        @ModelAttribute BrandRequestDTO brandRequestDTO) throws IOException {
            try {
                Brand updateBrand = brandService.updateBrand(id, brandRequestDTO);
                return ResponseEntity.status(HttpStatus.OK).body(updateBrand);
            }
            catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
    }
}
