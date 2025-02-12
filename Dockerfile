FROM openjdk:21
EXPOSE 9090
ADD target/docker-demo-dentist.jar docker-demo-dentist.jar
ENTRYPOINT ["java","-jar","/docker-demo-dentist.jar"]
