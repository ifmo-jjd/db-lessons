package ru.ifmo.db.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import org.eclipse.persistence.internal.jpa.rs.metadata.model.Link;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Nomination extends BaseId {

    private String title;
    private LocalDate date;

    @ManyToMany
    private List<Article> articles;

    public Nomination() {
        date = LocalDate.now();
        articles = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
