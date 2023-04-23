package org.irc.handler;

import org.irc.sender.IRCCommandSender;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public class OutputHandler {
    private final IRCCommandSender ircCommandSender;
    private final BufferedReader reader;

    public OutputHandler(IRCCommandSender ircCommandSender, BufferedReader reader) {
        this.ircCommandSender = ircCommandSender;
        this.reader = reader;
    }

    public void startListening() {
        new Thread(() -> {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    if (message.startsWith("PING")) {
                        String pongMessage = message.replace("PING", "PONG");
                        ircCommandSender.sendMessage(pongMessage);
                    } else {
                        System.out.println(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
