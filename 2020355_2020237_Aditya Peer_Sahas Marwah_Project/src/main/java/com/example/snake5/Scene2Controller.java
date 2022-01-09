package com.example.snake5;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Scene2Controller {

    {

        if (HelloController.currplayer2 == 100) {
            Media sound = new Media(new File("src/main/resources/com/example/snake5/CCC.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }
    }
}
