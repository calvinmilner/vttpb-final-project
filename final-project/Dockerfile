FROM node:23 AS buildang

WORKDIR /src

COPY client/*.json .
COPY client/public public
COPY client/src src

# Run npm to install node_modules -> package.json
RUN npm ci
RUN npm i -g @angular/cli

# Produce dist/client/browser
RUN ng build

# Build the Spring Boot application
FROM eclipse-temurin:23-jdk AS buildjava

WORKDIR /src

COPY server/mvnw .
COPY server/pom.xml .
COPY server/src src
COPY server/.mvn .mvn

# Copy the angular application over to static directory
COPY --from=buildang /src/dist/client/browser/ src/main/resources/static/

# Running on LINUX, make mvnw executable
RUN chmod a+x mvnw

# Build and produce target/server-0.0.1-SNAPSHOT.jar
RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true

# Deployment container
FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY --from=buildjava /src/target/final-project-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables
ENV PORT=8080
# Other environment variables
ENV SPOONCULAR_API_KEY=
ENV STRIPE_SECRET_KEY=

ENV SPRING_DATA_MONGODB_URI=
ENV SPRING_DATASOURCE_URL=
ENV SPRING_DATASOURCE_USERNAME=
ENV SPRING_DATASOURCE_PASSWORD=
ENV FRONTEND_BASE_URL=

ENV SPRING_MAIL_HOST=smtp.gmail.com
ENV SPRING_MAIL_PORT=587
ENV SPRING_MAIL_USERNAME=
ENV SPRING_MAIL_PASSWORD=
ENV SPRING_MAIL_PROPERTIES_MAIL.SMTP_AUTH=
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar
