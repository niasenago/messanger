package com.example.chat_5_darbas.ClientStuff;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    private Socket clientSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;

    public Client(Socket clientSocket,String clientUserName) {
        try {
            this.clientSocket = clientSocket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.clientUserName = clientUserName;
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(clientSocket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(){//String messageToSend
        try{
            bufferedWriter.write(clientUserName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            /*TODO: change this to textfield*/
            Scanner scanner = new Scanner(System.in);
            while(clientSocket.isConnected()){
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(clientUserName + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        }catch (IOException e) {
            e.printStackTrace();
            closeEverything(clientSocket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage(){     //receiveMessageFromServer
        new Thread(new Runnable() {     //mes tiesiog sukuriame ogjekta Thread ir paleidziame
            @Override
            public void run() {         /** what is inside method run will be executed on separete thread*/
                String messageFromGroupChat;
                while(clientSocket.isConnected()){
                    try{
                        messageFromGroupChat = bufferedReader.readLine();
                        //ClientController.addLabel(messageFromGroupChat, vbox);//i added this
                        System.out.println(messageFromGroupChat);
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeEverything(clientSocket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }


    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
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

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();

        Socket socket = new Socket("localhost", 4444);
        Client client = new Client(socket, username);
        client.listenForMessage();  //infinite loop on separete threads
        client.sendMessage();       //infinite loop on separete threads
    }

}
