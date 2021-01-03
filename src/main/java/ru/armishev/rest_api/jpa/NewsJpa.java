package ru.armishev.rest_api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.armishev.rest_api.entities.News;
import ru.armishev.rest_api.entities.Product;

public interface NewsJpa extends JpaRepository<News, Long> {
}
