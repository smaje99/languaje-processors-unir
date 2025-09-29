/* lexer.flex: clean, commented JFlex specification
 * package: com.workshop
 * generates class: GeneratedLexer
 * returns Token objects
 */

package com.workshop;

%%

%public
%class GeneratedLexer
%unicode
%line
%column
%implements java.io.Serializable
%type Token
%char
%eofval{ return new Token(TokenType.EOF, "<EOF>", yyline+1, yycolumn+1); }
%{

import java.util.Map;
import java.util.HashMap;

private static final Map<String, TokenType> RESERVED_MAP = new HashMap<>();
static {
    RESERVED_MAP.put("if", TokenType.RESERVED);
    RESERVED_MAP.put("then", TokenType.RESERVED);
    RESERVED_MAP.put("else", TokenType.RESERVED);
    RESERVED_MAP.put("int", TokenType.RESERVED);
    RESERVED_MAP.put("while", TokenType.RESERVED);
    RESERVED_MAP.put("do", TokenType.RESERVED);
    RESERVED_MAP.put("print", TokenType.RESERVED);
}

private Token makeToken(TokenType type) {
    return new Token(type, yytext(), yyline + 1, yycolumn + 1);
}

private Token makeIdentifierOrReserved() {
    String lex = yytext();
    TokenType type = RESERVED_MAP.get(lex.toLowerCase());
    if (type != null) {
        return new Token(type, lex, yyline + 1, yycolumn + 1);
    }
    return new Token(TokenType.IDENTIFIER, lex, yyline + 1, yycolumn + 1);
}

%}

ALPHA = [a-zA-Z_]
DIGIT = [0-9]
ID = {ALPHA}({ALPHA}|{DIGIT})*
INT = {DIGIT}+
FLOAT = {DIGIT}+("."{DIGIT}+)?
WS = [ \t\r\n]+
LINE_COMMENT = "//".*
BLOCK_COMMENT = "/*" ([^*] | "*" [^/])* "*/"

%%

{WS}             { /* skip whitespace */ }

{LINE_COMMENT}   { /* ignore single-line comment */ }

{BLOCK_COMMENT}  { /* ignore block comment */ }

"=="             { return makeToken(TokenType.EQUALS); }
":="             { return makeToken(TokenType.ASSIGN); }
"="              { return makeToken(TokenType.ASSIGN); }
"+"              { return makeToken(TokenType.PLUS); }
"-"              { return makeToken(TokenType.MINUS); }
"*"              { return makeToken(TokenType.MULTIPLY); }
"/"              { return makeToken(TokenType.DIVIDE); }
"("              { return makeToken(TokenType.LPAREN); }
")"              { return makeToken(TokenType.RPAREN); }
";"              { return makeToken(TokenType.SEMICOLON); }

{FLOAT}          { return makeToken(TokenType.NUMBER); }

{ID}             { return makeIdentifierOrReserved(); }

.                { return new Token(TokenType.ERROR, yytext(), yyline + 1, yycolumn + 1); }
