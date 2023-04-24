package org.example.irc.game;

import org.example.irc.model.Client;
import org.example.irc.sender.IRCCommandSender;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public class GuessingGame {
    private final Client client;
    private final IRCCommandSender ircCommandSender;

    public GuessingGame(Client client, IRCCommandSender ircCommandSender) {
        this.client = client;
        this.ircCommandSender = ircCommandSender;
    }

    public void startGame(String[] parts) throws IOException {
        if (parts.length == 1) {
            client.setChannel("#guessinggamechannel");
            ircCommandSender.joinChannel("#guessinggamechannel");

            System.out.printf("Welcome to the Number Guessing Game!%n" +
                            "The goal of the game is to guess a randomly generated number within a specified range.%n%n" +
                            "Here are the rules:%n" +
                            "- To start a game, type \"startgame\". The game will generate a random number between %d and %d.%n" +
                            "- To make a guess, type \"guess <number>\". For example: \"guess 50\" will guess the number 50.%n" +
                            "- If your guess is too high, the bot will respond with \"LOWER\".%n" +
                            "- If your guess is too low, the bot will respond with \"HIGHER\".%n" +
                            "- If your guess is correct, the bot will respond with \"YOU WON!\" and start a new game.%n%n" +
                            "- You can message the player by typing \"msg <message>\".%n" +
                            "- You can quit the game at any time by typing \"quitgame\".%n" +
                            "Good luck and have fun!%n%n",
                    0, 100);
            System.out.println("The random number is: " + client.getRandNum());
        } else {
            System.out.println("Usage: startgame");
        }
    }

    public void quitGame(String[] parts) throws IOException {
        if (parts.length == 1) {
            ircCommandSender.leaveChannel(client.getChannel());
            client.setChannel(null);
        } else {
            System.out.println("Usage: quitgame");
        }
    }

    public void guess(String[] parts) throws IOException {
        if (!isClientInGameChannel()) {
            return;
        }

        if (!isGuessCommandValid(parts)) {
            return;
        }

        int guess = Integer.parseInt(parts[1]);
        client.setGuessCount(client.getGuessCount() + 1);

        if (isGuessCorrect(guess)) {
            handleCorrectGuess();
        } else {
            printHigherOrLower(guess);
        }
    }

    private boolean isClientInGameChannel() {
        if (!client.getChannel().equals("#guessinggamechannel")) {
            System.out.println("The client must join a #guessinggamechannel\n" +
                    "Use join <channel> to participate in the game");
            return false;
        }
        return true;
    }

    private boolean isGuessCommandValid(String[] parts) {
        if (parts.length <= 1) {
            System.out.println("Usage: guess <number>");
            return false;
        }

        if (!parts[1].matches("^-?\\d+$")) {
            System.out.println("The guess is not a number");
            return false;
        }

        return true;
    }

    private boolean isGuessCorrect(int guess) {
        return guess == client.getRandNum();
    }

    private void handleCorrectGuess() throws IOException {
        client.setWinCount(client.getWinCount() + 1);
        client.randomiseNumber();

        String message = String.format("%s Won! (Win count: %d, guess count: %d)",
                client.getNick(), client.getWinCount(), client.getGuessCount());

        ircCommandSender.sendMessageToChannel("#guessinggamechannel", message);
        System.out.println(message);
    }

    private void printHigherOrLower(int guess) {
        System.out.println((guess < client.getRandNum()) ? "HIGHER" : "LOWER");
    }
}
