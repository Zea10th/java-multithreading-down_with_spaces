package org.example.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    private ServerSocketChannel serverChannel;
    private volatile boolean isRunning = true;
    public static final int PORT = 60666;
    public static final String IPADDRESS = "localhost";

    public void startServer() throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(IPADDRESS, PORT));
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void run() {
        try {
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (isRunning) {
            try (SocketChannel socketChannel = serverChannel.accept()) {
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(inputBuffer);

                    if (bytesCount == -1) break;

                    final String msg = new String(inputBuffer.array(), 0,
                            bytesCount, StandardCharsets.UTF_8);

                    inputBuffer.clear();

                    System.out.println("Client sent: " + msg);

                    String response;
                    if (msg.equals("end")) {
                        System.out.println("Server stopped.");;
                        setRunning(false);
                        break;
                    } else {
                        response = "Server returns: " + deleteSpaces(msg);
                    }

                    socketChannel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
                }
            } catch (IOException err) {
                System.out.println(err.getMessage());
            }
        }
    }

    public String deleteSpaces(String line) {
        return line.replaceAll(" ", "");
    }
}

