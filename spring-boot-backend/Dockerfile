FROM openjdk:11-jdk@sha256:a487fbdcf210c435c38cd36dc946591119a064d560354498d67d13d1e9a385a5

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
