package ru.armishev.rest_api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.armishev.rest_api.entities.News;
import ru.armishev.rest_api.storage.IStorageService;
import ru.armishev.rest_api.jpa.NewsJpa;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value="/api/v1/news")
public class NewsController {
    private NewsJpa newsJpa;
    private ru.armishev.rest_api.storage.IStorageService IStorageService;

    @Autowired
    public NewsController(NewsJpa newsJpa, IStorageService IStorageService) {
        this.newsJpa = newsJpa;
        this.IStorageService = IStorageService;
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

    @PostMapping(path = "/add",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public News addElem(@RequestPart(value="data", required = false) String data,
                           @RequestPart(value = "file",required = false) MultipartFile file) {
        ObjectMapper mapper = new ObjectMapper();
        News newNews = null;

        try {
            newNews =  mapper.readValue(data, News.class);


            String filePath = IStorageService.load(file);
            newNews.setImg(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newsJpa.save(newNews);
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public News updateElem(@PathVariable(value="id") long id,
                           @RequestPart(value="data", required = false) String data,
                           @RequestPart(value = "file",required = false) MultipartFile file) {

        newsJpa.findByIdAndIsDeletedFalse(id).orElseThrow(() -> {
            return new NoSuchElementException("Product not found");
        });

        ObjectMapper mapper = new ObjectMapper();
        News updateNews = new News();

        try {
            updateNews =  mapper.readValue(data, News.class);

            String filePath = IStorageService.load(file);
            updateNews.setImg(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
