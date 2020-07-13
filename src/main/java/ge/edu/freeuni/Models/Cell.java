package ge.edu.freeuni.Models;

import java.awt.*;

public class Cell {
    int time,computerID;
    String text;
    String color;

    public Cell(int time, int computerID, String text, String color) {
        this.time = time;
        this.computerID = computerID;
        this.text = text;
        this.color = color;
    }

    public synchronized void setTime(int time) {
        this.time = time;
    }

    public synchronized void setComputerID(int computerID) {
        this.computerID = computerID;
    }

    public int getTime() {
        return time;
    }

    public int getComputerID() {
        return computerID;
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
