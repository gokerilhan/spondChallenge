FROM eclipse-temurin:17
MAINTAINER spond.com
COPY  target/spondChallenge-1.0.jar spondChallenge-1.0.jar
ENTRYPOINT ["java","-jar","/spondChallenge-1.0.jar"]
