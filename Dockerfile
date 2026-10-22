# ---- Étape 1 : compilation avec Maven ----
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# On copie d'abord le wrapper Maven et le pom pour profiter du cache Docker
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw -q dependency:go-offline

# Puis les sources, et on construit le jar
COPY src src
RUN ./mvnw -q -DskipTests package

# ---- Étape 2 : image d'exécution légère ----
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]