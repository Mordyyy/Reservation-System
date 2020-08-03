package ge.edu.freeuni.Models;

import ge.edu.freeuni.Helpers.GenerateHash;

public class User {
    private String username, password, mail, avatar;
    private GenerateHash hasher = new GenerateHash();
    private int bonus,orders;

    public User(String username, String password, String mail) {
        this.username = username;
        this.password = hasher.generateHash(password);
        this.mail = mail;
        this.avatar = "pic.jpg";
        bonus = 0;
        orders = 0;
    }

    public User(String username, String password, String mail, String avatar) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.avatar = avatar;
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

    public int getBonus(){ return bonus;}

    public int getOrders(){ return orders;}

    public void setBonus(int bonus){
        this.bonus = bonus;
    }

    public void setOrders(int orders){
        this.orders = orders;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
