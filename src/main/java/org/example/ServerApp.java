package org.example;

import org.example.server.Server;

public class ServerApp {
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
