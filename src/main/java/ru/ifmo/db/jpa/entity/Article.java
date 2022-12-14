package ru.ifmo.db.jpa.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity // TODO:: требования к Entity классам
// задать имя таблице, установить индексы, связи с другими таблицами
@Table(name = "tb_articles") // если класс называется User или Group,
// необходимо задать новое имя таблице
@NamedNativeQueries({ // SQL - в запросе используются названия из БД
        @NamedNativeQuery(name = "get_all.native",
                query = "SELECT id, title, article_text, created FROM tb_articles",
                resultClass = Article.class)
        /*, следующий @NamedNativeQuery */
})
@NamedQueries({ // JPQL - в запросе используются названия из программы
        @NamedQuery(name = "get_all.jpql",
                query = "SELECT a FROM Article a"),
        @NamedQuery(name = "get_by_title.jpql",
                query = "SELECT a FROM Article a WHERE a.title = :title")
})
public class Article {

    @Id // столбец - первичный ключ, уникальный идентификатор записи
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SERIAL
    private int id;

    @Column(length = 100, unique = true, nullable = false)
    private String title;

    @Column(name = "article_text", columnDefinition = "TEXT")
    private String text;

    // если используется java-persistence-api,
    // то вместо пакета java.time используются Date и Calendar

    // в таблице будет создан столбец created
    private LocalDateTime created;

    public Article() {
        created = LocalDateTime.now();
    }

    // геттеры и сеттеры


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
