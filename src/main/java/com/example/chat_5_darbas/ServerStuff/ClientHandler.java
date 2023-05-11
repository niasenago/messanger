package com.example.chat_5_darbas.ServerStuff;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;
    private  String LOG_FILE_NAME;
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();//fiksuojame visus naujus klientus
    public ClientHandler(Socket clientSocket, String logFileName) {//TODO: change this add clientusername
        try{
            this.clientSocket = clientSocket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); // mes siunciam klientui
            this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //klientas mums siuncia
            //System.out.println(this.clientUserName);
            this.clientUserName = bufferedReader.readLine(); // TODO: change this
            this.LOG_FILE_NAME = logFileName;
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUserName + " has joined the chat.");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {     //thread.start();                                    //listens for the message from client
        String messageFromClient;
        while(clientSocket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine();  //blocking operation // is waiting for the message
                broadcastMessage(messageFromClient);
                System.out.println("client handler listen works. it received message");
            } catch(SocketException e){
                e.printStackTrace();
                closeEverything(clientSocket, bufferedReader, bufferedWriter);
                break;
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(clientSocket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
    private void broadcastMessage(String message){

        /**Well it works so do not change this**/
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(LOG_FILE_NAME, true));
            writer.write(message);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**TODO change this code*/

        String[] parts;
        String name;
        String sentMessage = message;
        //delete Name: part of string
        int colonIndex = sentMessage.indexOf(":");
        sentMessage = colonIndex != -1 ? sentMessage.substring(colonIndex + 1).trim() : sentMessage.trim();
        if(sentMessage.startsWith("@")){                        //send message to only one client
            System.out.println("dm work");
            parts = sentMessage.split("@", 2);
            name = parts[1].trim();
            sentMessage = parts[0].trim();
            name = name.split(" ", 2)[0];           // delete message part of string from name

            for(ClientHandler clientHandler: clientHandlers){
                try{
                    if(clientHandler.clientUserName.equals(name)){
                        clientHandler.bufferedWriter.write(message);
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            for(ClientHandler clientHandler : clientHandlers){  //sent message to all clients ;for each clientHandler in ArrayList clientHandlers
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
    }

    private void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUserName + " has left the chat.");
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
