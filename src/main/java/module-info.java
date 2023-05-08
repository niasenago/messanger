module com.example.chat_5_darbas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chat_5_darbas to javafx.fxml;
    exports com.example.chat_5_darbas;
}