FROM openjdk:21-jdk

RUN mkdir /app
WORKDIR /app

COPY ./target/crawler.jar /app/

ENV STAGE=PRODUCTION

CMD ["java", "-jar", "crawler.jar"]