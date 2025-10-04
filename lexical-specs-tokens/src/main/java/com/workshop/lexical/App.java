package com.workshop.lexical;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // App.run("int x = 10;\nif (x > 0) {\n print(x);\n} else {\n // Do nothing\n}");
        App.runGUI();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private static void run(String input) {
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

    private static void runGUI() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException
                    | UnsupportedLookAndFeelException e) {
                // Ignore and use default look and feel
            }

            LexerGUI gui = new LexerGUI();
            gui.setVisible(true);
        });
    }
}
