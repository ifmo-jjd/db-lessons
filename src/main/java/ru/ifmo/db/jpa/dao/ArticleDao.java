package ru.ifmo.db.jpa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.ifmo.db.base.Dao;
import ru.ifmo.db.jpa.entity.Article;

import java.util.List;

public class ArticleDao implements Dao<Article, Integer> {
    private EntityManager manager;

    public ArticleDao(EntityManager manager) {
        this.manager = manager;
    }


    @Override
    public void add(Article article) {

    }

    @Override
    public void update(Article article) {

    }

    @Override
    public Article delete(Integer integer) {
        return null;
    }

    @Override
    public Article get(Integer integer) {
        return null;
    }

    @Override
    public List<Article> get() {
        /* 1. named native query */
        // TypedQuery<T> extends (Query) - запрос
        /* TypedQuery<Article> query = manager.createNamedQuery("get_all.native", Article.class);
        List<Article> authors = query.getResultList(); */

        /* 2. named JPQL query */
        /* TypedQuery<Article> query = manager.createNamedQuery("get_all.jpql", Article.class);
        List<Article> authors = query.getResultList(); */

        /* 3. JPQL query */
        /* String jpql = "SELECT a FROM Article a";
        TypedQuery<Article> query = manager.createQuery(jpql, Article.class);
        List<Article> authors = query.getResultList(); */

        // "SELECT title FROM tb_articles";
        /* String jpqlTitles = "SELECT a.title FROM Article a";
        TypedQuery<String> queryNames = manager.createQuery(jpqlTitles, String.class);
        List<String> titles = queryNames.getResultList();
        System.out.println("TITLES: " + titles); */

        /* 4. Criteria API */
        // criteriaBuilder - формирует SQL запросы
        // criteriaQuery - SQL запрос (аналог SQL строки)

        // "SELECT id, title, article_text, created FROM tb_articles"

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();

        // тип данных результата
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);

        // откуда извлекаются данные
        Root<Article> root = criteriaQuery.from(Article.class); // FROM tb_articles
        criteriaQuery.select(root); // SELECT
        // SELECT id, title, article_text, created FROM tb_articles

        TypedQuery<Article> query = manager.createQuery(criteriaQuery);
        List<Article> authors = query.getResultList();

        return authors;
    }

    // SELECT id, title, created, article_text FROM tb_articles WHERE title = ?;
    // SELECT a FROM Article a WHERE a.title = ?;
    // 1 -> "JPA"

    // SELECT a FROM Article a WHERE a.title = :title_param
    // title_param -> "JPA"

    // SELECT a FROM Article a WHERE a.title = :title
    // title -> "JPA"
    public Article getByTitle(String title) {
        /* TypedQuery<Article> query = manager.createNamedQuery("get_by_title.jpql", Article.class);
        // query.setParameter(имя параметра из запроса (символы после :), значение)
        query.setParameter("title", title);

        Article article = query.getSingleResult(); */

        // "SELECT id, title, created, article_text FROM tb_articles WHERE title = " + title

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class); // тип данных результата

        // FROM tb_articles
        Root<Article> root = criteriaQuery.from(Article.class); // откуда извлекаются данные

        // Predicate - аналог условия блока WHERE
        // import jakarta.persistence.criteria.Predicate;
        // WHERE title = " + title AND ... AND ...
        Predicate titleCond = criteriaBuilder.equal(root.get("title"), title);
        // equal -> = из блока WHERE

        // Predicate titleCond01 = criteriaBuilder.equal(root.get("title"), title);
        // Predicate titleCond02 = criteriaBuilder.equal(root.get("title"), title);

        // AND / OR / NOT
        // Predicate all = criteriaBuilder.and(titleCond, titleCond01, titleCond02);

        // SELECT *
        criteriaQuery.select(root).where(titleCond);

        TypedQuery<Article> query = manager.createQuery(criteriaQuery);
        Article article = query.getSingleResult();
        return article;
    }
}
