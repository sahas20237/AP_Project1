package com.example.snake5;



import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class HelloController{
    private Stage stage;
public void switch1(ActionEvent actionEvent) throws IOException {

    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Scene1.fxml"));
    stage =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(fxmlLoader.load(), 320, 240);
    stage.setTitle("Snake and Ladder Game!");
    stage.setScene(scene);
    stage.show();

}

    static double currplayer1 =0;
    static double currplayer2 =0;
    @FXML
    private ImageView img;
    @FXML
    private Label welcomeText;


    @FXML
    private ImageView diceImage;
    token red;
    player me;
    LadderandSnakes v;
    DiceValue d;


    @FXML
    public Button rollButton;

    @FXML
    public ImageView player1;
    @FXML
    private ImageView player2;

    @FXML
    public Button Player1;

    @FXML
    public Button Player2;


    @FXML
    private ImageView Arrow;

    @FXML
    private Label locval;
    @FXML
    private Label status;
    @FXML
    void findloc(MouseEvent event) {
      me.ask2move(event,locval);
    }
    @FXML
    public int choice=1;

    @FXML
    static boolean start1 =false;
    @FXML
    static boolean start2 =false;

    public void initialize() {
      if(choice==1) {
          red = new token(player1, currplayer1, start1,status);
      }
     else if(choice==2) {
          red = new token(player2,currplayer2, start2,status);
      }
        me=new player();
        v =new LadderandSnakes();
        d =new DiceValue();
        Line line =new Line();
        line.setStartY(25);
        line.setStartY(25);
        PathTransition transition =new PathTransition();
        transition.setNode(Arrow);
        transition.setDuration(Duration.seconds(2));
        transition.setPath(line);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.play();
    }
    @FXML
    public void roll(ActionEvent actionEvent) throws IOException{

        rollButton.setDisable(true);
        Thread thread =new Thread() {
            public void run() {
                Media sound = new Media(new File("src/main/resources/com/example/snake5/R.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                System.out.println("Thread Running");
                int randomvalue =0;
                try {
                    System.out.println("Hello");
                    for (int i = 0; i < 15; i++) {
                         randomvalue =d.getrandomvalue();
                        File file = new File("src/main/resources/com/example/snake5/face"+randomvalue+".PNG");
                        diceImage.setImage(new Image(file.toURI().toString()));
                        Thread.sleep(50);

                    }
                    if(choice==1) {
                        System.out.println("The current position of player1 is :"+ " "+currplayer1);
                        red.makeAmove(me, locval, v, player1, d, randomvalue,choice,currplayer1, start1,status);
                        choice=2;
                    }
                    else if(choice==2) {
                        System.out.println("The current position of player2 is :"+ " "+currplayer2);
                        red.makeAmove(me, locval, v, player2, d, randomvalue,choice,currplayer2, start2,status);
                        choice=1;
                    }
                    rollButton.setDisable(false);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }

        };

        thread.start();
        if(currplayer1==100)
        {
            Media sound = new Media(new File("src/main/resources/com/example/snake5/CCC.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Scene2.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
       else if(currplayer2==100)
        {
            Media sound = new Media(new File("src/main/resources/com/example/snake5/CCC.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Scene3.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

        }
    }

}
class token{
    public double orgx;
    public double orgy;
    public double curry;
    public Button bt;
    public ImageView img;
    public boolean start =false;
    public double currx =0;
    Label status;



    <T> token(ImageView img, double currx,boolean start,Label status){
        Bounds bis1= img.localToScene(img.getBoundsInLocal());
        this.img =img;
        orgx=bis1.getMinX();
        orgy=bis1.getMinY();
        curry=orgy;
        this.start =start;
        this.currx =currx;
        this.status =status;
    }
    void makeAmove(player p,Label locval,LadderandSnakes v, ImageView img, DiceValue d, int position, int choice, double currx, boolean start, Label status) throws IOException, InterruptedException {

        double x =0;
        if(choice==1){
            System.out.println("The position of HelloController.currplayer1 is"+ " "+HelloController.currplayer1);
            x  =position+HelloController.currplayer1;

        }
        else if(choice==2)
        {  System.out.println("The position of HelloController.currplayer2 is"+ " "+HelloController.currplayer2);
            x  =position+HelloController.currplayer2;
        }


        if (x == 1|| start==true) {
            if(choice==1) {
                HelloController.start1 = true;
            }
            else if(choice==2){
                HelloController.start2 = true;
            }
            if(x==1) {
                TranslateTransition t1 = new TranslateTransition();
                t1.setNode(img);
                t1.setByX(v.normalx.get(1) - orgx);
                t1.setByY(v.normaly.get(1) - orgy);
                t1.setDuration(Duration.seconds(1));
                t1.setCycleCount(1);
                if(choice==1) {
                    HelloController.currplayer1 = 1;
                }
                else if(choice==2)
                {
                    HelloController.currplayer2 =1;
                }
                t1.play();
                Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            }
            System.out.println(position);
            if (x== 2) {

                TranslateTransition t = new TranslateTransition();
                t.setNode(img);
                t.setByX(v.ladderx.get(2) - orgx);
                t.setByY(v.laddery.get(2) - orgy);
                t.setDuration(Duration.seconds(1));
                t.setCycleCount(1);
                t.play();
                Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                if(choice==1) {
                    HelloController.currplayer1 = 38;
                }
                else if(choice==2)
                {
                    HelloController.currplayer2 =38;
                }
            }
            if (x == 1 ) {
                TranslateTransition t1 = new TranslateTransition();
                t1.setNode(img);
                t1.setByX(v.normalx.get(1) - orgx);
                t1.setByY(v.normaly.get(1) - orgy);
                t1.setDuration(Duration.seconds(1));
                t1.setCycleCount(1);
                t1.play();
                Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                if(choice==1) {
                    HelloController.currplayer1 = 1;
                }
                else if(choice==2)
                {
                    HelloController.currplayer2 =1;
                }
            }
            if (x == 3) {
                TranslateTransition t = new TranslateTransition();
                t.setNode(img);
                t.setByX(v.normalx.get(3) - orgx);
                t.setByY(v.normaly.get(3) - orgy);
                t.setDuration(Duration.seconds(1));
                t.setCycleCount(1);
                t.play();
                Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                if(choice==1) {
                    HelloController.currplayer1 = 3;
                }
                else if(choice==2)
                {
                    HelloController.currplayer2 =3;
                }
            }
            if (x== 4) {

                TranslateTransition t1 = new TranslateTransition();
                t1.setNode(img);
                t1.setByX(v.normalx.get(4) - orgx);
                t1.setByY(v.normaly.get(4) - orgy);
                t1.setDuration(Duration.seconds(1));
                t1.setCycleCount(1);
                t1.play();
                Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                if(choice==1) {
                    HelloController.currplayer1 = 4;
                }
                else if(choice==2)
                {
                    HelloController.currplayer2 =4;
                }
            }
            if (x== 5) {

                TranslateTransition t = new TranslateTransition();
                t.setNode(img);
                t.setByX(v.normalx.get(5) - orgx);
                t.setByY(v.normaly.get(5) - orgy);
                t.setDuration(Duration.seconds(2));
                t.setCycleCount(1);
                t.play();
                Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                if(choice==1) {
                    HelloController.currplayer1 = 5;
                }
                else if(choice==2)
                {
                    HelloController.currplayer2 =5;
                }
            }
            if (x==6) {
                TranslateTransition t = new TranslateTransition();
                t.setNode(img);
                t.setByX(v.normalx.get(6) - orgx);
                t.setByY(v.normaly.get(6) - orgy);
                t.setDuration(Duration.seconds(2));
                t.setCycleCount(1);
                t.play();
                Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                if(choice==1) {
                    HelloController.currplayer1 = 6;
                }
                else if(choice==2)
                {
                    HelloController.currplayer2 =6;
                }
            }
            else {
                if ((x== 7)) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setDuration(Duration.seconds(2));
                    t.setFromX(v.ladderx.get(7) - orgx);
                    t.setFromY(v.laddery.get(7) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 14;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2 =14;
                    }
                } else if (x== 8) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setDuration(Duration.seconds(4));
                    t.setFromX(v.ladderx.get(8) - orgx);
                    t.setFromY(v.laddery.get(8) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 31;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2 =31;
                    }
                } else if (x == 9) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setDuration(Duration.seconds(6));
                    t.setFromX(v.normalx.get(9) - orgx);
                    t.setFromY(v.normaly.get(9) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 9;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2 =9;
                    }
                } else if (x== 10){
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setDuration(Duration.seconds(6));
                    t.setFromX(v.normalx.get(10) - orgx);
                    t.setFromY(v.normaly.get(10) - orgy);
                    Thread.sleep(1);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    currx = 10;
                    if(choice==1) {
                        HelloController.currplayer1 = 10;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2 =10;
                    }
                    System.out.println(currx);
                } else if (x == 11) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setDuration(Duration.seconds(2));
                    t.setFromX(v.normalx.get(11) - orgx);
                    t.setFromY(v.normaly.get(11) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    currx = 11;
                    if(choice==1) {
                        HelloController.currplayer1 = 11;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=11;
                    }
                    System.out.println(currx);
                } else if (x == 12) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(12) - orgx);
                    t.setFromY(v.normaly.get(12) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    currx = 12;
                    if(choice==1) {
                        HelloController.currplayer1 = 12;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=12;
                    }
                    System.out.println(currx);
                } else if (x == 13) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(13) - orgx);
                    t.setFromY(v.normaly.get(13) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 13;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=13;
                    }
                    currx = 13;
                    System.out.println(currx);
                } else if (x == 14) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(14) - orgx);
                    t.setFromY(v.normaly.get(14) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 14;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=14;
                    }
                    System.out.println(currx);
                } else if (x == 15) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.ladderx.get(15) - orgx);
                    t.setFromY(v.laddery.get(15) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    currx = 26;
                    if(choice==1) {
                        HelloController.currplayer1 = 26;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=26;
                    }
                    System.out.println(currx);
                }
                else if (x == 16) {

                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.snakesx.get(16) - orgx);
                    t.setFromY(v.snakesy.get(16) - orgy);
                    t.setFromX(v.normalx.get(6) - orgx);
                    t.setFromY(v.normaly.get(6) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 6;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=6;
                    }
                    System.out.println(currx);
                }
                else if (x== 17) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(17) - orgx);
                    t.setFromY(v.normaly.get(17) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 17;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=17;
                    }
                }
                else if (x == 18) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(18) - orgx);
                    t.setFromY(v.normaly.get(18) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 18;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=18;
                    }
                }
                else if (x == 19) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(19) - orgx);
                    t.setFromY(v.normaly.get(19) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 19;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=19;
                    }
                }
                else if (x == 20) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(20) - orgx);
                    t.setFromY(v.normaly.get(20) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 20;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=20;
                    }

                }
                else if (x == 21) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.ladderx.get(21) - orgx);
                    t.setFromY(v.laddery.get(21) - orgy);
                    t.setCycleCount(1);
                    t.setDuration(Duration.seconds(2));
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =42;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=42;
                    }

                }
                else if (x == 22) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(22) - orgx);
                    t.setFromY(v.normaly.get(22) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =22;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=22;
                    }
                }
                else if (x == 23) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(23) - orgx);
                    t.setFromY(v.normaly.get(23) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =23;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=23;
                    }
                }
                else if (x == 24) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(24) - orgx);
                    t.setFromY(v.normaly.get(24) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =24;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=24;
                    }
                }
                else if (x == 25) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(25) - orgx);
                    t.setFromY(v.normaly.get(25) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =25;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=25;
                    }
                }
                else if (x == 26) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(26) - orgx);
                    t.setFromY(v.normaly.get(26) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =26;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=26;
                    }
                }
                else if (x == 27) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(27) - orgx);
                    t.setFromY(v.normaly.get(27) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =27;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=27;
                    }
                }
                else if (x == 28) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.ladderx.get(28) - orgx);
                    t.setFromY(v.laddery.get(28) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =84;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=84;
                    }
                } else if (x == 29) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(29) - orgx);
                    t.setFromY(v.normaly.get(29) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =29;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=29;
                    }
                }
                else if (x == 30) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(30) - orgx);
                    t.setFromY(v.normaly.get(30) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =30;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=30;
                    }
                }
                else if (x == 31) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(31) - orgx);
                    t.setFromY(v.normaly.get(31) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =31;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=31;
                    }
                }
                else if (x == 32) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(32) - orgx);
                    t.setFromY(v.normaly.get(32) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =32;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=32;
                    }
                }
                else if (x == 33) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(33) - orgx);
                    t.setFromY(v.normaly.get(33) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =33;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=33;
                    }
                }
                else if (x == 34) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(34) - orgx);
                    t.setFromY(v.normaly.get(34) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =34;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=34;
                    }
                }
                else if (x == 35) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(35) - orgx);
                    t.setFromY(v.normaly.get(35) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =35;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=35;
                    }
                }
                else if (x == 36) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.ladderx.get(36) - orgx);
                    t.setFromY(v.laddery.get(36) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =44;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=44;
                    }
                }
                else if (x == 37) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(37) - orgx);
                    t.setFromY(v.normaly.get(37) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =37;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=37;
                    }
                }
                else if (x == 38) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(38) - orgx);
                    t.setFromY(v.normaly.get(38) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =38;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=38;
                    }
                }
                else if (x == 39) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(39) - orgx);
                    t.setFromY(v.normaly.get(39) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =39;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=39;
                    }
                }
                else if (x == 40) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(40) - orgx);
                    t.setFromY(v.normaly.get(40) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =40;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=40;
                    }
                }
                else if (x == 41) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(41) - orgx);
                    t.setFromY(v.normaly.get(41) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =41;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=41;
                    }
                }
                else if (x == 42) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(42) - orgx);
                    t.setFromY(v.normaly.get(42) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =42;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=42;
                    }
                }
                else if (x == 43) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(43) - orgx);
                    t.setFromY(v.normaly.get(43) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =43;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=43;
                    }
                }
                else if (x == 44) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(44) - orgx);
                    t.setFromY(v.normaly.get(44) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =44;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=44;
                    }
                }
                else if (x == 45) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(45) - orgx);
                    t.setFromY(v.normaly.get(45) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =45;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=45;
                    }
                }
                else if (x == 46) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.snakesx.get(46) - orgx);
                    t.setFromY(v.snakesy.get(46) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =25;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=25;
                    }
                }
                else if (x == 47) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(47) - orgx);
                    t.setFromY(v.normaly.get(47) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =47;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=47;
                    }
                }
                else if (x == 48) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(48) - orgx);
                    t.setFromY(v.normaly.get(48) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =48;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=48;
                    }
                }
                else if (x == 49) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.snakesx.get(49) - orgx);
                    t.setFromY(v.snakesy.get(49) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =11;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=11;
                    }
                }
                else if (x == 50) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(50) - orgx);
                    t.setFromY(v.normaly.get(50) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =50;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=50;
                    }
                }
                else if (x == 51) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.ladderx.get(51) - orgx);
                    t.setFromY(v.laddery.get(51) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =67;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=67;
                    }
                }
                else if (x == 52) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(52) - orgx);
                    t.setFromY(v.normaly.get(52) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =52;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=52;
                    }
                }
                else if (x == 53) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(53) - orgx);
                    t.setFromY(v.normaly.get(53) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =53;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=53;
                    }
                }
                else if (x == 54) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(54) - orgx);
                    t.setFromY(v.normaly.get(54) - orgy);
                    t.setDuration(Duration.seconds(2));
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =54;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=54;
                    }
                }
                else if (x == 55) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(55) - orgx);
                    t.setFromY(v.normaly.get(55) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =55;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=55;
                    }
                }
                else if (x == 56) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(56) - orgx);
                    t.setFromY(v.normaly.get(56) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =56;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=56;
                    }
                }
                else if (x == 57) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(57) - orgx);
                    t.setFromY(v.normaly.get(57) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =57;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=57;
                    }
                }
                else if (x == 58) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(58) - orgx);
                    t.setFromY(v.normaly.get(58) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =58;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=58;
                    }
                }
                else if (x == 59) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(59) - orgx);
                    t.setFromY(v.normaly.get(59) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =59;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=59;
                    }
                }
                else if (x == 60) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(60) - orgx);
                    t.setFromY(v.normaly.get(60) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =60;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=60;
                    }
                }
                else if (x == 61) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(61) - orgx);
                    t.setFromY(v.normaly.get(61) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =61;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=61;
                    }
                }
                else if (x == 62) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.snakesx.get(62) - orgx);
                    t.setFromY(v.snakesy.get(62) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =19;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=19;
                    }
                }
                else if (x == 63) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(63) - orgx);
                    t.setFromY(v.normaly.get(63) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =63;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=63;
                    }
                }
                else if (x == 64) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.snakesx.get(64) - orgx);
                    t.setFromY(v.snakesy.get(64) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =60;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=60;
                    }
                }
                else if (x == 65) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(65) - orgx);
                    t.setFromY(v.normaly.get(65) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =65;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=65;
                    }
                }
                else if (x == 66) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(66) - orgx);
                    t.setFromY(v.normaly.get(66) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =66;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=66;
                    }
                }
                else if (x == 67) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(67) - orgx);
                    t.setFromY(v.normaly.get(67) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 67;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=67;
                    }
                }
                else if (x == 68) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(68) - orgx);
                    t.setFromY(v.normaly.get(68) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 68;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=68;
                    }
                }
                else if (x == 69) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(69) - orgx);
                    t.setFromY(v.normaly.get(69) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 = 69;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=69;
                    }
                }
                else if (x == 70) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(70) - orgx);
                    t.setFromY(v.normaly.get(70) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =70;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=70;
                    }
                }
                else if (x == 71) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.ladderx.get(71) - orgx);
                    t.setFromY(v.laddery.get(71) - orgy);
                    t.setCycleCount(1);
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    t.play();
                    if(choice==1) {
                        HelloController.currplayer1 =71;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=71;
                    }
                }
                else if (x == 72) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(72) - orgx);
                    t.setFromY(v.normaly.get(72) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =72;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=72;
                    }
                }
                else if (x == 72) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(72) - orgx);
                    t.setFromY(v.normaly.get(72) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =72;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=72;
                    }
                }
                else if (x == 73) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(73) - orgx);
                    t.setFromY(v.normaly.get(73) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =73;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=73;
                    }
                }
                else if (x == 74) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.snakesx.get(74) - orgx);
                    t.setFromY(v.snakesy.get(74) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =53;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=53;
                    }
                }
                else if (x == 75) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(75) - orgx);
                    t.setFromY(v.normaly.get(75) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =75;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=75;
                    }
                }
                else if (x == 76) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(76) - orgx);
                    t.setFromY(v.normaly.get(76) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =76;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=76;
                    }
                }
                else if (x == 77) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(77) - orgx);
                    t.setFromY(v.normaly.get(77) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =77;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=77;
                    }
                }
                else if (x == 78) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.ladderx.get(78) - orgx);
                    t.setFromY(v.laddery.get(78) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =98;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=98;
                    }
                }
                else if (x == 79) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(79) - orgx);
                    t.setFromY(v.normaly.get(79) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =79;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=79;
                    }
                }
                else if (x == 80) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(80) - orgx);
                    t.setFromY(v.normaly.get(80) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =80;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=80;
                    }
                }
                else if (x == 81) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(81) - orgx);
                    t.setFromY(v.normaly.get(81) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =81;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=81;
                    }
                }
                else if (x == 82) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(82) - orgx);
                    t.setFromY(v.normaly.get(82) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =82;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=82;
                    }
                }
                else if (x == 83) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(83) - orgx);
                    t.setFromY(v.normaly.get(83) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =83;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=83;
                    }
                }
                else if (x == 84) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(84) - orgx);
                    t.setFromY(v.normaly.get(84) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =84;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=84;
                    }
                }
                else if (x == 85) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(85) - orgx);
                    t.setFromY(v.normaly.get(85) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =85;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=85;
                    }
                }
                else if (x == 86) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(86) - orgx);
                    t.setFromY(v.normaly.get(86) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =86;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=86;
                    }
                }
                else if (x == 87) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.ladderx.get(87) - orgx);
                    t.setFromY(v.laddery.get(87) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =87;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=87;
                    }
                }
                else if (x == 88) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(88) - orgx);
                    t.setFromY(v.normaly.get(88) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =88;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=88;
                    }
                }
                else if (x == 89) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(89) - orgx);
                    t.setFromY(v.normaly.get(89) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =89;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=89;
                    }
                }
                else if (x == 90) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(90) - orgx);
                    t.setFromY(v.normaly.get(90) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =90;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=90;
                    }
                }
                else if (x == 91) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(91) - orgx);
                    t.setFromY(v.normaly.get(91) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =91;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=91;
                    }
                }
                else if (x == 92) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.snakesx.get(92) - orgx);
                    t.setFromY(v.snakesy.get(92) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =88;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=88;
                    }
                }
                else if (x == 93) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(93) - orgx);
                    t.setFromY(v.normaly.get(93) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =93;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=93;
                    }
                }
                else if (x == 94) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(94) - orgx);
                    t.setFromY(v.normaly.get(94) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =94;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=94;
                    }
                }
                else if (x == 95) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.snakesx.get(95) - orgx);
                    t.setFromY(v.snakesy.get(95) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =75;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=75;
                    }
                }
                else if (x == 96) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(96) - orgx);
                    t.setFromY(v.normaly.get(96) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =96;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=96;
                    }
                }
                else if (x == 97) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(97) - orgx);
                    t.setFromY(v.normaly.get(97) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =97;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=97;
                    }
                }
                else if (x == 98) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(98) - orgx);
                    t.setFromY(v.normaly.get(98) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =98;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=98;
                    }
                }
                else if (x == 99) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.snakesx.get(99) - orgx);
                    t.setFromY(v.snakesy.get(99) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1=80;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=80;
                    }
                }
                else if (x == 100) {
                    TranslateTransition t = new TranslateTransition();
                    t.setNode(img);
                    t.setFromX(v.normalx.get(100) - orgx);
                    t.setFromY(v.normaly.get(100) - orgy);
                    t.setCycleCount(1);
                    t.play();
                    Media sound = new Media(new File("src/main/resources/com/example/snake5/P.mp3").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if(choice==1) {
                        HelloController.currplayer1 =100;
                    }
                    else if(choice==2)
                    {
                        HelloController.currplayer2=100;
                    }
                    if(choice==1)
                    {
                        HelloController.start1 =false;
                        HelloController.start2 =false;
                    }
                    if(choice==2)
                    {
                        HelloController.start1 =false;
                        HelloController.start2 =false;
                    }
                }

            }

        }

    }
}
class DiceValue{
    public int rand;

