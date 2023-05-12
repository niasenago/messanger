package com.example.chat_5_darbas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    @FXML
    private TextField nameTF, portTF;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public LoginController() {
    }

    public void login(ActionEvent event){
        try {
            //System.out.println(this.toString());

            String username = nameTF.getText();
            int port = Integer.parseInt(portTF.getText());

            System.out.println("login scene name = " + username + " PORT: " + port);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomScene.fxml"));
            root = loader.load();
            RoomController roomController = loader.getController();
            roomController.setUserName(username);
            roomController.setPORT(port);


            stage = (Stage)((Node)event.getSource() ).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
