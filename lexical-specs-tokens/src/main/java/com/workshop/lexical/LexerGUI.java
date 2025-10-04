package com.workshop.lexical;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * Graphical User Interface for the Lexical Analyzer
 * Provides input/output panels and token statistics
 */
public final class LexerGUI extends JFrame {

    private static final String ARIAL_FONT = "Arial";
    private static final String CONSOLAS_FONT = "Consolas";

    private final transient LexerController controller = new LexerController();

    private JTextArea inputArea;
    private JTextArea outputArea;
    private JTextArea statsArea;
    private JButton analyzeButton;

    public LexerGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Lexical Analyzer - Workshop");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        // Main layout
        setLayout(new BorderLayout(10, 10));

        // Top button panel
        add(createButtonPanel(), BorderLayout.NORTH);

        // Center panel with input/output
        add(createCenterPanel(), BorderLayout.CENTER);

        // Bottom statistics panel
        add(createStatsPanel(), BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JButton loadSampleButton;
        JButton clearButton;
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setBackground(new Color(240, 240, 245));

        // Analyze button
        analyzeButton = new JButton("Analyze (Lexer)");
        analyzeButton.setFont(new Font(ARIAL_FONT, Font.BOLD, 12));
        analyzeButton.setFocusPainted(false);
        analyzeButton.addActionListener(this::handleAnalyze);

        // Clear button
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font(ARIAL_FONT, Font.PLAIN, 12));
        clearButton.addActionListener(this::handleClear);

        // Load sample button
        loadSampleButton = new JButton("Load Sample");
        loadSampleButton.setFont(new Font(ARIAL_FONT, Font.PLAIN, 12));
        loadSampleButton.addActionListener(this::handleLoadSample);

        panel.add(analyzeButton);
        panel.add(clearButton);
        panel.add(loadSampleButton);
        panel.add(Box.createHorizontalStrut(20));

        // Info label
        JLabel infoLabel = new JLabel("Enter source code and click 'Analyze' to tokenize");
        infoLabel.setFont(new Font(ARIAL_FONT, Font.ITALIC, 11));
        infoLabel.setForeground(Color.GRAY);
        panel.add(infoLabel);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBorder(new EmptyBorder(0, 10, 10, 10));

        // Input panel
        panel.add(createInputPanel());

        // Output panel
        panel.add(createOutputPanel());

        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JLabel label = new JLabel("Source Code Input:");
        label.setFont(new Font(ARIAL_FONT, Font.BOLD, 13));
        panel.add(label, BorderLayout.NORTH);

        inputArea = new JTextArea();
        inputArea.setFont(new Font(CONSOLAS_FONT, Font.PLAIN, 13));
        inputArea.setLineWrap(false);
        inputArea.setTabSize(4);
        inputArea.setText("// Enter your code here\nint x = 10;");

        JScrollPane scrollPane = new JScrollPane(inputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JLabel label = new JLabel("Lexer Output (Tokens):");
        label.setFont(new Font(ARIAL_FONT, Font.BOLD, 13));
        panel.add(label, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setFont(new Font(CONSOLAS_FONT, Font.PLAIN, 12));
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(250, 250, 250));
        outputArea.setLineWrap(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new EmptyBorder(0, 10, 10, 10));

        JLabel label = new JLabel("Statistics:");
        label.setFont(new Font(ARIAL_FONT, Font.BOLD, 13));
        panel.add(label, BorderLayout.NORTH);

        statsArea = new JTextArea(8, 0);
        statsArea.setFont(new Font(CONSOLAS_FONT, Font.PLAIN, 11));
        statsArea.setEditable(false);
        statsArea.setBackground(new Color(245, 245, 250));

        JScrollPane scrollPane = new JScrollPane(statsArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void handleAnalyze(@SuppressWarnings("unused") ActionEvent e) {
        String input = inputArea.getText();

        // Show loading state
        analyzeButton.setEnabled(false);
        analyzeButton.setText("Analyzing...");
        outputArea.setText("Processing...");
        statsArea.setText("");

        // Perform analysis
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            private LexerResult result;

            @Override
            protected Void doInBackground() {
                result = controller.analyze(input);
                return null;
            }

            @Override
            protected void done() {
                if (result.success()) {
                    outputArea.setText(controller.formatTokens(result.tokens()));
                    statsArea.setText(controller.generateStatistics(result));

                    if (result.errorCount() > 0) {
                        JOptionPane.showMessageDialog(
                            LexerGUI.this,
                            "Analysis completed with " + result.errorCount() + " error(s).",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                        );
                    }
                } else {
                    outputArea.setText("ERROR: " + result.message());
                    statsArea.setText("");
                    JOptionPane.showMessageDialog(
                        LexerGUI.this,
                        result.message(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }

                analyzeButton.setEnabled(true);
                analyzeButton.setText("Analyze (Lexer)");
            }
        };

        worker.execute();
    }

    private void handleClear(@SuppressWarnings("unused") ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(
            this,
            "Clear all content?",
            "Confirm Clear",
            JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            inputArea.setText("");
            outputArea.setText("");
            statsArea.setText("");
        }
    }

    private void handleLoadSample(@SuppressWarnings("unused") ActionEvent e) {
        String sample = """
                // Sample program
                int x = 10;
                int y = 20;

                if (x < y) {
                    print(x);
                } else {
                    print(y);
                }

                while (x <= 100) {
                    x = x + 1;
                }
                """;

        inputArea.setText(sample);
    }
}
