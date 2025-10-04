package com.workshop.lexical;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        App.run("int x = 10;\nif (x > 0) {\n    print(x);\n} else {\n    // Do nothing\n}");
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static void run(String input) {
        try (Reader reader = new StringReader(input)) {
            GeneratedLexer lexer = new GeneratedLexer(reader);
            Token token;
            do {
                token = lexer.yylex();
                System.out.println(token);
            } while (token.type() != TokenConstants.EOF);
            System.out.println("Lexing completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
