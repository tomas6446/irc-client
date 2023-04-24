package org.example.irc.sender;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public class IRCCommandSender {
    private final BufferedWriter writer;

    public IRCCommandSender(BufferedWriter writer) {
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

    public void leaveChannel(String channel) throws IOException {
        sendMessage("PART " + channel);
    }

    public void register(String password, String email) throws IOException {
        sendMessage("PRIVMSG NickServ :REGISTER " + password + " " + email);
    }

    public void identify(String nickname, String password) throws IOException {
        sendMessage("PRIVMSG NickServ :IDENTIFY " + nickname + " " + password);
    }

    public void connect(String nick, String user) throws IOException {
        sendMessage("NICK " + nick);
        sendMessage("USER " + user + " 8 * :" + user);
    }
}
