FROM java18
VOLUME /tmp
ADD target/springboot_01-0.0.1-SNAPSHOT.jar /tmp/springboot_01.jar
ENTRYPOINT ["java", "-jar", "/tmp/springboot_01.jar"]
EXPOSE 8080
