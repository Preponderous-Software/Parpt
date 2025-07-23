package com.preponderous.parpt.util;

import org.springframework.stereotype.Component;

@Component
public class SystemConsoleInputProvider implements ConsoleInputProvider {
    @Override
    public String readLine(String prompt) {
        if (System.console() == null) {
            // Fallback for environments without a console (like IDE tests)
            System.out.print(prompt);
            return new java.util.Scanner(System.in).nextLine();
        }
        return System.console().readLine(prompt);
    }
}