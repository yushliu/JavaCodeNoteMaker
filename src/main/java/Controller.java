package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;


import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Vector;


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
    //private GridPane previewGridPaneToSave;

    static public Vector<String> terminalLogoPath = new Vector<>();
    static {
        terminalLogoPath.add("file:src/main/resources/photo/terminalPicture/"+ "redLineTerminal" + ".png");
        terminalLogoPath.add("file:src/main/resources/photo/terminalPicture/"+ "blueLineTerminal" + ".png");
        terminalLogoPath.add("file:src/main/resources/photo/terminalPicture/"+ "yellowLineTerminal" + ".png");
        //orange green need to change
        terminalLogoPath.add("file:src/main/resources/photo/terminalPicture/"+ "orangeLineTerminal" + ".png");
        terminalLogoPath.add("file:src/main/resources/photo/terminalPicture/"+ "greenLineTerminal" + ".png");
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
            terminalLogoPreviewVBoxUpdate();
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
    void MenuItemReturn(ActionEvent event) {
//something here
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
        previewMenuItemAndSave();
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
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                pGridPane.add(imageView, temp.getI(), temp.getJ());
            }
        }
        stage.showAndWait();
    }

    @FXML
    void MenuitemCustomizeTerminal(ActionEvent event) {
        //System.out.println("OK");
        Stage newStage = new Stage();
        Button closeButton = new Button("Confirm");
        closeButton.setOnAction(e -> {
            terminalLogoPreviewVBoxUpdate();
            newStage.close();
        });

        VBox newVBox = new VBox();
        newVBox.setPadding(new Insets(10));
        newVBox.setSpacing(10);
        //button and a label
        newVBox.getChildren().add(new ToolBar(closeButton));
        for(int i=0; i<Main.lineNumber; i++) {
            VBox temp = new VBox();
            temp.setPadding(new Insets(10));
            temp.setSpacing(10);

            Label displayPathLabel = new Label(Controller.terminalLogoPath.get(i));
            Button selectPathButton = new Button("select file");
            ImageView imageToDisplay = new ImageView();
            imageToDisplay.setImage(new Image(displayPathLabel.getText()));
            imageToDisplay.setFitWidth(120);
            imageToDisplay.setFitHeight(120);
            FileChooser fileChooser = new FileChooser();
            selectPathButton.setOnAction(e -> {
                File fileToSave = fileChooser.showSaveDialog(newStage);
                if(fileToSave != null) {
                    int idx = terminalLogoPath.indexOf(displayPathLabel.getText());
                    System.out.println("index" + idx);
                    terminalLogoPath.set(idx, fileToSave.toURI().toString());
                    displayPathLabel.setText(terminalLogoPath.get(idx));
                    imageToDisplay.setImage(new Image(fileToSave.toURI().toString()));
                    //terminalLogoPath.get(i) =fileToSave.toURI().toString();
                }
            });

            temp.getChildren().addAll(new Label(Main.lineColorName.get(i)), displayPathLabel, selectPathButton, imageToDisplay);
            newVBox.getChildren().add(temp);
        }

        newStage.setScene(new Scene(new ScrollPane(newVBox), 1200, 800));
        newStage.showAndWait();
    }

    private void previewMenuItemAndSave() {
        GridPane pGridPane = new GridPane();
        ScrollPane pScrollPane = new ScrollPane();
        pScrollPane.setPannable(true);
        pGridPane.setPadding(new Insets(10, 10, 10, 10));
        pGridPane.setVgap(10);
        pGridPane.setHgap(10);
        pScrollPane.setContent(pGridPane);

        for(int i=0; i<Main.paneWidth; i++) {
            for(int j=0; j<Main.paneHeight; j++) {
                Label testLabel = new Label("used to create");
                pGridPane.add(testLabel, i, j);
                testLabel.setVisible(true);
            }
        }
        for(int i=0; i<Main.paneWidth; i++) pGridPane.getColumnConstraints().add(new ColumnConstraints(150));
        for(int j=0; j<Main.paneHeight; j++) pGridPane.getRowConstraints().add(new RowConstraints(150));

        Scene scene = new Scene(pScrollPane, 1200, 800);

        for(int i=0; i<Main.lineNumber; i++) {
            for(ButtonListView temp: lineStation[i]) {
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(terminalLogoPath.get(i)));
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                pGridPane.add(imageView, temp.getI(), temp.getJ());
            }
        }

        WritableImage image = pGridPane.snapshot(new SnapshotParameters(), null);
        try {
            FileChooser fileChooser = new FileChooser();
            /*
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "image files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);*/
            fileChooser.setTitle("select folder path");
            File folderPath = fileChooser.showSaveDialog(new Stage());

            if (folderPath != null) {
                if(folderPath.mkdir() == true) System.out.println("folder create successfully");
                else System.out.println("folder create unsuccessfully");

                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(folderPath.getAbsolutePath() + "\\" + "Image.png"));
                File saveTerminalInfoFile = new File(folderPath.getAbsolutePath() + "\\" + "info.txt");
                saveTerminalInfo(saveTerminalInfoFile);

                //copy terminal picture
                for(int i=0; i<Main.lineNumber; i++) {
                    URL source = new URL(terminalLogoPath.get(i));
                    System.out.println("target: " + source);
                    BufferedImage bufferedImage = ImageIO.read(source);

                    String path = folderPath.getAbsolutePath() + "\\";

                    switch (i) {
                        case 0 -> {path += "red.png";}
                        case 1 -> {path += "blue.png";}
                        case 2 -> {path += "yellow.png";}
                        case 3 -> {path += "orange.png";}
                        case 4 -> {path += "green.png";}
                    }
                    File target = new File(path);
                    ImageIO.write(bufferedImage, "png", target);
                }
            }
        } catch (Exception e) {}
    }

    private void saveTerminalInfo(File file) {
        //file.
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(Main.paneWidth + "\n");//width
            fileWriter.write(Main.paneHeight + "\n");//height

            for(int i=0; i<Main.paneWidth; i++) {
                for(int j=0; j<Main.paneHeight; j++) {
                    fileWriter.write(((BlockPane)gridPane.getChildren().get(i*Main.paneWidth+j)).getInfoToSave());
                }
            }

            fileWriter.close();
        } catch (IOException e) {e.printStackTrace();}
    }

    private void terminalLogoPreviewVBoxUpdate() {
        terminalLogoPreviewVBox.getChildren().clear();
        for(int i=0; i<Main.lineNumber; i++) {
            ImageView imageView = new ImageView(terminalLogoPath.get(i));
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            terminalLogoPreviewVBox.getChildren().add(new VBox(new Label("  " + Main.lineColorName.get(i)), imageView));
        }
        terminalLogoPreviewVBox.getChildren().add(new VBox(new Label()));
    }


    //test
    private void copyFileUsingFileStreams(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }
}