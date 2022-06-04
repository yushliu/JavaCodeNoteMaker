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
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class startPageController {

    @FXML
    void importFromLocalButtonOnAction(ActionEvent event) {


    }

    @FXML
    void newProjectButtonOnAction(ActionEvent event) {
        setWidthAndHeight();
        launchPage(event);
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

    private void launchPage(ActionEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("../resources/fxmlFile/view.fxml")));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1700, 800);
            stage.setScene(scene);
        } catch (IOException e2) {}
    }

    private void launchPageAndImportFromLocal(ActionEvent event) {

    }
}
