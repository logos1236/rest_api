package ru.armishev.rest_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.armishev.rest_api.entities.News;
import ru.armishev.rest_api.entities.NewsComment;
import ru.armishev.rest_api.jpa.NewsCommentsJpa;
import java.io.IOException;

@RestController
@RequestMapping(value="/api/v1/news-comments")
public class CommentsController {
    private NewsCommentsJpa newsCommentsJpa;

    @Autowired
    public CommentsController(NewsCommentsJpa newsCommentsJpa) {
        this.newsCommentsJpa = newsCommentsJpa;
    }

    @PostMapping(path = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public NewsComment addElem(@RequestPart(value="data", required = false) String data) {
        ObjectMapper mapper = new ObjectMapper();
        NewsComment newsComment = null;

        try {
            newsComment =  mapper.readValue(data, NewsComment.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return newsCommentsJpa.save(newsComment);
    }
}
