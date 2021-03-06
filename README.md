# Usage

## With only the Docker Compose

```bash
# Run everything skiping tests
docker-compose -f docker-compose.dev.yml up
```

```bash
# To remove everything run 
docker-compose -f docker-compose.dev.yml down -v
```

```bash
# Run everything with tests
docker-compose -f docker-compose.testing.yml up
```

```bash
# To remove everything run 
docker-compose -f docker-compose.testing.yml down -v
```

## With the Dockerfile

Step 1 - Create the MySQL database container (just for the first time)

```bash
# For Linux use $PWD instead of ${PWD}
docker run -p 3306:3306 --name jokr-mysql-server -v mysql-v:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=springuser -e MYSQL_PASSWORD=MySQL-P -e MYSQL_DATABASE=jokr_db -d mysql:8.0
```

Step 2 - Create the image that builds the project and runs it

```bash
docker build -t jokr-app-img .
```

Step 3 - Run the previous image with a link to the database and the correct host to it

```bash
docker run --rm -it -p 8080:8080 --link jokr-mysql-server -e DATABASE_HOST=jokr-mysql-server jokr-app-img
```

Each time a change is done just run the 2nd and 3rd Step again, making sure that the MySQL continer is started

## Use Swagger

Step 1 - Run the proyect

Step 2 - Go to the next link

http://localhost:8080/swagger-ui/index.html