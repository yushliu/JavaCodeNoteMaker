package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Vector;
import javafx.embed.swing.*;

public class Main extends Application {
    static int paneWidth = 0;
    static int paneHeight = 0;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader startPagefxmlLoader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("../resources/fxmlFile/startPage.fxml")));
        Scene startPageScene = new Scene(startPagefxmlLoader.load(), 1700, 800);
        stage.setScene(startPageScene);
        stage.show();
    }

    public static void main(String[] args) {launch();}

    static final public int lineNumber = 5;
    static public Vector<String> lineColorName = new Vector<>();
    {
        lineColorName.add("red");
        lineColorName.add("blue");
        lineColorName.add("yellow");
        lineColorName.add("orange");
        lineColorName.add("green");
    }

    static Vector<String> backgroundName = new Vector<>();
    {
        backgroundName.add("tree");
        backgroundName.add("building");
        backgroundName.add("grass");
        backgroundName.add("sand");
        backgroundName.add("mountain");
        backgroundName.add("automatic");
    }
}
