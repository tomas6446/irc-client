package org.irc;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * @author Tomas Kozakas
 */
public class Application {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String server = "irc.freenode.net";
        //String server = "irc.rizon.net";
        int port = 6667;

        String nick = dotenv.get("IRC_NICKNAME");
        String user = dotenv.get("IRC_USERNAME");
        String password = dotenv.get("IRC_PASSWORD");
        String email = dotenv.get("IRC_EMAIL");

        IRCClient ircClient = new IRCClient(server, port);

        ircClient.connect(nick, user, password, email);
        System.out.println("Connected to " + server + ":" + port);
    }
}
