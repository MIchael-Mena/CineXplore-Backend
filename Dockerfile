FROM maven:3.9.6-eclipse-temurin-21 AS dependencies
WORKDIR /home/app
COPY pom.xml pom.xml
RUN mvn dependency:resolve dependency:resolve-plugins

FROM dependencies AS build
COPY . .
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre AS deploy
WORKDIR /home/app
COPY --from=build /home/app/target/cinexplore-backend-0.0.1-SNAPSHOT.jar /home/app/cinexplore-backend-0.0.1-SNAPSHOT.jar
COPY ./src/main/resources /home/app/back/src/main/resources
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/app/cinexplore-backend-0.0.1-SNAPSHOT.jar"]