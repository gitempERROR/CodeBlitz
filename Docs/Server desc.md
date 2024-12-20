## Описание серверной части

В качестве платформы реализации серверной части был выбран Supabase. Supabase предоставляет все необходимые инструменты для создания базы данных, аутентификации, хранилища объектов, функций и т.д.

### ER-диаграмма

![ER-диаграмма](https://i.imgur.com/UXp8xce.png)

### Таблицы

| Таблица             | Описание                                                                     |
| :------------------ | :--------------------------------------------------------------------------- |
| `users`             | Содержит информацию о пользователях (часть Supabase.auth)                    |
| `user_data`         | Содержит расширенные данные о пользователях, не относящиеся к аутентификации |
| `user_roles`        | Содержит информацию о ролях пользователей                                    |
| `user_settings`     | Содержит информацию о настройках пользователя                                |
| `days`              | Содержит информацию о днях с задачами                                        |
| `tasks`             | Содержит информацию о задачах                                                |
| `task_solutions`    | Содержит информацию о решениях задач пользователей                           |
| `languages`         | Содержит информацию о доступных языках программирования                      |
| `solution_statuses` | Содержит информацию о статусах решения задачи                                |
| `themes`            | Содержит информацию о доступных темах                                        |

### Описание полей

| Таблица             | Поле                 | Назначение                                     | Тип данных | Ограничения                         |
| :------------------ | :------------------- | :--------------------------------------------- | :--------- | :---------------------------------- |
| `users`             | `instance_id`        | Идентификатор записи о пользователе            | `uuid`     | `primary key`                       |
|                     | `id`                 | Идентификатор пользователя                     | `uuid`     |                                     |
|                     | `email`              | Электронный адрес пользователя                 | `text`     | `unique`, `not null`                |
|                     | `encrypted_password` | Зашифрованный пароль пользователя              | `text`     | `not null`                          |
| `user_data`         | `id`                 | Идентификатор пользователя                     | `uuid`     | `primary key`                       |
|                     | `firstname`          | Имя пользователя                               | `text`     | `not null`, `length < 30`           |
|                     | `surname`            | Фамилия пользователя                           | `text`     | `not null`, `length < 30`           |
|                     | `role_id`            | Идентификатор роли пользователя                | `integer`  | `foreign key`                       |
|                     | `nickname`           | Никнейм пользователя                           | `text`     | `length < 30`                       |
| `user_roles`        | `id`                 | Идентификатор роли                             | `integer`  | `primary key`                       |
|                     | `role_name`          | Название роли                                  | `text`     | `not null`, `unique`                |
| `user_settings`     | `id`                 | Идентификатор настройки                        | `integer`  | `primary key`                       |
|                     | `selected_theme`     | Выбранная пользователем тема                   | `integer`  | `foreign key`,`not null`            |
|                     | `user_id`            | Идентификатор пользователя                     | `uuid`     | `foreign key`, `not null`, `unique` |
| `days`              | `id`                 | Идентификатор дня                              | `integer`  | `primary key`                       |
|                     | `day_date`           | Дата дня                                       | `date`     | `not null`, `unique`                |
|                     | `task_count`         | Количество задач в дне                         | `integer`  | `not null`, `0 <= task_count <= 2`  |
| `tasks`             | `id`                 | Идентификатор задачи                           | `integer`  | `primary key`, `not null`           |
|                     | `day_id`             | Идентификатор дня, к которому относится задача | `integer`  | `foreign key`, `not null`           |
|                     | `day_task_id`        | Количество задач в дне                         | `integer`  | `not null`, `1 <= day_task_id <= 2` |
|                     | `task_description`   | Описание задачи                                | `text`     | `not null`, `unique`                |
| `task_solutions`    | `id`                 | Идентификатор решения задачи                   | `integer`  | `primary key`                       |
|                     | `user_id`            | Идентификатор пользователя, решившего задачу   | `uuid`     | `foreign key`, `not null`           |
|                     | `task_id`            | Идентификатор задачи                           | `integer`  | `foreign key`, `not null`           |
|                     | `language_id`        | Идентификатор языка, на котором решена задача  | `integer`  | `foreign key`, `not null`           |
|                     | `code`               | Решение задачи                                 | `text`     | `not null`                          |
|                     | `current_status`     | Текущий статус решения задачи                  | `text`     | `not null`                          |
|                     | `start_time`         | Время начала решения задачи                    | `time`     | `not null`                          |
|                     | `end_time`           | Время окончания решения задачи                 | `time`     | `not null`                          |
| `languages`         | `id`                 | Идентификатор языка                            | `integer`  | `primary key`                       |
|                     | `language_name`      | Название языка                                 | `text`     | `not null`, `unique`                |
| `solution_statuses` | `id`                 | Идентификатор статуса решения задачи           | `integer`  | `primary key`                       |
|                     | `status_name`        | Название статуса решения задачи                | `text`     | `not null`, `unique`                |
| `themes`            | `id`                 | Идентификатор темы                             | `integer`  | `primary key`                       |
|                     | `theme_name`         | Название темы                                  | `text`     | `not null`                          |
|                     | `color_1`            | Цвет 1 для темы                                | `text`     |                                     |
|                     | `color_2`            | Цвет 2 для темы                                | `text`     |                                     |
|                     | `color_3`            | Цвет 3 для темы                                | `text`     |                                     |
|                     | `color_4`            | Цвет 4 для темы                                | `text`     |                                     |
|                     | `color_5`            | Цвет 5 для темы                                | `text`     |                                     |
|                     | `color_6`            | Цвет 6 для темы                                | `text`     |                                     |

### Связи

| Таблица 1        | Таблица 2           | Тип связи | Описание                                                               |
| :--------------- | :------------------ | :-------- | :--------------------------------------------------------------------- |
| `users`          | `user_data`         | 1-1       | Каждый пользователь имеет один набор данных                            |
| `user_data`      | `user_settings`     | 1-1       | Каждый пользователь имеет одну настройку                               |
| `user_data`      | `user_roles`        | м-1       | Пользователь может иметь только одну роль                              |
| `user_data`      | `task_solutions`    | 1-м       | Пользователь может иметь множество решений                             |
| `days`           | `tasks`             | 1-м       | День может содержать несколько задач                                   |
| `tasks`          | `task_solutions`    | 1-м       | Задача может иметь много решений                                       |
| `user_settings`  | `themes`            | м-1       | Пользователь может выбрать одну тему                                   |
| `task_solutions` | `languages`         | м-1       | На каждом языке программирования может быть написано несколько решений |
| `task_solutions` | `solution_statuses` | м-1       | Каждое решение задачи может иметь один из статусов                     |

### Реализованные функции Supabase

- [**Realtime**]: Обновление данных у подписанных клиентов для таблиц task_solutions и user_settings

### Триггеры

- [**check_task_count_trigger**]: Запрещает добавление задание на день, если для этого дня уже есть 2 задания

###### Код триггера

```sql
-- Триггер для проверки количества задач на день
CREATE OR REPLACE FUNCTION check_task_count() RETURNS TRIGGER AS $$
BEGIN
  IF (SELECT COUNT(*) FROM tasks WHERE day_id = NEW.day_id) >= 2 THEN
    RAISE EXCEPTION 'Нельзя добавить больше двух задач на этот день.';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_task_count_trigger
BEFORE INSERT ON tasks
FOR EACH ROW
EXECUTE PROCEDURE check_task_count();
```

- [**increment_task_count_trigger**]: Увеличивает счетчик заданий на день после добавлении задания

###### Код триггера

```sql
-- Триггер для увеличения количества задач на день
CREATE OR REPLACE FUNCTION increment_task_count() RETURNS TRIGGER AS $$
BEGIN
  UPDATE days SET task_count = task_count + 1 WHERE id = NEW.day_id;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER increment_task_count_trigger
AFTER INSERT ON tasks
FOR EACH ROW
EXECUTE PROCEDURE increment_task_count();
```

- [**set_day_task_id_trigger**]: Устанавливает day_task_id задачи в рамках дня (1 или 2)

###### Код триггера

```sql
-- Триггер для установки day_task_id
CREATE OR REPLACE FUNCTION set_day_task_id() RETURNS TRIGGER AS $$
BEGIN
  IF (SELECT COUNT(*) FROM tasks WHERE day_id = NEW.day_id) = 0 THEN
    NEW.day_task_id := 1;
  ELSE
    NEW.day_task_id := 2;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_day_task_id_trigger
BEFORE INSERT ON tasks
FOR EACH ROW
EXECUTE PROCEDURE set_day_task_id();
```
