package org.irc;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author Tomas Kozakas
 */
public class ConnectionHandler {
    private final String server;
    private final int port;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ConnectionHandler(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(server, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }
}
