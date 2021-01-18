package ru.armishev.rest_api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.armishev.rest_api.entities.News;

import java.util.List;
import java.util.Optional;

public interface NewsJpa extends JpaRepository<News, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update News n set n.isDeleted = true where n.id = :id")
    int safeDelete(@Param("id") long id);

    List<News> findAllByIsDeletedFalseOrderByIdAsc();

    Optional<News> findByIdAndIsDeletedFalse(long id);
}
