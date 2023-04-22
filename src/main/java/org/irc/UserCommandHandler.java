package org.irc;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public class UserCommandHandler {
    private final UserInputHandler userInputHandler;

    public UserCommandHandler(UserInputHandler userInputHandler) {
        this.userInputHandler = userInputHandler;
    }

    public void handleCommand(String command) throws IOException {
        String[] parts = command.split(" ", 2);
        String action = parts[0].toLowerCase();

        switch (action) {
            case "join":
                if (parts.length > 1) {
                    userInputHandler.joinChannel(parts[1]);
                } else {
                    System.out.println("Usage: join <channel>");
                }
                break;
            case "msg":
                if (parts.length > 1) {
                    String[] messageParts = parts[1].split(" ", 2);
                    if (messageParts.length > 1) {
                        userInputHandler.sendMessageToChannel(messageParts[0], messageParts[1]);
                    } else {
                        System.out.println("Usage: msg <channel> <message>");
                    }
                } else {
                    System.out.println("Usage: msg <channel> <message>");
                }
                break;
            default:
                System.out.println("Unknown command. Supported commands: join, msg");
                break;
        }
    }
}