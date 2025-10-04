/* lexer.flex: clean, commented JFlex specification
 * package: com.workshop.lexical
 * generates class: GeneratedLexer
 * returns Token objects
 */

package com.workshop.lexical;

import java.util.Map;
import java.util.HashMap;

%%

%public
%final
%class GeneratedLexer
%unicode
%line
%column
%type Token
%char

%eofval{
    return new Token(TokenConstants.EOF, "<EOF>", yyline+1, yycolumn+1);
%eofval}

%{
    private static final Map<String, TokenConstants> RESERVED_MAP = new HashMap<>();
    static {
        RESERVED_MAP.put("if", TokenConstants.RESERVED);
        RESERVED_MAP.put("then", TokenConstants.RESERVED);
        RESERVED_MAP.put("else", TokenConstants.RESERVED);
        RESERVED_MAP.put("int", TokenConstants.RESERVED);
        RESERVED_MAP.put("while", TokenConstants.RESERVED);
        RESERVED_MAP.put("do", TokenConstants.RESERVED);
        RESERVED_MAP.put("print", TokenConstants.RESERVED);
    }

    private Token makeToken(TokenConstants type) {
        return new Token(type, yytext(), yyline+1, yycolumn+1);
    }

    private Token makeIdentifierOrReserved() {
        String lex = yytext();
        TokenConstants type = RESERVED_MAP.get(lex.toLowerCase());
        if (type != null) {
            return new Token(type, lex, yyline + 1, yycolumn + 1);
        }
        return new Token(TokenConstants.IDENTIFIER, lex, yyline + 1, yycolumn + 1);
    }
%}

/* Macros definitions */
DIGIT = [0-9]
LETTER = [a-zA-Z]
WHITESPACE = [ \t\n\r]+
IDENTIFIER = {LETTER}({LETTER}|{DIGIT})*
INTEGER = {DIGIT}+
FLOAT = {DIGIT}+\.{DIGIT}+
LINE_COMMENT = "//"[^\r\n]*
BLOCK_COMMENT = "/*" ~"*/"

%%

/* Lexical rules */

{WHITESPACE}        { /* skip whitespace */ }

{LINE_COMMENT}      { /* ignore single-line comment */ }

{BLOCK_COMMENT}     { /* ignore block comment */ }

/* Operators and punctuation (two-char operators first) */
"=="                { return makeToken(TokenConstants.EQUALS); }
"!="                { return makeToken(TokenConstants.NOT_EQUALS); }
"<="                { return makeToken(TokenConstants.LESS_EQUAL); }
">="                { return makeToken(TokenConstants.GREATER_EQUAL); }
":="                { return makeToken(TokenConstants.ASSIGN); }

/* Single-char operators */
"="                 { return makeToken(TokenConstants.ASSIGN); }
"<"                 { return makeToken(TokenConstants.LESS_THAN); }
">"                 { return makeToken(TokenConstants.GREATER_THAN); }
"+"                 { return makeToken(TokenConstants.PLUS); }
"-"                 { return makeToken(TokenConstants.MINUS); }
"*"                 { return makeToken(TokenConstants.MULTIPLY); }
"/"                 { return makeToken(TokenConstants.DIVIDE); }
"("                 { return makeToken(TokenConstants.LPAREN); }
")"                 { return makeToken(TokenConstants.RPAREN); }
"{"                 { return makeToken(TokenConstants.LBRACE); }
"}"                 { return makeToken(TokenConstants.RBRACE); }
";"                 { return makeToken(TokenConstants.SEMICOLON); }

/* Numbers (FLOAT before INTEGER to match decimal numbers first) */
{FLOAT}             { return makeToken(TokenConstants.NUMBER); }
{INTEGER}           { return makeToken(TokenConstants.NUMBER); }

/* Identifiers and reserved words */
{IDENTIFIER}        { return makeIdentifierOrReserved(); }

/* Any other character is an error */
[^]                 { return new Token(TokenConstants.ERROR, yytext(), yyline + 1, yycolumn + 1); }