-- создать таблицу с названием tb_authors, если ее не существует
CREATE TABLE IF NOT EXISTS tb_authors(
   -- описание столбцов через запятую
   -- имя_столбца тип_данных доп_инфо
   author_id serial PRIMARY KEY,
   name VARCHAR(50) NOT NULL,
   age INTEGER NOT NULL -- после описания последнего запятую не ставим
); -- в конце запроса точка с запятой

-- добавляем данные в таблицу tb_authors
-- добавляем значения qwerty и 28 по столбцам name и age
-- количество и последовательность столбцов и значений должно совпадать
INSERT INTO tb_authors (name, age) VALUES ('qwerty', 28);

-- получить записи
-- SELECT имя_столбца1, имя_столбца2 FROM имя_таблицы WHERE;

-- получить все записи
SELECT * FROM tb_authors;

-- получить идентификаторы авторов, сортировать по убыванию возраста
SELECT author_id,
FROM tb_authors
ORDER BY age DESC;
-- ORDER BY DESC - сортировать по убыванию
-- ORDER BY ASC - сортировать по возрастанию

-- операторы = < > <= >= != для фильтрации по строкам
-- получить идентификаторы и имена авторов старше 30 лет
SELECT author_id, name
FROM tb_authors
WHERE age > 30;

-- получить возраст автора с именем Mike
SELECT age
FROM tb_authors
WHERE name = 'Mike';

-- BETWEEN ... AND ... диапазон
-- получить идентификаторы и имена авторов старше 30 и младше 50 лет
SELECT author_id, name
FROM tb_authors
WHERE age BETWEEN 30 AND 50;

-- AND OR NOT
-- получить идентификаторы и имена авторов старше 30 и младше 50 лет
SELECT author_id, name
FROM tb_authors
WHERE age > 30 AND age < 50;

-- получить идентификаторы и имена авторов младше 18 или старше 60
SELECT author_id, name
FROM tb_authors
WHERE age < 18 OR age > 60;

-- IN(value1, value2, value3)
-- получить идентификаторы авторов с именем John или Mike, или Anna
SELECT author_id
FROM tb_authors
WHERE name IN('John', 'Mike', 'Anna');

-- NOT IN(value1, value2, value3)
-- получить идентификаторы авторов, кроме авторов с именем John или Mike, или Anna
SELECT author_id
FROM tb_authors
WHERE name NOT IN('John', 'Mike', 'Anna');

-- LIKE
-- An%
-- %na
-- %n%

-- получить идентификаторы и возраст авторов имена которых начинается с 'A'
SELECT author_id
FROM tb_authors
WHERE name LIKE 'A%';

-- получить идентификаторы и возраст авторов имена которых заканчиваются на 'e'
SELECT author_id
FROM tb_authors
WHERE name LIKE '%e';

-- получить идентификаторы и возраст авторов имена которых содержат 'o'
SELECT author_id
FROM tb_authors
WHERE name LIKE '%o%';



