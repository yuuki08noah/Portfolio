FROM pitchio/amazoncorretto-21.0.3-alpine3.19-ruby-3.3.5
WORKDIR app

COPY build/libs app/build/libs
ENTRYPOINT ["java", "-jar", "app/build/libs/AuthServer-0.0.1.jar"]

EXPOSE 1001