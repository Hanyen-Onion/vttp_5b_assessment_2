#Imagine Dockerfile is ur notebook
#Install java
FROM eclipse-temurin:23-jdk AS builder

LABEL maintainer="Onion"
LABEL version="v1"

#create an directory /app and change the directory into /app
#before creating, we are outside of /app
#not necessary but then all files will be save at the root
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .
COPY src src

#build the jar file / application
RUN ./mvnw package -Dmaven.test.skip=true

#ow to run the applicaiton
#ENV  SERVER_PORT=8080 
#for railway,
ENV PORT=8080
#add environment variables for redis,

#What port does the application need
#EXPOSE ${SERVER_PORT}
EXPOSE ${PORT}

#remove entrypoint line and add the following

#Use the maven image as the base to build the application
FROM openjdk:23

LABEL “name”=“myapp”

#Build arguments
ARG APP_DIR=/app

#Sets the working directory.
#Like ‘cd’ into the directory
WORKDIR ${APP_DIR}

#Add all these files and directories into $APP_DIR
COPY --from=builder \
		/app/target/noticeboard-0.0.1-SNAPSHOT.jar app.jar

#Sets one or more environment variable
ENV SERVER_PORT=8080
#Tell Docker that the application is listening on $SERVER_PORT
EXPOSE ${PORT}

#Provide a default command with ENTRYPOINT
#Command to execute when container starts
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar

# HEALTHCHECK --interval=60s --timeout=30s --start-period=120s 
# 	CMD curl -s -f http:/localhost:8080/status || exit 1



