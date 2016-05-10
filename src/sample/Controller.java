package sample;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private FlowPane flowPane;
    private Rectangle rectangle;
    private Text text;
    private int count = 0;
    private boolean isRunning;


    public Rectangle createRectangle(){
        rectangle = new Rectangle(10,200,100, 30);
        rectangle.setArcHeight(15);
        rectangle.setArcWidth(15);
        rectangle.setFill(Color.ORANGE);
        rectangle.setSmooth(true);
        rectangle.setOnMousePressed(t -> {
            if (!isTaskRunning()){
                preload();
            }
        });
        rectangle.setOnMouseEntered(event -> {
            if (!isTaskRunning()) {
                rectangle.setOpacity(0.7);
                rectangle.setCursor(Cursor.HAND);
            }
        });
        rectangle.setOnMouseExited(event -> {
            if (!isTaskRunning()) {
                rectangle.setOpacity(1);
                rectangle.setCursor(Cursor.DEFAULT);
            }
        });
        return rectangle;
    }

    private void preload() {
        try {
            setTaskRunning(true);
            rectangle.setCursor(Cursor.WAIT);
            text.setVisible(false);
            runAnimation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setTaskRunning(boolean running) {
       isRunning = running;

    }

    private boolean isTaskRunning(){
        return isRunning;
    }

    public void runAnimation() throws InterruptedException {
        KeyValue kv = new KeyValue(rectangle.widthProperty(), 30);
        KeyValue kv2 = new KeyValue(rectangle.xProperty(), 10);
        KeyValue kv3 = new KeyValue(rectangle.arcHeightProperty(), 50);
        KeyValue kv4 = new KeyValue(rectangle.arcWidthProperty(), 50);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), kv, kv2, kv3, kv4);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);

        FillTransition fltr = new FillTransition(Duration.millis(500), rectangle, Color.ORANGE, Color.TRANSPARENT);
        StrokeTransition stroke = new StrokeTransition(Duration.millis(500), rectangle, Color.TRANSPARENT, Color.DARKGRAY);

        ParallelTransition ptr = new ParallelTransition();
        ptr.getChildren().addAll(fltr, timeline, stroke);

        RotateTransition rotation = new RotateTransition(Duration.millis(2000), rectangle);
        rotation.setFromAngle(0);
        rotation.setToAngle(360);

        StrokeTransition strokeSuccess = new StrokeTransition(Duration.millis(500), rectangle, Color.DARKGRAY,  Color.ORANGE);

        SequentialTransition sequential = new SequentialTransition();
        sequential.getChildren().addAll(ptr, rotation, strokeSuccess );

        sequential.setOnFinished(event -> {
            count++;
            if (count % 2 == 0){
                runSuccessfullyAnimation();
            } else {
                runFailedAnimation();
            }
        });
        sequential.play();
    }

    private void runFailedAnimation() {
        KeyValue kv = new KeyValue(rectangle.widthProperty(), 100);
        KeyValue kv2 = new KeyValue(rectangle.xProperty(), 10);
        KeyValue kv3 = new KeyValue(rectangle.arcHeightProperty(), 15);
        KeyValue kv4 = new KeyValue(rectangle.arcWidthProperty(), 15);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), kv, kv2, kv3, kv4);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);

        FillTransition fltr = new FillTransition(Duration.millis(500), rectangle,  Color.LIGHTGRAY, Color.RED.brighter());
        StrokeTransition strokeSuccess = new StrokeTransition(Duration.millis(500), rectangle,  Color.ORANGE, Color.RED);

        ParallelTransition ptr = new ParallelTransition();
        ptr.getChildren().addAll(fltr, timeline);

        SequentialTransition seqential = new SequentialTransition();
        seqential.setOnFinished(event -> {
            onComplete("Failed");
        });
        seqential.getChildren().addAll(strokeSuccess, ptr);
        seqential.play();
    }

    private void runSuccessfullyAnimation() {
        KeyValue kv = new KeyValue(rectangle.widthProperty(), 100);
        KeyValue kv2 = new KeyValue(rectangle.xProperty(), 10);
        KeyValue kv3 = new KeyValue(rectangle.arcHeightProperty(), 15);
        KeyValue kv4 = new KeyValue(rectangle.arcWidthProperty(), 15);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), kv, kv2, kv3, kv4);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);

        FillTransition fltr = new FillTransition(Duration.millis(500), rectangle, Color.LIGHTGRAY, Color.rgb(46, 204, 113));

        ParallelTransition ptr = new ParallelTransition();
        ptr.getChildren().addAll(fltr, timeline);

        StrokeTransition strokeSuccess = new StrokeTransition(Duration.millis(500), rectangle, Color.ORANGE, Color.rgb(46, 204, 113));

        SequentialTransition sequential = new SequentialTransition();
        sequential.getChildren().addAll(strokeSuccess, ptr);
        sequential.setOnFinished(event -> {
            onComplete("Success");
        });
        sequential.play();

    }

    private void onComplete(String textMessage) {
        text.setText(textMessage);
        text.setVisible(true);
        rectangle.setCursor(Cursor.HAND);
        setTaskRunning(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(20));
        stackPane.getChildren().addAll(createRectangle(), createText());
        flowPane.getChildren().add(stackPane);
        flowPane.setAlignment(Pos.CENTER);

    }


    private Text createText() {
        text = new Text();
        text.setText("Connect");
        text.setFont(Font.font("Open Sans"));
        text.setFill(Color.WHITE);
        text.setSmooth(true);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setOnMousePressed(t -> {
            if (!isTaskRunning()){
                preload();
            }
        });
        text.setOnMouseEntered(event -> {
            rectangle.setOpacity(0.7);
            text.setCursor(Cursor.HAND);
        });
        text.setOnMouseExited(event -> {
            rectangle.setOpacity(1);
            text.setCursor(Cursor.DEFAULT);
        });
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }
}
