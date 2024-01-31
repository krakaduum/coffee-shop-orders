FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar coffee-shop-orders.jar
ENTRYPOINT ["java","-jar","coffee-shop-orders.jar"]