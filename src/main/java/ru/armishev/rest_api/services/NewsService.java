package ru.armishev.rest_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.armishev.rest_api.entities.News;
import ru.armishev.rest_api.jpa.NewsJpa;

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
        return newsJpa.findAllByIsDeletedFalseOrderByIdAsc();
    }

    @GetMapping("/{id}")
    public News getById(@PathVariable(value="id") long id) {
        return newsJpa.findByIdAndIsDeletedFalse(id).orElseThrow(() -> {
                return new NoSuchElementException("Product not found");
        });
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public News addElem(@RequestBody News newNews) {
        return newsJpa.save(newNews);
    }

    @PutMapping("/{id}")
    public News updateElem(@PathVariable(value="id") long id, @RequestBody News updateNews) {
        newsJpa.findByIdAndIsDeletedFalse(id).orElseThrow(() -> {
            return new NoSuchElementException("Product not found");
        });

        updateNews.setId(id);

        return newsJpa.save(updateNews);
    }

    @DeleteMapping("/{id}")
    public void deleteElem(@PathVariable(value="id") long id) {
        newsJpa.findById(id).orElseThrow(() -> {
            return new NoSuchElementException("Product not found");
        });

        newsJpa.safeDelete(id);
    }

}
