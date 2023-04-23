package org.irc.handler;

import org.irc.sender.IRCCommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Tomas Kozakas
 */
public class InputHandler {
    private final IRCCommandSender ircCommandSender;

    public InputHandler(IRCCommandSender ircCommandSender) {
        this.ircCommandSender = ircCommandSender;
    }


    public void handleConsoleInput() throws IOException {
        try (BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                handleCommand(userInput);
            }
        }
    }

    public void handleCommand(String command) throws IOException {
        String[] parts = command.split(" ", 2);
        String action = parts[0].toLowerCase();

        switch (action) {
            case "join" -> {
                if (parts.length > 1) {
                    ircCommandSender.joinChannel(parts[1]);
                } else {
                    System.out.println("Usage: join <channel>");
                }
            }
            case "msg" -> {
                if (parts.length > 1) {
                    String[] messageParts = parts[1].split(" ", 2);
                    if (messageParts.length > 1) {
                        ircCommandSender.sendMessageToChannel(messageParts[0], messageParts[1]);
                    } else {
                        System.out.println("Usage: msg <channel> <message>");
                    }
                } else {
                    System.out.println("Usage: msg <channel> <message>");
                }
            }
            case "part" -> {
                if (parts.length > 1) {
                    ircCommandSender.leaveChannel(parts[1]);
                } else {
                    System.out.println("Usage: part <channel>");
                }
            }
            default -> ircCommandSender.sendMessage(command);
        }
    }
}