package com.example.chat_5_darbas;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private Label serverStatus;
    private Server server;
    private static final int PORT = 4444;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Server is running");
            server = new Server(new ServerSocket(PORT));
            server.startServer();   //blocking operation

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not create server");
        }
        if(server != null) {
            serverStatus.setText("Server is working");
        }else
            serverStatus.setText("Could not create a server.");
    }
}