package ru.ifmo.db.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.ifmo.db.jpa.dao.ArticleDao;
import ru.ifmo.db.jpa.entity.Article;

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
    }
}
