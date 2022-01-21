FROM openjdk:11

ARG JAR_FILE=build/libs/mvc-example-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE_PATH} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
