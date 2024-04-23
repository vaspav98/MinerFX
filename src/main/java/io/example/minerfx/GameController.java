package io.example.minerfx;

import io.example.minerfx.sweeper.Box;
import io.example.minerfx.sweeper.Coord;
import io.example.minerfx.sweeper.Game;
import io.example.minerfx.sweeper.Ranges;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {

    @FXML
    private AnchorPane anchorPane;

    private Game game;
    private static String level;
    private static int cols;
    private static int rows;
    private static int bombs;
    private final static int IMAGE_SIZE = 50;

    @FXML
    Label label;

    @FXML
    void initialize() {
        game = new Game(cols, rows, bombs);
        game.start();
        setImages();

        if (level.equals("easy")) {
            anchorPane.setPrefSize(400, 426);
        } else if (level.equals("medium")) {
            anchorPane.setPrefSize(800, 826);
        } else {
            anchorPane.setPrefSize(1500, 826);
        }

        for (Coord coord : Ranges.getAllCoords()) {
            ImageView imageView = new ImageView(game.getBox(coord).image);
            imageView.setLayoutX(coord.getX() * IMAGE_SIZE);
            imageView.setLayoutY(coord.getY() * IMAGE_SIZE);

            imageView.setOnMouseClicked(event -> {
                handleMouseClick(event, coord);
            });

            anchorPane.getChildren().add(imageView);
        }
    }

    public static void adjustDifficultyLevel(String lvl) {
        switch (lvl) {
            case "easy":
                cols = 8;
                rows = 8;
                bombs = 10;
                level = "easy";
                break;
            case "medium":
                cols = 16;
                rows = 16;
                bombs = 40;
                level = "medium";
                break;
            case "hard":
                cols = 30;
                rows = 16;
                bombs = 99;
                level = "hard";
        }
    }

    @FXML
    void clickBtnChangeDifficulty(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 426);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        // Закрываем предыдущее окно
        ((Node) event.getSource()).getScene().getWindow().hide();


    }

    private static Scene getScene(FXMLLoader fxmlLoader) throws IOException {
        return new Scene(fxmlLoader.load(), 200, 300);
    }


    private void handleMouseClick(MouseEvent event, Coord coord) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            game.pressLeftButton(coord);
        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
            game.pressRightButton(coord);
        } else if (event.getButton().equals(MouseButton.MIDDLE)) {
            game.start();
        }

        for (Node node : anchorPane.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;

                int x = (int) imageView.getLayoutX() / IMAGE_SIZE;
                int y = (int) imageView.getLayoutY() / IMAGE_SIZE;
                Coord crd = new Coord(x, y);

                imageView.setImage(game.getBox(crd).image);

                label.setText(getMessage());
            }
        }
    }

    private String getMessage() {
        return switch (game.getState()) {
            case PLAYED -> "Think twice!";
            case BOMBED -> "You lose! BA BAX!";
            case WINNER -> "CONGRATULATIONS!";
        };
    }

    private void setImages() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name().toLowerCase());
        }
    }

    private Image getImage(String name) {
        String filename = "/images/" + name + ".png";
        return new Image(filename);
    }

}
