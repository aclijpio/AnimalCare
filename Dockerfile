FROM eclipse-temurin:21-jdk-alpine as build
LABEL maintainer="aclij"

RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/Europe/Moscow /etc/localtime \
    && echo "Europe/Moscow" > /etc/timezone

ARG JAR_FILE=/target/*-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp

ARG DEPENDENCY=/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app


ENTRYPOINT ["java","-cp","app:app/lib/*", "com.github.aclijpio.animalcare.AnimalCareApplication"]



















