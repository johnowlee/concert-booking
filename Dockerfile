FROM gradle:8.3-jdk17-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle ./

RUN gradle dependencies --no-daemon

COPY . /app

RUN gradle clean build --no-daemon

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/concert-booking.jar

EXPOSE 8080
ENTRYPOINT ["java"]
CMD ["-jar", "concert-booking.jar"]