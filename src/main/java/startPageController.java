package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.io.*;
import java.util.Objects;
import java.util.Optional;

public class startPageController {

    @FXML
    private Button importFromLocalButton;

    @FXML
    void importFromLocalButtonOnAction(ActionEvent event) {
        System.out.println("press");
        Main.ifImport = true;
        //Main.importPath = "";
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.showOpenDialog(null);

        //System.out.println(jFileChooser.getSelectedFile() + "\\");
        Main.importPath = jFileChooser.getSelectedFile() + "\\";
        System.out.println(Main.importPath);

        if(Main.importPath != null) {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(Main.importPath + "info.txt")));
                BufferedReader bufferedReader = new BufferedReader(reader);
                Main.paneWidth = Integer.parseInt(bufferedReader.readLine());
                Main.paneHeight = Integer.parseInt(bufferedReader.readLine());
            } catch(Exception e) {}
        }


        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open directory");
        //File importFile = fileChooser.showOpenDialog(new Stage());

        File [] files = path.listFiles();

        for(int i=0; i<files.length; i++) {
            System.out.println(files[i].toString());
        }*/

        /*
        if(importFile != null) {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(importFile));
                BufferedReader bufferedReader = new BufferedReader(reader);
                Main.paneWidth = Integer.parseInt(bufferedReader.readLine());
                Main.paneHeight = Integer.parseInt(bufferedReader.readLine());
            } catch(Exception e) {}
        }*/

        /*
        if(importFile != null) {
            try {

                InputStreamReader reader = new InputStreamReader(new FileInputStream(importFile));
                System.out.println("import file path: " + importFile.getAbsolutePath());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String readLine = "";
                readLine = bufferedReader.readLine();
                while(readLine != null) {
                    System.out.println(readLine);
                    readLine = bufferedReader.readLine();
                }

            } catch (Exception e) {}

        }*/


        launchPage();

    }

    @FXML
    void newProjectButtonOnAction(ActionEvent event) {
        setWidthAndHeight();
        launchPage();
    }

    @FXML
    void ExitButtonOnAction(ActionEvent event) {
        ((Stage)Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null)).close();
    }

    private void setWidthAndHeight() {
        //need redesign here
        Stage stage = new Stage();
        //Button selectSavePathButton = new Button("select path");
        Button button = new Button("confirm");
        TextArea wTextArea = new TextArea();
        wTextArea.setPrefSize(50,10);
        TextArea hTextArea = new TextArea();
        hTextArea.setPrefSize(50,10);
        button.setOnAction(e -> {
            //still need to determine if the input string in a number
            Main.paneWidth = Integer.parseInt(wTextArea.getText());
            Main.paneHeight = Integer.parseInt(hTextArea.getText());
            stage.close();
        });
        HBox wHBox = new HBox(new Label("width"), wTextArea);
        wHBox.setSpacing(10);
        wHBox.setPadding(new Insets(10));
        HBox hHBox = new HBox(new Label("height"), hTextArea);
        hHBox.setSpacing(10);
        hHBox.setPadding(new Insets(10));
        VBox vBox = new VBox(new Label("Enter width and height"), wHBox, hHBox, button);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void launchPage() {
        //Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        Stage stage = (Stage) importFromLocalButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("../resources/fxmlFile/view.fxml")));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1700, 800);
            stage.setScene(scene);
        } catch (IOException e2) {}
    }

    /*
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    FileChooserSample.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }*/
}
