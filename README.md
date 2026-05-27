# Movie Watchlist Manager

Project for the Automated Software Testing course @ University of Florence

## CI & Coverage

[![Java CI with Maven and Docker in Linux](https://github.com/sohailnajib/AST/actions/workflows/maven-ci.yml/badge.svg)](https://github.com/sohailnajib/AST/actions/workflows/maven-ci.yml)
[![Coverage Status](https://coveralls.io/repos/github/sohailnajib/AST/badge.svg?branch=main)](https://coveralls.io/github/sohailnajib/AST?branch=main)

## Code Quality

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sohailnajib_AST&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=sohailnajib_AST)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=sohailnajib_AST&metric=coverage)](https://sonarcloud.io/summary/new_code?id=sohailnajib_AST)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=sohailnajib_AST&metric=bugs)](https://sonarcloud.io/summary/new_code?id=sohailnajib_AST)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=sohailnajib_AST&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=sohailnajib_AST)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=sohailnajib_AST&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=sohailnajib_AST)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=sohailnajib_AST&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=sohailnajib_AST)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=sohailnajib_AST&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=sohailnajib_AST)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=sohailnajib_AST&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=sohailnajib_AST)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=sohailnajib_AST&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=sohailnajib_AST)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=sohailnajib_AST&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=sohailnajib_AST)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=sohailnajib_AST&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=sohailnajib_AST)

## Description

Movie Watchlist Manager is a Java Swing desktop application for managing a personal movie watchlist organized by genre. You can add and delete genres, add and delete movies assigned to a genre, and mark movies as watched.

## Requirements

- Java 21
- Maven
- Docker

## Running the Application

Start a MongoDB container:  
docker run --name movie-watchlist-mongo -d -p 27017:27017 mongo:4.4.18

If the container already exists, start it with:  
docker start movie-watchlist-mongo

Build the JAR:  
mvn clean package -DskipTests

Run the application:  
java -jar target/com.ahmad.sohail.moviewatchlist-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
  --mongo-host=localhost \
  --mongo-port=27017 \
  --db-name=moviewatchlist \
  --genre-collection=genre \
  --movie-collection=movie

Stop MongoDB after use:  
docker stop movie-watchlist-mongo

## Running the Tests

Testcontainers handles the MongoDB container automatically for integration and E2E tests — no manual Docker step needed.

Run all tests:  
mvn verify

With coverage report:  
mvn verify -Pjacoco

With mutation testing:   
mvn verify -Ppit-mutation-testing

## Technologies

- Java 21, Eclipse, Maven
- JUnit 4, AssertJ, AssertJ Swing, Mockito, Awaitility
- MongoDB, mongo-java-server, Testcontainers
- JaCoCo, Coveralls, PIT mutation testing
- SonarCloud, GitHub Actions, Docker
