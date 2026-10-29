FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY prices-domain/pom.xml prices-domain/
COPY prices-application/pom.xml prices-application/
COPY prices-api/pom.xml prices-api/
COPY prices-infrastructure/pom.xml prices-infrastructure/
COPY prices-boot/pom.xml prices-boot/
COPY prices-e2e/pom.xml prices-e2e/
RUN mvn -B -q dependency:go-offline -pl prices-boot -am
COPY . .
RUN mvn -B -q package -pl prices-boot -am -DskipTests

FROM eclipse-temurin:17-jre
RUN groupadd --system app && useradd --system --gid app app
USER app
WORKDIR /app
COPY --from=build /workspace/prices-boot/target/prices-boot-*-exec.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
