package ru.armishev.rest_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ru.armishev.rest_api.entities.Product;
import ru.armishev.rest_api.jpa.ProductJpa;

import java.util.*;

@RestController
@RequestMapping(value="/api/v1/product")
public class ProductController {
    private ProductJpa productJpa;

    @Autowired
    public ProductController(ProductJpa productJpa) {
        this.productJpa = productJpa;
    }

    @GetMapping("")
    public List<Product> getList() {
        return productJpa.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable(value="id") long id) {
        return productJpa.findById(id).orElseThrow(() -> {
                return new NoSuchElementException("Product not found");
        });
    }
}
