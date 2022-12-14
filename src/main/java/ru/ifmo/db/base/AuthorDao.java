package ru.ifmo.db.base;

import java.sql.*;
import java.util.ArrayList;
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
        // PRIMARY KEY -
        // 1. уникальный индекс
        // 2. значения не могут быть NULL
        // 3. может быть только один в таблице
        // 4. может быть составным

        // Class.forName(имя класса) - динамическая загрузка класса
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // ClassNotFoundException, если класс, переданный в качестве аргумента, не был найден
            throw new RuntimeException(e);
        }

        // getConnection выбрасывает SQLException,
        // если не удается установить соединение с сервером
        // (сервер не запущен, синтаксическая ошибка в строке CONNECTION_STR,
        // адрес сервера, порт или имя БД указаны с ошибкой,
        // имя пользователя или пароль указаны с ошибкой,
        // у пользователя нет прав на работу с указанной БД и тд)
        try (Connection connection = DriverManager.getConnection(
                ConnectionSettings.CONNECTION_STR,
                ConnectionSettings.LOGIN,
                ConnectionSettings.PASSWORD
        )){
            // для выполнения запросов используются Statement и PrepareStatement
            // createStatement выбрасывает SQLException, если возникли проблемы
            // с соединением в момент создания объекта Statement
            try (Statement statement = connection.createStatement()){
                // выполнение запроса
                // executeUpdate выбрасывает SQLException, если возникли проблемы
                // с соединением, допущена ошибка в строке запросе
                statement.executeUpdate(create);
                System.out.println("Таблица была создана");
                // executeUpdate используется для запросов, которые не предполагают
                // извлечение данных из базы (не select запросы)
                // возвращает количество добавленных, измененных, удаленных строк
                // 0, если выполняется запрос на создание/редактирование таблицы
            }
        } catch (SQLException e) {
            System.out.println("Таблица не была создана " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Author author) {
        String insert = "INSERT INTO tb_authors (name, age) VALUES (?, ?) " +
                "RETURNING author_id";
        // Statement (уверены в данных и запросы выполняются нечасто)
        // PreparedStatement (не уверены в данных или запрос выполняется часто:
        // защищает от sql инъекций путем экранирования / запросы сохраняются в памяти,
        // не создаются заново, а просто выполняются)
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("org.postgresql.Driver не был загружен");
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(
                ConnectionSettings.CONNECTION_STR,
                ConnectionSettings.LOGIN,
                ConnectionSettings.PASSWORD
        )){
            try (PreparedStatement statement = connection.prepareStatement(insert)){
                // передача данных вместо ? знаков
                // INSERT INTO tb_authors (name, age) VALUES (?, ?)
                statement.setString(1, author.getName());
                statement.setInt(2, author.getAge());
                // statement.executeUpdate();
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("author_id");
                    System.out.println("Данные были добавлены, идентификатор автора " + id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Author author) {
        String update = "UPDATE author SET name = ?, age = ? " +
                "WHERE author_id = ?";
    }

    @Override
    public Author delete(Integer integer) {
        String delete = "DELETE FROM tb_users WHERE author_id = " + integer;

        return null; // метод должен вернуть информацию об удаленном авторе
    }

    @Override
    public Author get(Integer integer) {
        Author author = null;
        String select = "SELECT name, age FROM tb_authors " +
                "WHERE author_id = ?";
        // в блоке where можно использовать операторы сравнения =, > < >= <= !=
        // в блоке where можно объединять условия через AND OR NOT
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("org.postgresql.Driver не был загружен");
            throw new RuntimeException(e);
        }
        // "SELECT name, age FROM tb_authors WHERE author_id = ?";
        try (Connection connection = DriverManager.getConnection(
                ConnectionSettings.CONNECTION_STR,
                ConnectionSettings.LOGIN,
                ConnectionSettings.PASSWORD
        )){
            try (PreparedStatement statement = connection.prepareStatement(select)){
                statement.setInt(1, integer);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    author = new Author();
                    author.setId(integer);
                    author.setAge(resultSet.getInt("age"));
                    author.setName(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return author;
    }

    @Override
    public List<Author> get() {
        List<Author> authors = new ArrayList<>();
        String select = "SELECT * FROM tb_authors";
        // select = "SELECT author_id, age FROM tb_authors";
        // select = "SELECT названия столбцов FROM название таблицы";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(
                ConnectionSettings.CONNECTION_STR,
                ConnectionSettings.LOGIN,
                ConnectionSettings.PASSWORD
        )){
            try (Statement statement = connection.createStatement()){
                // если в результате выполнения запроса необходимо получить данные
                // (например, выполнение select запроса),
                // используется метод executeQuery
                // работать с ResultSet можно до вызова метода close
                // у Statement, Connection
                ResultSet resultSet = statement.executeQuery(select);
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    int id = resultSet.getInt("author_id");

                    Author author = new Author();
                    author.setId(id);
                    author.setName(name);
                    author.setAge(age);

                    authors.add(author);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // try - close вызывается сразу после Exception до передачи управления блоку catch,
        // или в конце блока try, если ошибки не возникнет
        // finally - close вызывается по месту объявления блока finally, после try-catch

        return authors;
    }
}
