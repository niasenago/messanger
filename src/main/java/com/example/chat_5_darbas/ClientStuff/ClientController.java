package com.example.chat_5_darbas.ClientStuff;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vbox_messages;
    @FXML
    private ScrollPane sp_main;
    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Scanner scanner = new Scanner(System.in);
        //System.out.println("Enter your username for the group chat: ");
        //String username = scanner.nextLine();
        String username = "Lili";
        Socket socket = null;
        try {
            socket = new Socket("localhost", 4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Client client = new Client(socket, username);

        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }

        });
        client.listenForMessage(); //vbox_messages
        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String messageToSend = tf_message.getText();
                if(!messageToSend.isEmpty()){
                    HBox hbox = new HBox();
                    hbox.setAlignment(Pos.CENTER_RIGHT);
                    hbox.setPadding(new Insets(5,5,5,10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-color:rgb(239,242,255); " +
                            "-fx-background-color: rgb(15,125,242); " +
                            "-fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5,10,5,10));
                    text.setFill(Color.color(0.934,0.945,0.996));

                    hbox.getChildren().add(textFlow);
                    vbox_messages.getChildren().add(hbox);

                    client.sendMessage(messageToSend);//messageToSend
                    tf_message.clear();
                }
            }
        });

    }
    public static void addLabel(String msgFromServer, VBox vbox){
        System.out.println("add label works");
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(msgFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-color:rgb(0,0,0) " +
                "-fx-background-color: rgb(233,233,233) " +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.color(0,0,0));

        hbox.getChildren().add(textFlow);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hbox);
            }
        });
    }

}
