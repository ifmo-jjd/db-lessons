package ru.ifmo.db.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class AuthorDao implements Dao<Author, Integer>{

    public void createTable(){
        // название_столбца тип_данных доп_информация,
        // author_id SERIAL PRIMARY KEY

        // NOT NULL - значение по столбцу не может быть пустым
        String create = "CREATE TABLE IF NOT EXISTS tb_authors(" +
                "author_id SERIAL PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "age INTEGER NOT NULL" +
                ");";

        // import java.sql
        // Class.forName(имя класса) - динамическая загрузка класса
        Class.forName("org.postgresql.Driver");

        try (Connection connection = DriverManager.getConnection(
                ConnectionSettings.CONNECTION_STR,
                ConnectionSettings.LOGIN,
                ConnectionSettings.PASSWORD
        )){

        }

    }

    @Override
    public void add(Author author) {

    }

    @Override
    public void update(Author author) {

    }

    @Override
    public Author delete(Integer integer) {
        return null;
    }

    @Override
    public Author get(Integer integer) {
        return null;
    }

    @Override
    public List<Author> get() {
        return null;
    }
}
