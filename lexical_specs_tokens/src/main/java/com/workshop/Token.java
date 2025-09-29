package com.workshop;

/**
 * Immutable token object produced by the lexer.
 */
public record Token(TokenType type, String lexeme, int line, int column) {
    @Override
    public String toString() {
        return String.format("Token[type=%s, lexeme='%s', line=%d, col=%d]",
                type, lexeme, line, column);
    }
}
