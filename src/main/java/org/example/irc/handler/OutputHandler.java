package org.example.irc.handler;

import org.example.irc.sender.IRCCommandSender;

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

    public void handleOutput() {
        new Thread(() -> {
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("PING")) {
                        String pongMessage = line.replace("PING", "PONG");
                        ircCommandSender.sendMessage(pongMessage);
                    } else {
                        processMessage(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void processMessage(String rawMessage) {
        String[] parts = rawMessage.split(" ");
        String command = parts[1];

        switch (command) {
            case "JOIN" -> {
                String joiner = parts[0].substring(1, parts[0].indexOf("!"));
                String channel = parts[2].substring(1);
                System.out.println(joiner + " has joined the channel " + channel);
            }
            case "PRIVMSG" -> {
                String sender = parts[0].substring(1, parts[0].indexOf("!"));
                String target = parts[2];
                String message = rawMessage.substring(rawMessage.indexOf(":", 1) + 1);
                System.out.println(sender + " " + target + ": " + message);
            }
            case "353" -> {
                String channelName = parts[5];
                String users = rawMessage.substring(rawMessage.indexOf(":", 1) + 1);
                System.out.println("Users in " + channelName + ": \n" + users);
            }
            case "366" -> {
                String endOfNamesChannel = parts[4];
                System.out.println("End of /NAMES list for " + endOfNamesChannel);
            }
            default -> System.out.println("Unprocessed message: " + rawMessage);
        }
    }

}
