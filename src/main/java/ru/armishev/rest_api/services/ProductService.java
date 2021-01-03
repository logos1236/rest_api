package ru.armishev.rest_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ru.armishev.rest_api.entities.Product;
import ru.armishev.rest_api.jpa.ProductJpa;

import java.util.*;

@RestController
@RequestMapping(value="/api/v1/product")
public class ProductService {
    private ProductJpa productJpa;

    @Autowired
    public ProductService(ProductJpa productJpa) {
        this.productJpa = productJpa;
    }

    @GetMapping("")
    public List<Product> getList() {
        return productJpa.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable(value="id") long id, WebRequest request) {
        return productJpa.findById(id).orElseThrow(() -> {
                return new NoSuchElementException("Product not found");
        });
    }
}
