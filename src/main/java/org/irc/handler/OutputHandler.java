package org.irc.handler;

import org.irc.model.Client;
import org.irc.sender.IRCCommandSender;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public class OutputHandler {
    private final Client client;
    private final IRCCommandSender ircCommandSender;
    private final BufferedReader reader;

    public OutputHandler(Client client, IRCCommandSender ircCommandSender, BufferedReader reader) {
        this.client = client;
        this.ircCommandSender = ircCommandSender;
        this.reader = reader;
    }

    public void handleOutput() {
        new Thread(() -> {
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("PING")) {
                        String pongMessage = line.replace("PING", "PONG");
                        ircCommandSender.sendMessage(pongMessage);
                    } else {
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
