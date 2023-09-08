FROM openjdk:17
ADD target/instrument-online-shop-1.0-SNAPSHOT.jar instrument-online-shop-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/instrument-online-shop-1.0-SNAPSHOT.jar"]