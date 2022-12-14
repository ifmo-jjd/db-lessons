package ru.ifmo.db.jpa;

import jakarta.persistence.*;
import ru.ifmo.db.jpa.dao.ArticleDao;
import ru.ifmo.db.jpa.entity.Article;
import ru.ifmo.db.jpa.entity.User;

public class JPApplication {
    public static void main(String[] args) {
        // Entity классами управляет EntityManager
        // EntityManager создается EntityManagerFactory

        // EntityManagerFactory должна создаваться в try с ресурсами
        // или у объекта дб вызван метод close
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("ormLesson");

        // EntityManager должен создаваться в try с ресурсами
        // или у объекта дб вызван метод close

        EntityManager manager = factory.createEntityManager();

        Article article = new Article();
        article.setTitle("JPA");
        article.setText("Jakarta Per ...");

        // добавление информации об объекте в таблицу
        manager.getTransaction().begin(); // открытие транзакции

        // запросы, которые дб выполнены
        manager.persist(article);

        // можно вызвать manager.getTransaction().rollback()
        // и отменить выполнение всех запросов

        // подтверждение транзакции, обновление таблиц,
        // состояние БД меняется согласно запросам
        manager.getTransaction().commit();

        // получение записи по первичному ключу
        Article fromDB = manager.find(Article.class, 1);
        System.out.println("fromDB 1" + fromDB.getTitle());

        fromDB.setTitle("JakartaPA");

        // обновление информации в таблице
        manager.getTransaction().begin();
        manager.merge(fromDB); // по первичному ключу (@Id)
        manager.getTransaction().commit();

        fromDB = manager.find(Article.class, 1);
        System.out.println("fromDB 2" + fromDB.getTitle());

        // удаление информации из таблицы
        /*
        manager.getTransaction().begin();
        manager.remove(fromDB); // по первичному ключу (@Id)
        manager.getTransaction().commit();
        */

        ArticleDao articleDao = new ArticleDao(manager);
        System.out.println(articleDao.get());

        System.out.println(articleDao.getByTitle("JakartaPA"));

        User paul = new User();
        paul.setName("Paul");
        paul.setAge(32);

        // устанавливаем взаимные ссылки
        paul.getArticles().add(article);
        article.setUser(paul);

        // в классе User можно создать метод
        // void addArticle(Article article) {
        //    можно добавить проверки
        //    далее код, устанавливающий взаимные ссылки:
        //    article.setAuthor(this);
        //    articles.add(article);
        // }

        // и вызвать его вместо 2 предыдущих инструкций
        // paul.addArticle(article);

        // благодаря взаимным ссылкам и cascade = CascadeType.PERSIST в классе User
        // вызов persist приведет к добавлению данных о пользователе и о статье
        manager.getTransaction().begin();
        manager.persist(paul);
        manager.getTransaction().commit();


        // @Table(name = "tb_articles")
        // class Article

        // @Column(name = "article_text", columnDefinition = "TEXT")
        // private String text;

        // 1. Использовать SQL: имена таблиц и столбцов как в БД
        // SELECT article_text FROM tb_articles ...
        // 2. Использовать JPQL: имена классов и свойств как в java приложении
        // SELECT text FROM Article ...
        // SQL / JPQL запросы можно написать через аннотации к entity классу
        // SQL / JPQL запросы можно написать отдельно, например, в DAO классе

        // 3. ООП подход в написании запросов Criteria API
        // Criteria API запросы можно написать отдельно, например, в DAO классе


        // java приложение  ->  фреймворк  ->  ORM (JPA) -> pgdriver (jdbc)
    }
}
