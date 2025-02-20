FROM openjdk:21
EXPOSE 8080
WORKDIR /app
COPY target/springSecurity-0.0.1-SNAPSHOT.jar /app/app.jar
#ADD target/docker-demo-dentist.jar docker-demo-dentist.jar
#ENTRYPOINT ["java","-jar","/docker-demo-dentist.jar"]
ENTRYPOINT ["java","-jar","app.jar"]
