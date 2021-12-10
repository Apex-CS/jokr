FROM openjdk:17-alpine
WORKDIR /work

COPY mvnw /work/mvnw
COPY .mvn /work/.mvn
COPY pom.xml /work/pom.xml

RUN ./mvnw dependency:go-offline

COPY . /work/
RUN ./mvnw install -DskipTests

ENTRYPOINT ["java", "-jar", "/work/target/app.jar"]
