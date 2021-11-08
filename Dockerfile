FROM openjdk:11
WORKDIR /work

COPY mvnw /work/mvnw
COPY .mvn /work/.mvn
COPY pom.xml /work/pom.xml

RUN ./mvnw dependency:go-offline

COPY . /work/
RUN ./mvnw install -DskipTests
RUN cp /work/target/*.jar /work/target/app.jar

ENTRYPOINT ["java","-jar","/work/target/app.jar"]