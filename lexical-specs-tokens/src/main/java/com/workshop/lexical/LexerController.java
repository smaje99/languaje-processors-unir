package com.workshop.lexical;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Controller that processes input text through the lexer
 * and provides token analysis and statistics.
 */
public final class LexerController {
    public LexerController() {
      // No initialization needed
    }

    /**
     * Process input text through the lexer
     *
     * @param input The source code to analyze
     * @return LexerResult containing tokens and statistics
     */
    public LexerResult analyze(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new LexerResult(
                    List.of(),
                    Map.of(),
                    0,
                    0,
                    true,
                    "Input is empty");
        }

        List<Token> tokens = new ArrayList<>();
        Map<TokenConstants, Integer> tokenCounts = new EnumMap<>(TokenConstants.class);
        int errorCount = 0;

        try (Reader reader = new StringReader(input)) {
            GeneratedLexer lexer = new GeneratedLexer(reader);
            Token token;

            do {
                token = lexer.yylex();
                tokens.add(token);
                // Count tokens by type
                tokenCounts.put(token.type(), tokenCounts.getOrDefault(token.type(), 0) + 1);

                // Count errors
                if (token.type() == TokenConstants.ERROR) {
                    errorCount++;
                }
            } while (token.type() != TokenConstants.EOF);

            // Don't count EOF in total tokens
            int totalTokens = tokens.size() - 1;

            return new LexerResult(
                    tokens,
                    tokenCounts,
                    totalTokens,
                    errorCount,
                    errorCount == 0,
                    errorCount == 0 ? "Lexing completed successfully" : "Lexing completed with errors");
        } catch (Exception e) {
            return new LexerResult(
                    List.of(),
                    Map.of(),
                    0,
                    0,
                    false,
                    "Error during lexical analysis: " + e.getMessage());
        }
    }

    /**
     * Format tokens as a readable string
     * @param tokens List of tokens to format
     * @return Formatted string representation
     */
    public String formatTokens(List<Token> tokens) {
        StringBuilder sb = new StringBuilder();
        for (Token token : tokens) {
            sb.append(token.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Generate statistics summary from the lexer result
     * @param result The lexer result to summarize
     * @return Formatted statistics string
     */
    public String generateStatistics(LexerResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== LEXICAL ANALYSIS STATISTICS ===\n\n");

        if (!result.success()) {
            sb.append("ERROR: ").append(result.message()).append("\n");
            return sb.toString();
        }

        sb.append("Total Tokens: ").append(result.totalTokens()).append("\n");
        sb.append("Errors Found: ").append(result.errorCount()).append("\n\n");

        sb.append("Token Distribution:\n");
        result.tokenCounts().entrySet().stream()
            .filter(entry -> entry.getKey() != TokenConstants.EOF)
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .forEach(entry ->
                sb.append(String.format("  %-20s: %d%n",
                    entry.getKey().name(),
                    entry.getValue()))
            );

        return sb.toString();
    }
}
