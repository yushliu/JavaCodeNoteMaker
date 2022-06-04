package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
//import javafx.embed.swing.SwingFXUtils;
//import javafx.
//import org.openjfx:javafx-swing


public class Controller {
    @FXML
    private BorderPane borderPane;
    @FXML
    private ScrollPane scrollPane;

    private GridPane gridPane = new GridPane();

    @FXML
    private MenuBar menuBar;
    @FXML
    private ListView<ButtonListView> lineManageCenterListView;
    @FXML
    private ComboBox<String> lineComboBox;
    @FXML
    private VBox terminalLogoPreviewVBox;
    private GridPane previewGridPaneToSave;

    static public Vector<String> terminalLogoPath = new Vector<>();
    static {
        terminalLogoPath.add("file:src/main/resources/photo/terminalPicture/file:src/main/resources/photo/terminalPicture/"+ "redLineTerminal" + ".png");
        terminalLogoPath.add("file:src/main/resources/photo/terminalPicture/"+ "blueLineTerminal" + ".png");
        terminalLogoPath.add("file:src/main/resources/photo/terminalPicture/"+ "yellowLineTerminal" + ".png");
        //orange green need to change
        terminalLogoPath.add("file:src/main/resources/photo/terminalPicture/"+ "yellowLineTerminal" + ".png");
        terminalLogoPath.add("file:src/main/resources/photo/terminalPicture/"+ "yellowLineTerminal" + ".png");
    }

    public static int curLineManageCenterListView = -1;

    static public Vector<ButtonListView>[]  lineStation = (Vector<ButtonListView>[]) new Vector[Main.lineNumber];//red blue yellow orange green
    {
        for(int i=0; i<Main.lineNumber; i++) lineStation[i] = new Vector<>();
    }

