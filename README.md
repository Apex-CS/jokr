# Usage

## With only the docker-compose.yml 

```bash
docker-compose up
```

## With the Dockerfile

Step 1 - Create the MySQL database container (just for the first time)

```bash
# For Linux use $PWD instead of ${PWD}
docker run -p 3306:3306 --name jokr-mysql-server -v mysql-v:/var/lib/mysql -v ${PWD}/mysql:/docker-entrypoint-initdb.d -e MYSQL_ROOT_PASSWORD=root -d mysql
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