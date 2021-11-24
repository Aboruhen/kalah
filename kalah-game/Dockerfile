FROM openjdk:17-alpine

ARG JAR_FILE
COPY target/${JAR_FILE} service.jar

EXPOSE 8080
ENTRYPOINT ["java", \
            "-jar", \
            "service.jar"]