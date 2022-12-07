package ru.ifmo.db.base;

import java.util.List;

// реализация DAO паттерна
// T - информацию об экземплярах типа T будем хранить в БД
// PK - типа данных уникального идентификатора экземпляра типа T
public interface Dao<T, PK> {
    // добавить информацию в таблицу
    void add(T t);
    // обновление данных в таблице
    void update(T t);
    // удаление данных по уникальному идентификатору
    T delete(PK pk); // void delete(PK pk); void delete(T t);
    // получение по уникальному идентификатору
    T get(PK pk);
    // получение всех записей
    List<T> get();
}
