package ru.ifmo.db.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_users")
public class User extends BaseId{
    @Column(name = "user_name", length = 100, nullable = false)
    private String name;
    private int age;

    @OneToMany(mappedBy = "user")
    private List<Article> articles;

    public User() {
        articles = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
// @Entity
// class User {
//      @OneToMany
//      private List<Article> articles; // список статей,
//                  написанных пользователем
// }

// @Entity
// class Article {
//      @ManyToOne
//      private User user; // пользователь, написавший статью
//
//      @ManyToMany
//      private List<Nomination> nominations;
// }

// @Entity
// class Nomination {
//      @ManyToMany
//      private List<Article> articles;
// }

// связи между Entity классами описываются через аннотации
// @OneToMany
// @ManyToOne
// @ManyToMany
// @OneToOne