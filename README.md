# Docker for the project

Step 1 - Use the maven image to run a "clean install" on all the project.

```bash
# For Linux
docker run -it --name maven-install -v $PWD:/usr/src/mymaven -w /usr/src/mymaven maven mvn clean install

# For PowerShell
docker run -it --name maven-install -v ${PWD}:/usr/src/mymaven -w /usr/src/mymaven maven mvn clean install
```

Step 2 - Build the image to run the web page server.

```bash
docker build -t java-web-app-img .
```

Step 3 - Run the previous image.

```bash
docker run --rm -it -p 8080:8080 java-web-app-img
```