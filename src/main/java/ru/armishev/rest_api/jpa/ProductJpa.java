package ru.armishev.rest_api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.armishev.rest_api.entities.Product;

public interface ProductJpa extends JpaRepository<Product, Long> {
}
