package com.workshop;

/**
 * Token types used by the lexical analyzer.
 */
public enum TokenType {
    EOF,
    IDENTIFIER,
    RESERVED,
    NUMBER,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    ASSIGN,
    EQUALS,
    LPAREN,
    RPAREN,
    SEMICOLON,
    COMMENT,
    ERROR
}