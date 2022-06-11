package main.java;

import javafx.scene.control.Button;

import java.security.PublicKey;

public class ButtonListView extends Button {
    private int i;
    private int j;
    private String line;

    public ButtonListView(String buttonName, int i, int j, String line) {
        super(buttonName);
        this.i = i;
        this.j = j;
        this.line = line;
    }

    public String getAllInfo() {
        return (i+"|"+j+"|"+line+"|"+this.getText());
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
