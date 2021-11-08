# Docker for the project

Step 1 - Create the MySQL database container

```bash
# For Linux use $PWD instead of ${PWD}
docker run -p 3306:3306 --name mysql-server -v mysql-v:/var/lib/mysql -v ${PWD}/mysql:/docker-entrypoint-initdb.d -e MYSQL_ROOT_PASSWORD=root -d mysql
```

Step 2 - Build the image to run the web page server

```bash
docker build -t spring-app-img .
```

Step 3 - Run the previous image

```bash
docker run --rm -it -p 8080:8080 --link mysql-server -e DATABASE_HOST=mysql-server spring-app-img
```

# Resources

https://spring.io/guides/gs/spring-boot-docker/

https://spring.io/guides/gs/accessing-data-mysql/

https://spring.io/blog/2018/11/08/spring-boot-in-a-container

https://docs.spring.io/spring-boot/docs/1.5.6.RELEASE/reference/html/boot-features-external-config.html