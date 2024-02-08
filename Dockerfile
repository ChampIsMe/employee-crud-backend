FROM amazoncorretto:21-alpine
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /app/
COPY build/libs/employee-crud-backend-${VERSION}.jar application.jar
CMD java -jar /app/application.jar