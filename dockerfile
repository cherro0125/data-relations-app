FROM arm64v8/openjdk:17-ea-14 as builder
RUN mkdir /app
WORKDIR /app
COPY . .
RUN ./mvnw package -DskipTests

FROM arm64v8/openjdk:17-ea-14
RUN mkdir /app
WORKDIR /app
COPY --from=builder /app/target/*.jar data-relations-spring.jar
CMD ["java", "-jar", "data-relations-spring.jar"]