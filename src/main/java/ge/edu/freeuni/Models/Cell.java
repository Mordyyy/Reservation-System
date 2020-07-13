package ge.edu.freeuni.Models;

import java.awt.*;

public class Cell {
    String text;
    String color;

    public Cell(String text, String color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public synchronized void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public synchronized void setColor(String color) {
        this.color = color;
    }
}
