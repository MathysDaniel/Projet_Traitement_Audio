package ui;

import audio.AudioSignal;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.sound.sampled.*;

import static audio.AudioIO.obtainAudioInput;


public class Main extends Application {

    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            root.setTop(createToolbar());
            root.setBottom(createStatusbar());
            root.setCenter(createMainContent());
            Scene scene = new Scene(root, 1360, 690);
            primaryStage.setScene(scene);
            primaryStage.setTitle("The JavaFX audio processor");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Node createToolbar() {
        Button button = new Button("Hello World !");
        ToolBar tb = new ToolBar(button, new Label("Label"), new Separator());
        button.setOnAction(event -> System.out.println("Hello World !"));
        ComboBox<String> cb = new ComboBox<>();
        cb.getItems().addAll("Item 1", "Item 2", "Item 3");
        tb.getItems().add(cb);
        return tb;
    }

    private Node createStatusbar() {
        HBox statusbar = new HBox();
        statusbar.getChildren().addAll(new Label("Name:"), new TextField(" "));
        return statusbar;
    }
    /* private Node createDblevel(){
        ToolBar Dblevel = new ToolBar(button, new Label("Label"), new Separator());

    }
*\

     */
    private Node createMainContent() {
        Group g = new Group();
        // ici en utilisant g.getChildren().add(...) vous pouvez ajouter tout  ́el ́ement graphique souhait ́e de type Node
        return g;
    }

    public interface SourceDataLine extends DataLine {

    }

    public static void main(String[] args) throws LineUnavailableException {
        launch(args);
        AudioFormat AudioFormat;
        AudioFormat = null;
        new AudioFormat(8000, 8, 1, true, true);
        DataLine line = AudioSystem.getTargetDataLine(AudioFormat);
        line.open();
        line.start();
    }

}