    @FXML
    public void initialize() {
        {
            for(String str: Main.lineColorName) lineComboBox.getItems().add(str);
        }

        {
            ObservableList<ButtonListView> lineManageCenterObservableList = FXCollections.observableArrayList();
            lineManageCenterListView.setItems(lineManageCenterObservableList);
        }

        scrollPane.setContent(gridPane);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        for(int i=0; i<Main.paneWidth; i++)
            for(int j=0; j<Main.paneHeight; j++) {
                gridPane.add(new BlockPane(i,j, lineManageCenterListView),i,j);
            }

        //terminalLogoPreviewVBox setup
        {
            for(int i=0; i<Main.lineNumber; i++) {
                ImageView imageView = new ImageView(terminalLogoPath.get(i));
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);
                terminalLogoPreviewVBox.getChildren().add(new VBox(new Label("  " + Main.lineColorName.get(i)), imageView));
            }
            terminalLogoPreviewVBox.getChildren().add(new VBox(new Label()));
        }
    }

    @FXML
    void lineComboBoxOnAction(ActionEvent event) {
        if(lineComboBox != null)
            switch (lineComboBox.getValue()) {
                case "red" -> {
                    //need revise if can edit line name, number
                    curLineManageCenterListView = 0;
                    System.out.println("red");
                }
                case "blue" -> {
                    curLineManageCenterListView = 1;
                    System.out.println("blue");
                }
                case "yellow" -> {
                    curLineManageCenterListView = 2;
                    System.out.println("yellow");
                }
                case "orange" -> {
                    curLineManageCenterListView = 3;
                    System.out.println("orange");
                }
                case "green" -> {
                    curLineManageCenterListView = 4;
                    System.out.println("green");
                }
            }

        new BlockPane(lineManageCenterListView).updateLineManageCenterListView();
    }

    @FXML
    void MenuItemSave(ActionEvent event) {

        try {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.close();

            FXMLLoader startPagefxmlLoader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("../resources/fxmlFile/startPage.fxml")));
            Scene startPageScene = new Scene(startPagefxmlLoader.load(), 1700, 800);
            Stage primaryStage = new Stage();
            primaryStage.setScene(startPageScene);
            primaryStage.show();
        } catch (IOException e) {}
    }

    @FXML
    void MenuItemSaveAs(ActionEvent event) {
        if(previewGridPaneToSave==null) {
            System.out.println("null");
        } else {
            System.out.println("not null");
            /*
            WritableImage image = previewGridPaneToSave.snapshot(new SnapshotParameters(), null);
            //"file:src/main/resources/com/photo/desktopTerminalPicture/"+ "one" + ".png"
            //File file = new File("D:\\anchor.png");
            //previewGridPaneToSave
            File file = new File("file:src/main/resources/com/photo/desktopTerminalPicture/"+ "one" + ".png");
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            */
      }
        /*
        WritableImage image = anchorPane.snapshot(new SnapshotParameters(), null);

        File file = new File("D:\\anchor.png");

        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);*/
    }

    @FXML
    void MenuItemInsertColumn(ActionEvent event) {
        Main.paneWidth++;
        for(int i=0; i<Main.paneHeight; i++) gridPane.add(new BlockPane(Main.paneWidth-1, i, lineManageCenterListView), (Main.paneWidth-1), i);
    }

    @FXML
    void MenuItemInsertRow(ActionEvent event) {
        Main.paneHeight++;
        for(int i=0; i<Main.paneWidth; i++) gridPane.add(new BlockPane(i, Main.paneHeight-1, lineManageCenterListView), i, (Main.paneHeight-1));
    }

    @FXML
    void previewMenuItem(ActionEvent event) {
        Stage stage = new Stage();
        GridPane pGridPane = new GridPane();
        ScrollPane pScrollPane = new ScrollPane();
        pScrollPane.setPannable(true);
        pGridPane.setPadding(new Insets(10, 10, 10, 10));
        pGridPane.setVgap(10);
        pGridPane.setHgap(10);
        pScrollPane.setContent(pGridPane);
        previewGridPaneToSave = gridPane;

        for(int i=0; i<Main.paneWidth; i++) {
            for(int j=0; j<Main.paneHeight; j++) {
                Label testLabel = new Label("used to create");
                pGridPane.add(testLabel, i, j);
                testLabel.setVisible(false);
            }
        }
        for(int i=0; i<Main.paneWidth; i++) pGridPane.getColumnConstraints().add(new ColumnConstraints(150));
        for(int j=0; j<Main.paneHeight; j++) pGridPane.getRowConstraints().add(new RowConstraints(150));

        Scene scene = new Scene(pScrollPane, 1200, 800);
        stage.setScene(scene);


        for(int i=0; i<Main.lineNumber; i++) {
            for(ButtonListView temp: lineStation[i]) {
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(terminalLogoPath.get(i)));
                /*
                switch (i) {
                    //red
                    case 0 -> {imageView.setImage(new Image(terminalLogoPath.get(0)));}
                    //blue
                    case 1 -> {imageView.setImage(new Image(terminalLogoPath.get(1)));}
                    //yellow
                    case 2 -> {imageView.setImage(new Image(terminalLogoPath.get(2)));}
                    //orange
                    case 3 -> {}
                    //green
                    case 4 -> {}
                }*/
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                pGridPane.add(imageView, temp.getI(), temp.getJ());
            }
        }

        /*
        for(int i=0; i<Main.paneWidth; i++) {
            for(int j=0; j<Main.paneHeight; j++) {
                ImageView imageView = new ImageView( );
                imageView.setImage(new Image("file:src/main/resources/com/photo/terminalPicture/"+ "blueLineTerminal" + ".png"));
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                pGridPane.add(imageView,i,j);
            }
        }*/

        stage.showAndWait();
    }

    @FXML
    void MenuitemCustomizeTerminal(ActionEvent event) {
        //System.out.println("OK");
        Stage newStage = new Stage();
        Button closeButton = new Button("Confirm");
        closeButton.setOnAction(e -> {newStage.close();});

        VBox newVBox = new VBox();
        newVBox.setPadding(new Insets(10));
        newVBox.setSpacing(10);
        newVBox.getChildren().addAll(new ToolBar(closeButton), new Label("123"), new Label("165"));

        newStage.setScene(new Scene(newVBox, 1200, 800));
        newStage.showAndWait();
    }
}