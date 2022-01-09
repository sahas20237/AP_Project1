package com.example.snake5;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Scene1Controller {

    {
        if (HelloController.currplayer1 == 100) {
            Media sound = new Media(new File("src/main/resources/com/example/snake5/CCC.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }
    }

    private Stage stage;
    public void switchToHelloController(ActionEvent actionEvent) throws IOException {
        Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        stage =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Snake and Ladder!");
        stage.setScene(scene);
        stage.show();

    }

}
