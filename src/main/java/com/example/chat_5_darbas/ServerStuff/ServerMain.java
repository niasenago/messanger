package com.example.chat_5_darbas.ServerStuff;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain{
    private static Server server;
    private static final int PORT = 4444;          //scan this port from user to create N rooms
    public static void main(String[] args) {
        System.out.println("Server port: " + PORT);
        try {
            System.out.println("Server is running");
            server = new Server(new ServerSocket(PORT));
            server.startServer();                //"endless" while loop blocking operation

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not create server");
        }
    }
}