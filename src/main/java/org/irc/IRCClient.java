package org.irc;

import org.irc.connection.Connection;
import org.irc.handler.InputHandler;
import org.irc.handler.OutputHandler;
import org.irc.model.Client;
import org.irc.sender.IRCCommandSender;

import java.io.IOException;

/**
 * @author Tomas Kozakas
 */
public class IRCClient {
    private final Connection connectionHandler;

    public IRCClient(String server, int port) {
        connectionHandler = new Connection(server, port);
    }

    public void connect(String nick, String user, String password, String email) {
        try {
            connectionHandler.connect();
            Client client = new Client(nick, user);

            IRCCommandSender ircCommandSender = new IRCCommandSender(connectionHandler.getWriter());

            ircCommandSender.register(password, email);
            ircCommandSender.identify(nick, user);
            ircCommandSender.connect(nick, user);

            InputHandler inputHandler = new InputHandler(client, ircCommandSender);
            OutputHandler outputHandler = new OutputHandler(ircCommandSender, connectionHandler.getReader());

            outputHandler.handleOutput();
            inputHandler.handleInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
