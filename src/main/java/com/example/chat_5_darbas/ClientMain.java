package com.example.chat_5_darbas;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void ClientMain(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();

        Socket socket = new Socket("localhost", 4444);
        Client client = new Client(socket, username);
        client.listenForMessage();  //infinite loop on separete threads
        client.sendMessage();       //infinite loop on separete threads

    }
}
