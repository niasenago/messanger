package com.example.chat_5_darbas;

import com.example.chat_5_darbas.ClientStuff.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MessangerController implements Initializable {
    @FXML
    private Label nameLbl, roomLbl;
    @FXML
    private TextField messageTF;

    private String userName = "";
    private int PORT = 0000;
    private Client client;
    @FXML
    private static TableView<Message> table;
    @FXML
    private TableColumn<Message,String> msgCol = new TableColumn<Message, String>("Messages");
    private static ObservableList<Message> msgList = FXCollections.observableArrayList();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        nameLbl.setText("Hi, " + this.userName);
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
        roomLbl.setText("Room : " + PORT);
    }

    public static void addMsgToList(String msg){
        table.getItems().removeAll();
        Message message = new Message(msg);
        msgList.add(message);
        table.setItems(msgList);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        msgCol.setCellValueFactory(new PropertyValueFactory<Message, String>("msg"));
        //table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }
    public void connect(ActionEvent e)  {

        System.out.println("connect works");
        Socket socket = null;
        try {
            socket = new Socket("localhost", PORT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        client = new Client(socket, userName);
        client.sendMessage(userName);
        client.listenForMessage();//new thread


    }
    public void send(ActionEvent e){
        System.out.println("send btn works");
        String messageToSend = messageTF.getText();
        if(!messageToSend.isEmpty()){
            client.sendMessage(messageToSend);//messageToSend
            messageTF.clear();
        }
    }
    public static void addStringToTable(String msgFromServer){
        System.out.println("add string to table works");
        /**TODO*/
    }

}
