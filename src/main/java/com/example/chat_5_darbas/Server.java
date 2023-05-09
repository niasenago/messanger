package com.example.chat_5_darbas;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final int PORT = 4444;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients;
    private String logFileName;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        logFileName = String.valueOf(serverSocket.getLocalPort());
        logFileName = logFileName + "_room_log.txt";
    }
    public void startServer(){
        try{
            while(!serverSocket.isClosed()){
                Socket clientSocket = serverSocket.accept();
                System.out.println("A new client has connected!");
                ClientHandler clientHandler = new ClientHandler(clientSocket, logFileName);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeServerSocket(){
        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}

