package io.example.minerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button easy;


    // Создаём новое окно и сцену после выбора уровня сложности
    @FXML
    void chooseLevel(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        FXMLLoader fxmlLoader = new FXMLLoader(WelcomeController.class.getResource("hello-view.fxml"));
        Stage stage = new Stage();
        if (button.getText().equals("Easy (8 x 8)")) {
            // Настройки уровня сложности делаем перед загрузкой FXML файла
            GameController.adjustDifficultyLevel("easy");
            Scene scene = new Scene(fxmlLoader.load(), 400, 426);
            stage.setScene(scene);
        } else if (button.getText().equals("Medium (16 x 16)")) {
            GameController.adjustDifficultyLevel("medium");
            Scene scene = new Scene(fxmlLoader.load(), 800, 826);
            stage.setScene(scene);
        } else {
            GameController.adjustDifficultyLevel("hard");
            Scene scene = new Scene(fxmlLoader.load(), 1500, 826);
            stage.setScene(scene);
        }

        stage.show();

        // Закрываем предыдущее окно
        button.getScene().getWindow().hide();

    }

}
