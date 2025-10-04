package com.workshop.lexical;

import java.util.List;
import java.util.Map;

/**
 * Result of lexical analysis containing tokens and statistics.
 */
public record LexerResult(
    List<Token> tokens,
    Map<TokenConstants, Integer> tokenCounts,
    int totalTokens,
    int errorCount,
    boolean success,
    String message
) {
}
