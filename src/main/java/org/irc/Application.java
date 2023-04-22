package org.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Tomas Kozakas
 */
public class Application {
    public static void main(String[] args) {
        String server = "irc.freenode.net";
        int port = 6667;

        ConnectionHandler connectionHandler = new ConnectionHandler(server, port);
        try {
            connectionHandler.connect();
            System.out.println("Connected to " + server + ":" + port);

            UserInputHandler userInputHandler = new UserInputHandler(connectionHandler.getWriter());
            MessageHandler messageHandler = new MessageHandler();
            UserCommandHandler userCommandHandler = new UserCommandHandler(userInputHandler);

            userInputHandler.sendMessage("NICK tomas147");
            userInputHandler.sendMessage("USER YourUsername 8 * :Tomas");

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                if (messageHandler.isError(userInput)) {
                    System.out.println("Error: " + userInput);
                }
                userCommandHandler.handleCommand(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionHandler.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
