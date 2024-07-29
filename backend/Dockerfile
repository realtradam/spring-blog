FROM eclipse-temurin:17.0.11_9-jdk-alpine as base

# Make temporary build container
From base as build
COPY . .

# Build our app
RUN ./mvnw package

# Go back to our main container and copy over the build jar to it
FROM base
COPY --from=build target/*.jar app.jar

# Add user
RUN addgroup -S spring && adduser -S spring -G spring && \
	chown -R spring:spring app.jar
USER spring:spring

# Run the app
ENTRYPOINT ["java","-jar","/app.jar"]
