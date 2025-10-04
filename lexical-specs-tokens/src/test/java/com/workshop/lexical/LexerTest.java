package com.workshop.lexical;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LexerTest {
    @Test
    void matchIdentifier() throws IOException {
        String input = "id1";
        GeneratedLexer lexer = new GeneratedLexer(new StringReader(input));
        Token token = lexer.yylex();
        assertEquals(TokenConstants.IDENTIFIER, token.type());
    }

    @Test
    void testSimpleAssignment() throws IOException {
        String input = "int a := 10;";
        GeneratedLexer lexer = new GeneratedLexer(new StringReader(input));
        assertEquals(TokenConstants.RESERVED, lexer.yylex().type()); // int
        assertEquals(TokenConstants.IDENTIFIER, lexer.yylex().type()); // a
        assertEquals(TokenConstants.ASSIGN, lexer.yylex().type()); // :=
        assertEquals(TokenConstants.NUMBER, lexer.yylex().type()); // 10
        assertEquals(TokenConstants.SEMICOLON, lexer.yylex().type()); // ;
    }

    @Test
    void testArithmeticExpression() throws IOException {
        String input = "x = 5 + 3 * (2 - 1);";
        GeneratedLexer lexer = new GeneratedLexer(new StringReader(input));
        assertEquals(TokenConstants.IDENTIFIER, lexer.yylex().type()); // x
        assertEquals(TokenConstants.ASSIGN, lexer.yylex().type()); // =
        assertEquals(TokenConstants.NUMBER, lexer.yylex().type()); // 5
        assertEquals(TokenConstants.PLUS, lexer.yylex().type()); // +
        assertEquals(TokenConstants.NUMBER, lexer.yylex().type()); // 3
        assertEquals(TokenConstants.MULTIPLY, lexer.yylex().type()); // *
        assertEquals(TokenConstants.LPAREN, lexer.yylex().type()); // (
        assertEquals(TokenConstants.NUMBER, lexer.yylex().type()); // 2
        assertEquals(TokenConstants.MINUS, lexer.yylex().type()); // -
        assertEquals(TokenConstants.NUMBER, lexer.yylex().type()); // 1
        assertEquals(TokenConstants.RPAREN, lexer.yylex().type()); // )
        assertEquals(TokenConstants.SEMICOLON, lexer.yylex().type()); // ;
    }

    @Test
    void testIfElseStatement() throws IOException {
        String input = "int x = 10;\nif (x > 0) {\n    print(x);\n} else {\n    // Do nothing\n}";
        GeneratedLexer lexer = new GeneratedLexer(new StringReader(input));
        assertEquals(TokenConstants.RESERVED, lexer.yylex().type()); // int
        assertEquals(TokenConstants.IDENTIFIER, lexer.yylex().type()); // x
        assertEquals(TokenConstants.ASSIGN, lexer.yylex().type()); // :=
        assertEquals(TokenConstants.NUMBER, lexer.yylex().type()); // 10
        assertEquals(TokenConstants.SEMICOLON, lexer.yylex().type()); // ;
        assertEquals(TokenConstants.RESERVED, lexer.yylex().type()); // if
        assertEquals(TokenConstants.LPAREN, lexer.yylex().type()); // (
        assertEquals(TokenConstants.IDENTIFIER, lexer.yylex().type()); // x
        assertEquals(TokenConstants.GREATER_THAN, lexer.yylex().type()); // >
        assertEquals(TokenConstants.NUMBER, lexer.yylex().type()); // 0
        assertEquals(TokenConstants.RPAREN, lexer.yylex().type()); // )
        assertEquals(TokenConstants.LBRACE, lexer.yylex().type()); // {
        assertEquals(TokenConstants.RESERVED, lexer.yylex().type()); // print
        assertEquals(TokenConstants.LPAREN, lexer.yylex().type()); // (
        assertEquals(TokenConstants.IDENTIFIER, lexer.yylex().type()); // x
        assertEquals(TokenConstants.RPAREN, lexer.yylex().type()); // )
        assertEquals(TokenConstants.SEMICOLON, lexer.yylex().type()); // ;
        assertEquals(TokenConstants.RBRACE, lexer.yylex().type()); // }
        assertEquals(TokenConstants.RESERVED, lexer.yylex().type()); // else
        assertEquals(TokenConstants.LBRACE, lexer.yylex().type()); // {
        // Comment is ignored
        assertEquals(TokenConstants.RBRACE, lexer.yylex().type()); // }
    }
}
