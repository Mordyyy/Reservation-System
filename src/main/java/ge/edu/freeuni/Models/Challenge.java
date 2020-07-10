package ge.edu.freeuni.Models;

public class Challenge {
    private String fromUser, toUser;
    private int time, computerID;

    public Challenge(String fromUser, String toUser, int time, int computerID) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.time = time;
        this.computerID = computerID;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public int getTime() {
        return time;
    }

    public int getComputerID() {
        return computerID;
    }
}
