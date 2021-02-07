package ru.armishev.rest_api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.armishev.rest_api.entities.NewsComment;

public interface NewsCommentsJpa extends JpaRepository<NewsComment, Long> {
}
