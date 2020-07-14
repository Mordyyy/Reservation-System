package ge.edu.freeuni.Models;

public class Reservation {
    private String username;
    private int time,compID;

    public Reservation(String username, int time, int compID) {
        this.username = username;
        this.time = time;
        this.compID = compID;
    }

    public String getUsername() {
        return username;
    }

    public int getTime() {
        return time;
    }

    public int getCompID() {
        return compID;
    }
}
