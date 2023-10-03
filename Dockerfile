FROM openjdk:17
ADD target/instrument-online-shop-1.0-SNAPSHOT.jar instrument-online-shop-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/instrument-online-shop-1.0-SNAPSHOT.jar"]


#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17
COPY --from=build target/instrument-online-shop-1.0-SNAPSHOT.jar instrument-online-shop-1.0-SNAPSHOT.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/instrument-online-shop-1.0-SNAPSHOT.jar"]