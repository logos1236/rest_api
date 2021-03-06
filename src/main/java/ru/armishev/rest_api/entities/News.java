package ru.armishev.rest_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name="news")
public class News {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_id_seq")
    @SequenceGenerator(name = "news_id_seq", sequenceName = "news_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Name may not be empty")
    private String name;

    @Column(name = "preview_text")
    private String previewText;

    @Column(name = "text")
    private String text;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted = false;

    @Column(name = "img")
    private String img;

    @JsonIgnoreProperties(value = {"news"})
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "news")
    private List<NewsComment> listComment;

    public List<NewsComment> getListComment() {
        return listComment;
    }

    public void setListComment(List<NewsComment> listComment) {
        this.listComment = listComment;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public News() {
    }

    public News(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", previewText='" + previewText + '\'' +
                ", text='" + text + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
