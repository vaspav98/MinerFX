module io.example.minerfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.example.minerfx to javafx.fxml;
    exports io.example.minerfx;
}