begin;
select plan( 10 );

--Юнит-тест 1: Проверка, что в схеме public присутствуют все 9 таблиц
SELECT is(cast(count(*) as integer), 9) FROM information_schema.tables 
WHERE table_schema = 'public';

--Вставка данных о дне и задачах на день для тестов ниже
BEGIN;
INSERT INTO days(day_date) VALUES (CURRENT_DATE);
INSERT INTO tasks(day_id, task_description) VALUES ((SELECT id FROM days WHERE day_date = CURRENT_DATE), 'test1');
INSERT INTO tasks(day_id, task_description) VALUES ((SELECT id FROM days WHERE day_date = CURRENT_DATE), 'test2');
COMMIT;

--Юнит-тест 2: Проверка, что в счётчике заданий на текущий день значение равно 2
SELECT is(cast((SELECT task_count FROM days WHERE day_date = CURRENT_DATE)as integer), 2);

--Юнит-тест 3: Проверка, что в таблицу заданий за текущий день добавилось 2 задания
SELECT is(cast(count(*) as integer), 2) from tasks WHERE day_id = (SELECT id FROM days WHERE day_date = CURRENT_DATE);

--Юнит-тест 4 и 5: Проверка, что добавленные задания имеют id в рамках дня 1 и 2
SELECT is(cast(day_task_id as integer), 1) FROM tasks WHERE task_description = 'test1';
SELECT is(cast(day_task_id as integer), 2) FROM tasks WHERE task_description = 'test2';

--Юнит-тест 6: Проверка, что при попытке добавить третье задание на день происходит исключение
SELECT throws_like('INSERT INTO tasks (day_id, task_description) VALUES ((SELECT id FROM days WHERE day_date = CURRENT_DATE),''3131'')', 'Нельзя добавить больше двух задач на этот день.');

--Удаление записей для очистки БД от данных, используемых для тестирования
BEGIN;
DELETE FROM tasks WHERE day_id = (SELECT id FROM days WHERE day_date = CURRENT_DATE);
DELETE FROM days WHERE id = (SELECT id FROM days WHERE day_date = CURRENT_DATE);
COMMIT;

--Вставка данных о пользователе и его настройках для тестов ниже
BEGIN;
TRUNCATE TABLE themes RESTART IDENTITY CASCADE;
INSERT INTO user_roles(role_name) VALUES ('test');
INSERT INTO user_data(id, firstname, surname, nickname, role_id) VALUES (CAST('3818e8d2-79e3-40f8-9790-f10ecd332554' AS UUID), 'test', 'test', 'test', (SELECT id from user_roles where role_name = 'test'));
INSERT INTO themes(theme_name) VALUES('test');
INSERT INTO user_settings(user_id) VALUES (CAST('3818e8d2-79e3-40f8-9790-f10ecd332554' AS UUID));
COMMIT;

--Юнит-тест 7: Проверка ограничения уникальности у ролей
SELECT throws_like('INSERT INTO user_roles(role_name) VALUES (''test'');', 'duplicate key value violates unique constraint%');

--Юнит-тест 8: Проверка значения по умолчанию у темы приложения в настройках
SELECT is(cast(selected_theme as integer), 1) FROM user_settings WHERE user_id = CAST('3818e8d2-79e3-40f8-9790-f10ecd332554' AS UUID);

--Юнит-тест 9: Проверка ограничения уникальности у настроек пользователя
SELECT throws_like('INSERT INTO user_settings(user_id) VALUES (CAST(''3818e8d2-79e3-40f8-9790-f10ecd332554'' AS UUID));', 'duplicate key value violates unique constraint%');

--Юнит-тест 10: Проверка каскадного удаления у параметров пользователя
BEGIN;
DELETE FROM user_data where id = CAST('3818e8d2-79e3-40f8-9790-f10ecd332554' AS UUID);
DELETE FROM user_roles where role_name = 'test';
DELETE FROM themes where theme_name = 'test';
COMMIT;
SELECT is(cast(count(*) as integer), 0) FROM user_settings WHERE user_id = CAST('3818e8d2-79e3-40f8-9790-f10ecd332554' AS UUID);

select * from finish();
rollback;