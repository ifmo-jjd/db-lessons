package ru.ifmo.db.base;

public class ConnectionSettings {
    // данные для подключения к серверу:
    // postgresql - используемый драйвер (используемая СУБД)
    // localhost - адрес машины, на которой запущен сервер Postgresql
    // 5432 - порт, на котором запущен сервер Postgresql
    // db_lessons - название базы данных, к которой будем подключаться
    public static final String CONNECTION_STR =
            "jdbc:postgresql://localhost:5432/db_lessons";

    // имя пользователя и его пароль для подключения
    // к серверу Postgresql
    public static final String LOGIN = "ifmo";
    public static final String PASSWORD = "ifmo";
}
