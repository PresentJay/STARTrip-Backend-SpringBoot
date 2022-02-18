FROM openjdk:11

ARG JAVA_OPTS

ENV JAVA_OPTS=$JAVA_OPTS

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar