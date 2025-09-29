package com.workshop;

import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;

public class LexerRunner {
    // Private constructor to prevent instantiation
    private LexerRunner() {}

    @SuppressWarnings("CallToPrintStackTrace")
    public static void run(String input) {
        try (Reader reader = new StringReader(input)) {
            GeneratedLexer lexer = new GeneratedLexer(reader);
            Token token;
            do {
                token = lexer.yylex();
                System.out.println(token);
            } while (token.type() != TokenType.EOF);
            System.out.println("Lexing completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
