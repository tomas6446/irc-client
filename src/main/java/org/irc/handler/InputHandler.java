package org.irc.handler;

import org.irc.game.GuessingGame;
import org.irc.model.Client;
import org.irc.sender.IRCCommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Tomas Kozakas
 */
public class InputHandler {
    private final Client client;
    private final IRCCommandSender ircCommandSender;
    private final GuessingGame guessingGame;

    public InputHandler(Client client, IRCCommandSender ircCommandSender) {
        this.client = client;
        this.ircCommandSender = ircCommandSender;
        this.guessingGame = new GuessingGame(client, ircCommandSender);
    }


    public void handleInput() throws IOException {
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
            case "startgame" -> guessingGame.startGame(parts);
            case "guess" -> guessingGame.guess(parts);
            case "quitgame" -> guessingGame.quitGame(parts);
            case "msg" -> message(parts);
            default -> ircCommandSender.sendMessage(command);
        }
    }

    private void message(String[] parts) throws IOException {
        if (client.getChannel() != null) {
            if (parts.length > 1) {
                ircCommandSender.sendMessageToChannel(client.getChannel(), parts[1]);
                System.out.println(client.getNick() + ": " + parts[1]);
            } else {
                System.out.println("Usage: msg <message>");
            }
        } else {
            System.out.println("You must enter a channel first to use this command");
        }
    }


}