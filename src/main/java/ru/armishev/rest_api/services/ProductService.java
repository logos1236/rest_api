package ru.armishev.rest_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.armishev.rest_api.entities.Product;
import ru.armishev.rest_api.jpa.ProductJpa;

import java.util.*;

@RestController
@RequestMapping(value="/api/product")
public class ProductService {
    @Autowired
    private ProductJpa productJpa;

    @GetMapping("")
    public List<Product> getList() {
        return productJpa.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable(value="id") long id) {
        return productJpa.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
    }
}
