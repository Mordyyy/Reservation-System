package ge.edu.freeuni.Models;

public class Challenge {
    private String fromUser, toUser;
    private int id, time, computerID;

    public Challenge(int id, String fromUser, String toUser, int time, int computerID) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.time = time;
        this.computerID = computerID;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", id=" + id +
                ", time=" + time +
                ", computerID=" + computerID +
                '}';
    }

    public int getId() {
        return id;
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
