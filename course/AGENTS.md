# Repository Guidelines

## Project Structure & Module Organization

This repository contains a Spring Boot application for users, products, categories, orders, and order items.

- `src/main/java/com/educandoweb/course/`: application entry point, JPA `entities/`, Spring Data `repositories/`, `services/`, and `config/`.
- `src/main/java/resource/`: REST controllers such as `UserResource` and `ProductResource`. This package sits outside the application's default component scan; verify controller discovery when changing endpoints.
- `src/main/resources/`: application properties and profile configuration.
- `src/test/java/com/educandoweb/course/`: automated tests.
- `gradle/wrapper/`: pinned Gradle wrapper. Generated output belongs in `build/`; do not commit it or `.gradle/`.

## Build, Test, and Development Commands

Use JDK 25, as required by `build.gradle`, and the checked-in Gradle wrapper. From PowerShell:

- `.\gradlew.bat bootRun`: start the application with the configured `test` profile and seeded H2 database.
- `.\gradlew.bat test`: execute the JUnit test suite.
- `.\gradlew.bat build`: compile, run tests, and package the application.
- `.\gradlew.bat clean`: remove generated build output.

On Unix-like systems, use `./gradlew` for the equivalent commands.

## Coding Style & Naming Conventions

Match surrounding Java formatting: most domain and service classes use four spaces; generated application and test classes use tabs. Avoid unrelated reformatting. Use `PascalCase` for classes, `camelCase` for methods and fields, and uppercase enum constants. Follow existing suffixes: `Resource`, `Service`, and `Repository`. Keep HTTP handling in resources, business logic in services, and persistence access in repositories. No formatter or lint plugin is currently configured.

## Testing Guidelines

Tests use JUnit Jupiter with Spring Boot test support. The existing `CourseApplicationTests` checks context startup. Name new test classes `*Tests` and mirror source packages. Add focused tests for changed behavior, especially entity relationships, repository queries, and endpoints. No coverage threshold is configured.

## Commit & Pull Request Guidelines

History uses short descriptive messages in Portuguese and English, without a strict prefix convention. Write focused messages describing the affected behavior or association. Pull requests should explain the change, link relevant issues, and report validation commands and results. Include request/response examples for API changes and describe persistence or configuration impacts.

## Configuration & Security

The default `test` profile uses an in-memory H2 database, enables `/h2-console`, and seeds sample data through `TesteConfig`. Keep these settings local; use a separate profile and external credentials for deployment. Never commit real passwords or connection secrets.

## Idioma e comunicação

- Responder sempre em português brasileiro (pt-BR).
- Explicar análises, erros e soluções em português.
- Utilizar linguagem simples e didática.
- Explicar conceitos novos de Java, Spring Boot, JPA e SQL.
- Manter nomes de classes, métodos e anotações em inglês.
- Antes de modificar código, explicar o que será feito.
- 