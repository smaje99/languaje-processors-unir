# Lexical Analyzer (JFlex + Java)

This project demonstrates a clean, production-friendly lexical analyzer using JFlex and Java.

## Goals
- Provide a clean JFlex specification (`lexer.flex`) that emits a `Token` object.
- Keep project build automated with Maven (generates scanner during build).
- Provide a `LexerRunner` to test quickly from the command line.

## Requirements
- JDK 17+ (or JDK 11 minimum)
- Maven 3.6+

## Build and run

1. Build the project (Maven will run JFlex plugin and generate the scanner):

```bash
mvn clean package

Run the runner that tokenizes a sample string:

java -cp target/classes com.example.lexer.LexerRunner

Or use Maven exec plugin:

mvn -q exec:java -Dexec.mainClass="com.example.lexer.LexerRunner"
```

## Notes

The JFlex source is at src/main/jflex/lexer.flex.

The generated scanner will be placed at target/generated-sources/jflex and compiled into target/classes automatically during mvn package.