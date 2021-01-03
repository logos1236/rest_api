package ru.armishev.rest_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.armishev.rest_api.entities.News;
import ru.armishev.rest_api.entities.Product;
import ru.armishev.rest_api.jpa.NewsJpa;
import ru.armishev.rest_api.jpa.ProductJpa;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value="/api/v1/news")
public class NewsService {
    private NewsJpa newsJpa;

    @Autowired
    public NewsService(NewsJpa newsJpa) {
        this.newsJpa = newsJpa;
    }

    @GetMapping("")
    public List<News> getList() {
        return newsJpa.findAll();
    }

    @GetMapping("/{id}")
    public News getById(@PathVariable(value="id") long id) {
        return newsJpa.findById(id).orElseThrow(() -> {
                return new NoSuchElementException("Product not found");
        });
    }
}
