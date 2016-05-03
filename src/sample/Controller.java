package sample;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private FlowPane flowPane;
    private Rectangle rectangle;
    private Text text;
    private int count = 0;


    public Rectangle createRetangle(){
        rectangle = new Rectangle(10,200,100, 30);
        rectangle.setArcHeight(15);
        rectangle.setArcWidth(15);
        rectangle.setFill(Color.ORANGE);
        rectangle.setTranslateX(100);
        rectangle.setTranslateY(100);
        rectangle.setSmooth(true);
        rectangle.setOnMousePressed(t -> preload());
        return rectangle;
    }

    private void preload() {
        try {
            text.setVisible(false);
            runAnimation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runAnimation() throws InterruptedException {
        KeyValue kv = new KeyValue(rectangle.widthProperty(), 30);
        KeyValue kv2 = new KeyValue(rectangle.xProperty(), 10);
        KeyValue kv3 = new KeyValue(rectangle.arcHeightProperty(), 50);
        KeyValue kv4 = new KeyValue(rectangle.arcWidthProperty(), 50);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), kv, kv2, kv3, kv4);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), rectangle);
        translateTransition.setFromX(100);
        translateTransition.setToX(125);

        FillTransition fltr = new FillTransition(Duration.millis(500), rectangle, Color.ORANGE, Color.TRANSPARENT);
        StrokeTransition stroke = new StrokeTransition(Duration.millis(500), rectangle, Color.TRANSPARENT, Color.DARKGRAY);


        ParallelTransition ptr = new ParallelTransition();
        ptr.getChildren().addAll(fltr, translateTransition, timeline, stroke);

        RotateTransition rotation = new RotateTransition(Duration.millis(2000), rectangle);
        rotation.setFromAngle(0);
        rotation.setToAngle(360);

        StrokeTransition strokeSucess = new StrokeTransition(Duration.millis(500), rectangle, Color.DARKGRAY,  Color.ORANGE);

        SequentialTransition seqential = new SequentialTransition();
        seqential.getChildren().addAll(ptr, rotation, strokeSucess );

        seqential.setOnFinished(event -> {
            count++;
            if (count % 2 == 0){
                runSucessfulAnimation();
            } else {
                runFailedAnimation();
            }
        });
        seqential.play();
    }

    private void runFailedAnimation() {
        KeyValue kv = new KeyValue(rectangle.widthProperty(), 100);
        KeyValue kv2 = new KeyValue(rectangle.xProperty(), 10);
        KeyValue kv3 = new KeyValue(rectangle.arcHeightProperty(), 15);
        KeyValue kv4 = new KeyValue(rectangle.arcWidthProperty(), 15);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), kv, kv2, kv3, kv4);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), rectangle);
        translateTransition.setFromX(125);
        translateTransition.setToX(100);

        FillTransition fltr = new FillTransition(Duration.millis(500), rectangle,  Color.LIGHTGRAY, Color.RED.brighter());
        StrokeTransition strokeSucess = new StrokeTransition(Duration.millis(500), rectangle,  Color.ORANGE, Color.RED);

        ParallelTransition ptr = new ParallelTransition();
        ptr.getChildren().addAll(fltr, translateTransition, timeline);

        SequentialTransition seqential = new SequentialTransition();
        seqential.setOnFinished(event -> {
            text.setText("Failed");
            text.setVisible(true);
        });
        seqential.getChildren().addAll(strokeSucess, ptr);
        seqential.play();
    }

    private void runSucessfulAnimation() {
        KeyValue kv = new KeyValue(rectangle.widthProperty(), 100);
        KeyValue kv2 = new KeyValue(rectangle.xProperty(), 10);
        KeyValue kv3 = new KeyValue(rectangle.arcHeightProperty(), 15);
        KeyValue kv4 = new KeyValue(rectangle.arcWidthProperty(), 15);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), kv, kv2, kv3, kv4);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);

        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(500), rectangle);
        translateTransition.setFromX(125);
        translateTransition.setToX(100);

        FillTransition fltr = new FillTransition(Duration.millis(500), rectangle, Color.LIGHTGRAY, Color.rgb(46, 204, 113));

        ParallelTransition ptr = new ParallelTransition();
        ptr.getChildren().addAll(fltr, translateTransition, timeline);

        StrokeTransition strokeSucess = new StrokeTransition(Duration.millis(500), rectangle, Color.ORANGE, Color.rgb(46, 204, 113));

        SequentialTransition seqential = new SequentialTransition();
        seqential.getChildren().addAll(strokeSucess, ptr);
        seqential.setOnFinished(event -> {
            text.setText("Success!");
            text.setVisible(true);
        });
        seqential.play();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        flowPane.getChildren().add(createCanvas());
        flowPane.getChildren().addAll(createRetangle(), createText());
    }

    private Canvas createCanvas() {
        return new Canvas();
    }

    private Text createText() {
        text = new Text();
        text.setText("Connect");
        text.setTranslateX(27);
        text.setTranslateY(100);
        text.setFill(Color.WHITE);
        text.setOnMousePressed(t -> preload());
        return text;
    }
}
