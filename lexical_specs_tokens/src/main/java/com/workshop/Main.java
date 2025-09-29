package com.workshop;

public class Main {
    public static void main(String[] args) {
        LexerRunner.run("int x := 123;\nif x == 10 then print(x);\n// a comment\nx := x + 1.5;");
    }
}