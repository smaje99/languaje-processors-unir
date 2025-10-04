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

{WHITESPACE} { /* skip whitespace */ }

{LINE_COMMENT} { /* ignore single-line comment */ }

{BLOCK_COMMENT} { /* ignore block comment */ }

/* Operators and punctuation */
"==" { makeToken(TokenConstants.EQUALS); }
":=" { makeToken(TokenConstants.ASSIGN); }
"=" { makeToken(TokenConstants.ASSIGN); }
"+" { makeToken(TokenConstants.PLUS); }
"-" { makeToken(TokenConstants.MINUS); }
"*" { makeToken(TokenConstants.MULTIPLY); }
"/" { makeToken(TokenConstants.DIVIDE); }
"(" { makeToken(TokenConstants.LPAREN); }
")" { makeToken(TokenConstants.RPAREN); }
";" { makeToken(TokenConstants.SEMICOLON); }

/* Numbers (FLOAT before INTEGER to match decimal first) */
{FLOAT} { makeToken(TokenConstants.NUMBER); }
{INTEGER} { makeToken(TokenConstants.NUMBER); }

/* Identifiers and reserved words */
{IDENTIFIER} { return makeIdentifierOrReserved(); }

/* Any other character is an error */
[^] { return new Token(TokenConstants.ERROR, yytext(), yyline + 1, yycolumn + 1); }