package com.example.restapi.controller;

import com.example.restapi.dto.ProductDTO;
import com.example.restapi.exception.ProductNotFoundException;
import com.example.restapi.mapper.ProductMapper;
import com.example.restapi.model.Product;
import com.example.restapi.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;

    @GetMapping(value = "/products")
    List<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/products/{id}")
    ResponseEntity<Product> getById(@PathVariable("id") @Min(1) int id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No Product with ID : " + id));
        return ResponseEntity.ok().body(product);
    }

    @PostMapping(value = "/products")
    ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO inprod) {
        Product prd = ProductMapper.DtoToEntity(inprod);
        Product addedprd = productService.save(prd);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedprd.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/products/{id}")
    ResponseEntity<Product> updateProduct(@PathVariable("id") @Min(1) int id, @Valid @RequestBody ProductDTO inprod) {
        Product prd = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No Product with ID : " + id));

        Product newprd = ProductMapper.DtoToEntity(inprod);
        newprd.setId(prd.getId());
        productService.save(newprd);
        return ResponseEntity.ok().body(newprd);
    }

    @DeleteMapping(value="/products/{id}")
    ResponseEntity deleteProduct( @PathVariable("id") @Min(1) int id) {
        Product prd = productService.findById(id)
                .orElseThrow(()->new ProductNotFoundException("No Product with ID : " + id));
        productService.delete(prd.getId());
        return ResponseEntity.ok().body("Product with ID : " + id + " deleted with success!");
    }

}
