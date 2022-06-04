package main.java;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Vector;

public class BlockPane extends VBox {
    private int i;
    private int j;
    private Label label;
    private TextArea nameTextArea = new TextArea("enter name");
    private ListView<ButtonListView> lineManageCenterListView;

    private ComboBox<String> comboBox = new ComboBox<>();
    {
        comboBox.getItems().add("Central Station");
        comboBox.getItems().add("Station");
        comboBox.getItems().add("left clear");
        comboBox.getItems().add("");
    }

    private ComboBox<String> subChoiceComboBox = new ComboBox<>();
    CheckBox addCheckBox = new CheckBox("set");

    //used for update listview
    public BlockPane(ListView<ButtonListView> lineManageCenterListView) {
        setLineManageCenterListView(lineManageCenterListView);
    }

    public BlockPane(int i, int j, ListView<ButtonListView> lineManageCenterListView) {
        setLineManageCenterListView(lineManageCenterListView);
        this.i = i;
        this.j = j;
        this.label = new Label("("+i+", "+j+")");
        label.setPrefHeight(15);
        nameTextArea.setPrefSize(50,15);
        //name.bind(nameTextArea.textProperty());
        addCheckBox.selectedProperty().addListener(e -> {
            if(addCheckBox.isSelected()) {
                if(subChoiceComboBox.getValue() != null) {
                    int choice = -1;
                    switch (subChoiceComboBox.getValue()) {//red blue yellow orange green
                        case "red" -> {choice = 0; break;}
                        case "blue" -> {choice = 1;break;}
                        case "yellow" -> {choice = 2;break;}
                        case "orange" -> {choice = 3;break;}
                        case "green" -> {choice = 4;break;}
                    }
                    Controller.lineStation[choice].add(new ButtonListView(nameTextArea.getText(), this.i, this.j, subChoiceComboBox.getValue()));
                    System.out.println("add" + choice);
                    updateLineManageCenterListView();
                }
            } else {
                int numberArray = 0;
                if(subChoiceComboBox.getValue() != null)
                    switch (subChoiceComboBox.getValue()) {//red blue yellow orange green
                        case "red" -> {numberArray = 0;}
                        case "blue" -> {numberArray = 1;}
                        case "yellow" -> {numberArray = 2;}
                        case "orange" -> {numberArray = 3;}
                        case "green" -> {numberArray = 4;}
                    }

                for(int k=0; k<Controller.lineStation[numberArray].size(); k++) {
                    if(((ButtonListView)(Controller.lineStation[numberArray].get(k))).getI()==this.i && ((ButtonListView)(Controller.lineStation[numberArray].get(k))).getJ()==this.j)  {
                        Controller.lineStation[numberArray].remove(k);
                    }
                }
                updateLineManageCenterListView();
            }
        });

        this.getChildren().add(label);
        this.getChildren().add(comboBox);
        this.setPrefSize(150,150);
        //this.getChildren().add(subChoiceComboBox);
        //this.getChildren().add(nameTextArea);
        //this.getChildren().add(addCheckBox);

        comboBox.setOnAction(e -> {
            if(comboBox.getValue() != null) {
                switch (comboBox.getValue()) {
                    case "Central Station" -> {

                    }

                    case "Station" -> {
                        this.getChildren().add(subChoiceComboBox);
                        this.getChildren().add(nameTextArea);
                        this.getChildren().add(addCheckBox);
                        for(String str: Main.lineColorName) subChoiceComboBox.getItems().add(str);
                    }

                    case "left clear" -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("The block will no longer be available!");
                        alert.setContentText("");
                        alert.showAndWait().ifPresentOrElse(rs -> {if (rs == ButtonType.OK) this.setVisible(false);}, () -> {comboBox.setValue("");});
                    }
                }
            }
        });
    }

    public void setLineManageCenterListView(ListView<ButtonListView> lineManageCenterListView) {
        this.lineManageCenterListView = lineManageCenterListView;
    }
    public void updateLineManageCenterListView() {
        lineManageCenterListView.getItems().clear();
        if(Controller.curLineManageCenterListView != -1) {
            for(ButtonListView temp: Controller.lineStation[Controller.curLineManageCenterListView]) {
                lineManageCenterListView.getItems().add(temp);
            }
        }
    }

    public String getInfoToSave() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(i + "|" + j);


        if(addCheckBox.selectedProperty().getValue()) {
            System.out.println("selected");
            stringBuffer.append("|" + comboBox.getValue().toString() + "|" + subChoiceComboBox.getValue().toString());
        } else {
            System.out.println("not selected");
        }

        stringBuffer.append("\n");

        return stringBuffer.toString();
    }

}
