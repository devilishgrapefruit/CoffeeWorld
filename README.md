# Coffee World

### Описание
Приложение онлайн-кофейни, предоставляет пользователю возможность регистрации и авторизации,
выбора блюд из меню, добавления блюд в корзину, совершения заказа с подсчетом итоговой суммы.
Для администратора реализована возможность редактирования пользователей и добавления блюд в меню.

<img width="280" height="255" src="https://i.pinimg.com/originals/23/bd/26/23bd26dcbbb36c433553a25d10608433.png"/>

### Технологии, используемые в создании приложения
1. Spring Framework с зависимостями:
- Spring Boot Security Starter
- Spring Boot Starter Web
- Postgresql
- Spring Boot Starter ThymeLeaf, thymeleaf
- Spring Boot Starter Tomcat
- Spring Boot-starter Mail
- Lombok
- Spring Session Jdbc
- Spring Boot Starter Data Jpa
### Как запустить проект
Для запуска проекта понадобится JAVA с с версией 11 и выше, а также сборщик проектов Maven.
Кроме этого, требуется установка система управления базами данных postgres.
Для начала необходимо создать базу данных - coffeeworld.
В терминале напишем:
```groovy
createdb coffeeworld
```
___
<br>
<p>Затем нужно подредактировать файл application.properties, путь: CoffeeWorld/src/main/resources  </p>
<p>Меняем spring.datasource.password и spring.datasource.username на свой логин и пароль от postgreSQL </p>
<p>Вместо YOUR_MAIL пишем свою почту и вместо YOUR_PASSWORD - пароль от почты. </p>
<p>Также редактируем data.path: .../.../CoffeeWorld/src/main/resources/images (вместо точек указываем свой путь) </p> 
После этих шагов можем убедиться, что приложение работает корректно, перейдем в папку CoffeeWorld и введем в терминале:

> java -jar target/CoffeeWorld-0.0.1-SNAPSHOT.jar

Для остановки работы приложения зажмите: Ctrl + C


