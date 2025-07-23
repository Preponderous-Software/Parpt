package com.preponderous.parpt.util;

/**
 * Provides an interface for console-based user input operations.
 */
public interface ConsoleInputProvider {
    /**
     * Reads a line of input from the user.
     *
     * @param prompt Message to display to the user
     * @return The line of input entered by the user
     */
    String readLine(String prompt);
}