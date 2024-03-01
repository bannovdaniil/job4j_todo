## TODO список

#### Приложение "TODO список".

**Функционал:**

- Добавить задание
- Удалить задание
- Пометить задание выполненным
- Пометить задание не выполненным

### Стек:
**Spring boot, Thymeleaf, Bootstrap, Hibernate, PostgreSql, Liquibase, jUnit5**

### Требования:
Postgres
port: 5432
db: j4j_todo_db
> create database j4j_todo_db;

user & password set in **application.properties**

### Как запустить:
Открыть проект в IDE выбрать файл Main выполнить.
в браузере открыть
http://localhost:8080/

### Контакты

https://github.com/bannovdaniil
https://www.linkedin.com/in/bannovdaniil
https://t.me/BannovDaniil

**Виды.**
- http://localhost:8080/

**Для работы со писком задач, потребуется авторизация.**
- страница авторизации http://localhost:8080/users/login
![image](https://github.com/bannovdaniil/job4j_todo/assets/96119195/d4353dc8-fd36-4c39-a80e-9e1b51b8ab33)

- страница регистрации http://localhost:8080/users/register
![image](https://github.com/bannovdaniil/job4j_todo/assets/96119195/7dc24b2a-effc-4a54-8a57-b19202e9e61c)

- Страница со списком всех заданий. В таблице отображаем имя, дату создания и состояние (выполнено или нет)
- На странице со списком три ссылки: "Все", "Выполненные", "Новые". При переходе по ссылкам в таблице нужно отображать: все задания, только выполненные или только новые.
![image](https://github.com/bannovdaniil/job4j_todo/assets/96119195/e8f659a3-7389-4f6f-a687-6778243de6ee)

- http://localhost:8080/tasks/add
- "Добавить задание".
![image](https://github.com/bannovdaniil/job4j_todo/assets/96119195/df2f1dbd-86a6-48a9-bb9b-d78790d620e9)
  
- http://localhost:8080/tasks/{taskId}
- Страница с подробным описанием задания.
- Кнопки: "Выполнено", "Редактировать", "Удалить".
![image](https://github.com/bannovdaniil/job4j_todo/assets/96119195/ac9d79ab-5945-4713-80de-0ea8393fce13)

- http://localhost:8080/tasks/edit/{taskId}
- Кнопка "Редактировать" переводит пользователя на отдельную страницу для редактирования.
![image](https://github.com/bannovdaniil/job4j_todo/assets/96119195/a2ff974c-76d1-45d5-9ba0-0a0849d979ec)

- http://localhost:8080/tasks/delete/{taskId}
- Кнопка "Удалить" удаляет задание и переходит на список всех заданий. 
