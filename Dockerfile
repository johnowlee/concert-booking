FROM eclipse-temurin:17-jdk-alpine
COPY ./build/libs/*SNAPSHOT.jar concert-booking.jar
ENTRYPOINT ["java", "-jar", "concert-booking.jar"]