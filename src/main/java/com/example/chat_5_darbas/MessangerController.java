package com.example.chat_5_darbas;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MessangerController implements Initializable {
    private String userName = "";
    private int PORT = 0000;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("It works");
        System.out.println("My name is " + userName + " PORT: " + PORT);
    }

}
