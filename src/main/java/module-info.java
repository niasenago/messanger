module com.example.chat_5_darbas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chat_5_darbas to javafx.fxml;
    exports com.example.chat_5_darbas;
    exports com.example.chat_5_darbas.ServerStuff;
    opens com.example.chat_5_darbas.ServerStuff to javafx.fxml;
    exports com.example.chat_5_darbas.ClientStuff;
    opens com.example.chat_5_darbas.ClientStuff to javafx.fxml;
}