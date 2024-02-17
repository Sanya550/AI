module com.example.ai {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.ai to javafx.fxml;
    exports com.example.ai;
}