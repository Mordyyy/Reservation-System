package ge.edu.freeuni.Models;

import java.util.HashMap;
import java.util.Random;

public class Email{
    private HashMap<String,Integer> randomCodes;
    public Email(){
        randomCodes = new HashMap<>();
    }
    public boolean verifyRandomCode(User user, int code){
        if(!randomCodes.containsKey(user.getUsername())) return false;
        return randomCodes.get(user.getUsername()) == code;
    }

    public void sendCode(User user, String message){

    }

    public void sendRandomCode(User user){
        int randomCode = getRandomInteger();
        randomCodes.put(user.getUsername(),randomCode);
        sendCode(user,Integer.toString(randomCode));
    }

    private int getRandomInteger(){
        Random rand = new Random();
        int randInteger = rand.nextInt(1000000) + 100000;
        return randInteger;
    }
}
