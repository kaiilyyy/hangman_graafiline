module com.example.hangman_graafiline {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hangman_graafiline to javafx.fxml;
    exports com.example.hangman_graafiline;
}