    public int getrandomvalue(){
        this.rand =(int)(Math.random()*6+1);
        return this.rand;
    }
}
class player{
    public double desx;
    public double desy;
    public void ask2move(MouseEvent event, Label locval){
        locval.setText("Move to X:"+String.valueOf(event.getSceneX())+"Y:"+String.valueOf(event.getSceneY()));
        desx=event.getSceneX();
        desy=event.getSceneY();

    }
}
class LadderandSnakes{
    public Map<Integer,Double>ladderx = new HashMap<Integer,Double>();
    public Map<Integer,Double>laddery = new HashMap<Integer,Double>();
    public Map<Integer,Double>normalx = new HashMap<Integer,Double>();
    public Map<Integer,Double>normaly = new HashMap<Integer,Double>();
    public Map<Integer,Double>snakesx = new HashMap<Integer,Double>();
    public Map<Integer,Double>snakesy = new HashMap<Integer,Double>();
    LadderandSnakes()
    {
        this.normalx.put(1,287.0);
        this.normaly.put(1,654.0);
        this.ladderx.put(2,436.0);
        this.laddery.put(2,474.0);
        this.normalx.put(3,432.0);
        this.normaly.put(3,648.0);
        this.normalx.put(4,496.0);
        this.normaly.put(4,686.0);
        this.normalx.put(5,562.0);
        this.normaly.put(5,664.0);
        this.normalx.put(6,628.0);
        this.normaly.put(6,663.0);
        this.ladderx.put(7,696.0);
        this.laddery.put(7,579.0);
        this.ladderx.put(8,894.0);
        this.laddery.put(8,445.0);
        this.normalx.put(9,818.0);
        this.normaly.put(9,644.0);
        this.normalx.put(10,886.0);
        this.normaly.put(10,652.0);
        this.normalx.put(11,886.0);
        this.normaly.put(11,586.0);
        this.normalx.put(12,817.0);
        this.normaly.put(12,582.0);
        this.normalx.put(13,750.0);
        this.normaly.put(13,580.0);
        this.normalx.put(14,691.0);
        this.normaly.put(14,581.0);
        this.ladderx.put(15,635.0);
        this.laddery.put(15,528.0);
        this.snakesx.put(16,589.0);
        this.snakesy.put(16,646.0);
        this.normalx.put(17,500.0);
        this.normaly.put(17,597.0);
        this.normalx.put(18,432.0);
        this.normaly.put(18,596.0);
        this.normalx.put(19,368.0);
        this.normaly.put(19,602.0);
        this.normalx.put(20,300.0);
        this.normaly.put(20,590.0);
        this.ladderx.put(21,359.0);
        this.laddery.put(21,393.0);
        this.normalx.put(22,368.0);
        this.normaly.put(22,538.0);
        this.normalx.put(23,436.0);
        this.normaly.put(23,535.0);
        this.normalx.put(24,499.0);
        this.normaly.put(24,530.0);
        this.normalx.put(25,499.0);
        this.normaly.put(25,530.0);
        this.normalx.put(26,565.0);
        this.normaly.put(26,527.0);
        this.normalx.put(27,696.0);
        this.normaly.put(27,528.0);
        this.ladderx.put(28,502.0);
        this.laddery.put(28,135.0);
        this.normalx.put(29,822.0);
        this.normaly.put(29,527.0);
        this.normalx.put(30,894.0);
        this.normaly.put(30,528.0);
        this.normalx.put(31,896.0);
        this.normaly.put(31,449.0);
        this.normalx.put(32,826.0);
        this.normaly.put(32,467.0);
        this.normalx.put(33,765.0);
        this.normaly.put(33,458.0);
        this.normalx.put(34,694.0);
        this.normaly.put(34,465.0);
        this.normalx.put(35,632.0);
        this.normaly.put(35,457.0);
        this.ladderx.put(36,498.0);
        this.laddery.put(36,395.0);
        this.normalx.put(37,498.0);
        this.normaly.put(37,464.0);
        this.normalx.put(38,427.0);
        this.normaly.put(38,458.0);
        this.normalx.put(39,368.0);
        this.normaly.put(39,465.0);
        this.normalx.put(40,294.0);
        this.normaly.put(40,466.0);
        this.normalx.put(41,304.0);
        this.normaly.put(41,403.0);
        this.normalx.put(42,373.0);
        this.normaly.put(42,397.0);
        this.normalx.put(43,436.0);
        this.normaly.put(43,397.0);
        this.normalx.put(44,497.0);
        this.normaly.put(44,397.0);
        this.normalx.put(45,566.0);
        this.normaly.put(45,399.0);
        this.snakesx.put(46,572.0);
        this.snakesy.put(46,537.0);
        this.normalx.put(47,701.0);
        this.normaly.put(47,395.0);
        this.normalx.put(48,765.0);
        this.normaly.put(48,400.0);
        this.snakesx.put(49,897.0);
        this.snakesy.put(49,590.0);
        this.normalx.put(50,896.0);
        this.normaly.put(50,398.0);
        this.ladderx.put(51,692.0);
        this.laddery.put(51,259.0);
        this.normalx.put(52,824.0);
        this.normaly.put(52,338.0);
        this.normalx.put(53,763.0);
        this.normaly.put(53,334.0);
        this.normalx.put(54,700.0);
        this.normaly.put(54,334.0);
        this.normalx.put(55,639.0);
        this.normaly.put(55,330.0);
        this.normalx.put(56,568.0);
        this.normaly.put(56,330.0);
        this.normalx.put(57,500.0);
        this.normaly.put(57,330.0);
        this.normalx.put(58,436.0);
        this.normaly.put(58,332.0);
        this.normalx.put(59,371.0);
        this.normaly.put(59,337.0);
        this.normalx.put(60,301.0);
        this.normaly.put(60,334.0);
        this.normalx.put(61,301.0);
        this.normaly.put(61,267.0);
        this.snakesx.put(62,359.0);
        this.snakesy.put(62,593.0);
        this.normalx.put(63,437.0);
        this.normaly.put(63,273.0);
        this.snakesx.put(64,304.0);
        this.snakesy.put(64,333.0);
        this.normalx.put(65,569.0);
        this.normaly.put(65,271.0);
        this.normalx.put(66,636.0);
        this.normaly.put(66,266.0);
        this.normalx.put(67,694.0);
        this.normaly.put(67,259.0);
        this.normalx.put(68,764.0);
        this.normaly.put(68,263.0);
        this.normalx.put(69,834.0);
        this.normaly.put(69,270.0);
        this.normalx.put(70,899.0);
        this.normaly.put(70,266.0);
        this.ladderx.put(71,894.0);
        this.laddery.put(71,63.0);
        this.normalx.put(72,836.0);
        this.normaly.put(72,204.0);
        this.normalx.put(73,765.0);
        this.normaly.put(73,205.0);
        this.snakesx.put(74,765.0);
        this.snakesy.put(74,335.0);
        this.normalx.put(75,626.0);
        this.normaly.put(75,213.0);
        this.normalx.put(76,570.0);
        this.normaly.put(76,203.0);
        this.normalx.put(77,503.0);
        this.normaly.put(77,206.0);
        this.ladderx.put(78,451.0);
        this.laddery.put(78,68.0);
        this.normalx.put(79,373.0);
        this.normaly.put(79,203.0);
        this.normalx.put(80,295.0);
        this.normaly.put(80,198.0);
        this.normalx.put(81,307.0);
        this.normaly.put(81,137.0);
        this.normalx.put(82,368.0);
        this.normaly.put(82,135.0);
        this.normalx.put(83,440.0);
        this.normaly.put(83,506.0);
        this.normalx.put(84,440.0);
        this.normaly.put(84,133.0);
        this.normalx.put(85,566.0);
        this.normaly.put(85,134.0);
        this.normalx.put(86,638.0);
        this.normaly.put(86,138.0);
        this.ladderx.put(87,704.0);
        this.laddery.put(87,72.0);
        this.normalx.put(88,766.0);
        this.normaly.put(88,134.0);
        this.normalx.put(89,830.0);
        this.normaly.put(89,134.0);
        this.normalx.put(90,901.0);
        this.normaly.put(90,132.0);
        this.normalx.put(91,898.0);
        this.normaly.put(91,65.0);
        this.snakesx.put(92,768.0);
        this.snakesy.put(92,135.0);
        this.normalx.put(93,768.0);
        this.normaly.put(93,66.0);
        this.normalx.put(94,704.0);
        this.normaly.put(94,73.0);
        this.snakesx.put(95,627.0);
        this.snakesy.put(95,203.0);
        this.normalx.put(96,570.0);
        this.normaly.put(96,70.0);
        this.normalx.put(97,502.0);
        this.normaly.put(97,72.0);
        this.normalx.put(98,434.0);
        this.normaly.put(98,70.0);
        this.snakesx.put(99,300.0);
        this.snakesy.put(99,199.0);
        this.normalx.put(100,305.0);
        this.normaly.put(100,75.0);

    }

}