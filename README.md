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

**Movie Watchlist Manager** is a Java Swing desktop application for managing a personal movie watchlist. Users can add genres, add movies assigned to genres, mark movies as watched, and delete both genres and movies.

## Features

- Add and delete genres
- Add and delete movies
- Mark movies as watched
- Simple and clean Swing GUI

## Technologies

- Java 21, Eclipse, Maven
- JUnit 4, AssertJ, Mockito
- AssertJ Swing (UI testing)
- MongoDB with Testcontainers
- JaCoCo, Coveralls (code coverage)
- PIT (mutation testing)
- SonarCloud (code quality)
- GitHub Actions (CI)

## Running the application

Start a MongoDB instance then run:
target/com.ahmad.sohail.moviewatchlist-0.0.1-SNAPSHOT-jar-with-dependencies.jar
--mongo-host=localhost 
--mongo-port=27017 
--db-name=moviewatchlist 
--genre-collection=genre 
--movie-collection=movie

## Running the tests
mvn verify

With coverage:
mvn verify -Pjacoco

With mutation testing:
mvn verify -Ppit-mutation-testing
