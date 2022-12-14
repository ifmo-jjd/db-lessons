package ru.ifmo.db.base;

public class Application {
    public static void main(String[] args) {
       AuthorDao authorDao = new AuthorDao();
       authorDao.createTable();

       Author author01 = new Author();
       author01.setName("Mike");
       author01.setAge(26);

       authorDao.add(author01);

       System.out.println(authorDao.get());
       System.out.println(authorDao.get(1));
    }
}

















