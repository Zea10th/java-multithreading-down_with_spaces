package org.example;

import org.example.client.Client;
import org.example.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Client client = new Client();

        client.startConnection(Server.IPADDRESS, Server.PORT);

        while (true) {
            String line = scanner.nextLine();
            System.out.println(client.sendMessage(line));
            if (line.equals("end")) break;
        }

        client.stopConnection();
    }
}
