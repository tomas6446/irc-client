package org.example;

import java.io.*;
import java.net.Socket;

/**
 * @author Tomas Kozakas
 */
public class IRCClient {
    private final String server;
    private final int port;
    private final String nick;
    private final String user;
    private final String channel;
    private Socket socket;
    private BufferedWriter writer;

    public IRCClient(String server, int port, String nick, String user, String channel) {
        this.server = server;
        this.port = port;
        this.nick = nick;
        this.user = user;
        this.channel = channel;
    }

    public void connect() {
        try {
            socket = new Socket(server, port);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            sendCommand("NICK", nick);
            sendCommand("USER", user + " 0 * :" + user);

            // Start a separate thread to handle incoming messages
            new Thread(() -> {
                try {
                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        handleServerMessage(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleServerMessage(String line) throws IOException {
        System.out.println(line);
        if (line.contains("004")) {
            // Connected to the IRC server.
            sendCommand("JOIN", channel);
        } else if (line.contains("433")) {
            System.out.println("Nickname is already in use.");
            socket.close();
        } else if (line.startsWith("PING")) {
            sendCommand("PONG", line.substring(5));
        }
    }

    private void sendCommand(String command, String message) throws IOException {
        writer.write(command + " " + message + "\r\n");
        writer.flush();
    }


    public static void main(String[] args) {
        IRCClient client = new IRCClient("irc.freenode.net", 6667, "MyNick", "MyUser", "#mychannel");
        client.connect();
    }
}
