FROM openjdk:17
VOLUME /tmp
COPY target/customer-0.0.1-SNAPSHOT.jar customer.jar
ENTRYPOINT ["java","-jar","/customer.jar"]