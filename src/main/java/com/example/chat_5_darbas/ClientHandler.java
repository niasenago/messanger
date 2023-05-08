package com.example.chat_5_darbas;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();//fiksuojame visus naujus klientus
    public ClientHandler(Socket clientSocket) {
        try{
            this.clientSocket = clientSocket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); // mes siunciam klientui
            this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //klientas mums siuncia
            this.clientUserName = bufferedReader.readLine();

            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUserName + "has joined the chat.");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        String messageFromClient;
        while(clientSocket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine();  //blocking operation
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(clientSocket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
    private void broadcastMessage(String message){
        for(ClientHandler clientHandler : clientHandlers){  //for each clientHandler in ArrayList clientHandlers
            try{
                if(!clientHandler.clientUserName.equals(this.clientUserName)){
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(clientSocket, bufferedReader, bufferedWriter);
            }
        }
    }

    private void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUserName + "has left the chat.");
    }
    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
        try{
            if(socket != null){
                socket.close();
            }
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
        }catch (IOException e){
            System.out.println("Error. Could not close socket / bufferedReader / bufferedWriter");
            e.printStackTrace();
        }
    }
}
