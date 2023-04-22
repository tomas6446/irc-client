package org.irc;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public class UserInputHandler {
    private final BufferedWriter writer;

    public UserInputHandler(BufferedWriter writer) {
        this.writer = writer;
    }

    public void sendMessage(String message) throws IOException {
        writer.write(message + "\r\n");
        writer.flush();
    }

    public void joinChannel(String channel) throws IOException {
        sendMessage("JOIN " + channel);
    }

    public void sendMessageToChannel(String channel, String message) throws IOException {
        sendMessage("PRIVMSG " + channel + " :" + message);
    }
}
