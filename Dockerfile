# Stage 1: WAR-Datei mit Maven bauen (Maven muss lokal nicht installiert sein)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q package -DskipTests

# Stage 2: WAR in Apache TomEE 8 (webprofile, javax-Namensraum) bereitstellen
FROM tomee:8-jre17
RUN rm -rf /usr/local/tomee/webapps/*
COPY --from=build /build/target/require4testing.war /usr/local/tomee/webapps/require4testing.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
