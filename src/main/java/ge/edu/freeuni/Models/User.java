package ge.edu.freeuni.Models;

public class User {
    private String username, password, mail, avatar;
    private int cancelledOrders;

    public User(String username, String password, String mail, String avatar, int cancelledOrders) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.avatar = avatar;
        this.cancelledOrders = cancelledOrders;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getCancelledOrders() {
        return cancelledOrders;
    }
}
