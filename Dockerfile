FROM amd64/eclipse-temurin:17-jre-alpine
EXPOSE 8765
ARG JAR_FILE=target/*jar
COPY ${JAR_FILE} my-cloud.jar
ENTRYPOINT ["java", "-jar", "my-cloud.jar"]