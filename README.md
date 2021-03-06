# Курсовая работа по УД, вариант 16 

Проектом является *REST* сервис, работающий на сервере ***Tomcat***. Все необходимые действия с базой данных осуществляются через веб запросы. Стек проекта:
* **Java 11**.
* **Spring Boot, Security, OAuth, Data** - Фрейворк для веб-приложения, авторизации, токенов и представления объектов базы данных в *Java* коде.
* **PostgreSQL** - непосредственно база данных.
* **Maven** - сборка проекта.
* **Liquibase** - сохранение всех изменений *базы данных*.
* **Lombok** - генерирование конструкторов, геттеров, сеттеров и т.д.
* **Mapstruct** - генерирование мапперов.

### Необходимое ПО
---
1. Установить ***PostgreSQL*** сервер на локальную машину. Скачать можно [здесь](https://www.postgresql.org/download/), либо [прямая ссылка](https://get.enterprisedb.com/postgresql/postgresql-13.1-1-windows-x64.exe) установщика для Windows 10.
2. Установить ***Intellij IDEA*** [отсюда](https://www.jetbrains.com/idea/download/#section=windows). Желательно *Ultimate* версию т.к. у неё есть клиент для работы с бд напрямую, что упростит работу (Иначе придется устанавливать [pgAdmin](https://www.pgadmin.org/download/)). *Ultimate* версия платная, но есть пробный период в 30 дней. Также можно создать студенческий аккаунт на университетскую почту, что позволит
3. Установить ***Insomnia Core***, скачав её [здесь](https://insomnia.rest/download/#windows). Программа необходима для осуществления *REST* запросов к серверу.


### Запуск проекта
---
1.  В контекстном меню ***IDEA*** *File -> New -> Project from Version Control*.
2.  Вставить в поле *URL* ссылку: ```https://github.com/dvur0g/band-manager```и нажать *Clone*.
3. В правом вертикальном меню нажать кнопку ***Maven***, в открывшимся меню нажать на кнопку с символом **М** (при наведении на которую возникает контекстное сообщение ***Run Maven Goal***) и ввести в октрывшееся окно команду ```mvn clean package```. Данная команда запустит генерацию всех недостающих классов, после чего в консоле возникнет сообщение *BUILD SUCCESS*.
4. На правой верхней панели нажать на зеленый треугольник (при на ведении на который возникает контекстное сообщение ***Run 'BandManagerApplication'***)
5. Дождаться в появившейся консоли сообщения ***Started BandManagerApplication in XX seconds...*** Это сообщение означает, что сервис запустился и работает по адресу ```localhost:8080/```.
>**Внимание!** Первые два пункта необходимы только при первом запуске проекта, когда он отсутствует на локальной машине

## Методы проекта
### Подготовка 
---
Для доступа к методам проекта, необходимо импортировать все запросы в ***Insomnia Core*** следующим способом:
1. В контекстном меню выбрать ***Applicaion -> Preferences -> Вкладка *Data* -> Кнопка *Import Data* -> From File***.
2. В открывшемся меню выбрать файл: 
 ```*Путь до проекта*\Insomnia\requests\InsomniaRequests.json```

После этого в левом меню программы появятся директории со всеми доступными *REST* запросами.

### Выполение запросов
---
Так как в проекте используется авторизация *OAuth*, ни один запрос не удастся выполнить (кроме запроса создания новых пользователей и самого запроса на получение токена), пока у него не будет ***токена***. Чтобы выполнить любой запрос необходимо: 
1. Создать пользователя сервиса (если вы этого не делали).
2. По его логину и паролю получить *токен*.
3. Приставить *токен* к запросу, который необходимо выполнить.

#### 1. Создание пользователя
---
В ***Insomnia Core*** в левом меню открыть директорию ***band-manager -> oauth***, выбрать метод 
```POST sign-in``` и нажать кнопку *Send*. По умолчанию тело запроса выглядит так:
```json
{
    "username": "admin",
    "password": "admin"
}
```
Что создаст пользователя с данными значениями в базе данных.
>**Внимание!** Создавать пользователей нужно только один раз, после этого они сохранятся в таблице ```user_entity```.  
А также запрос регистрации ```POST sign-in``` выполняется без необходимости в ***токене***.

#### 2. Получение токена
---
В левом меню открыть директорию ***band-manager -> oauth***, выбрать метод ```POST get-token```, ввести в поля *username* и *password* валидные значения и нажать кнопку *Send*.
После отправки запроса, в правом окне программы появится ответ примерно следующего содержания:
```json
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpIl0sInVzZXJfbmFtZSI6InVzZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNjA4MTg3MTcyLCJhdXRob3JpdGllcyI6WyJNQU5BR0VSIl0sImp0aSI6Inkwdk5OUnd2R2xieE1xRk9zRUkwQVVmMWtHbz0iLCJjbGllbnRfaWQiOiJjbGllbnQifQ.YlmtKgJzpvdyaQyhSW9Awo4JOOJZApL7DCFhxS6VIDs",
  "token_type": "bearer",
  "expires_in": 43199,
  "scope": "read write",
  "jti": "y0vNNRwvGlbxMqFOsEI0AUf1kGo="
}
```
Необходимым нам *токеном* является значение в поле ***access_token***, необходимо скопировать его значение в буфер обмена и перейти к следующему пункту.

>**Внимание!** Полученный токен имеет время действия, в нашем случае это значение в секундах в поле ***expires_in***. В течение этого времени данный токен можно использовать для всех необходимых запросов.

#### 3. Выполение запроса с токеном
---
Открыть необходимый запрос в ***Insomnia Core***, Перейти во вкладку ***Header***, найти хедер с названием ```Authorization``` и вставить в значение слово ```Bearer``` + токен из буфера обмена из предыдущего пункта. В значении хедера ```Authorization``` должно получиться следующее:
```bash
Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpIl0sInVzZXJfbmFtZSI6InVzZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNjA4MTg3MTcyLCJhdXRob3JpdGllcyI6WyJNQU5BR0VSIl0sImp0aSI6Inkwdk5OUnd2R2xieE1xRk9zRUkwQVVmMWtHbz0iLCJjbGllbnRfaWQiOiJjbGllbnQifQ.YlmtKgJzpvdyaQyhSW9Awo4JOOJZApL7DCFhxS6VIDs
```
После этого можно нажимать кнопку *Send* для выполнения запроса.

