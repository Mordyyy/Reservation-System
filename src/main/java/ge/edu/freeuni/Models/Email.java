package ge.edu.freeuni.Models;

import java.util.HashMap;
import java.util.Random;

public class Email{
    private HashMap<String,Integer> randomCodes;    // key = email, value = random integer
    public Email(){
        randomCodes = new HashMap<>();
    }
    public boolean verifyRandomCode(String email, int code){
        if(!randomCodes.containsKey(email)) return false;
        return randomCodes.get(email) == code;
    }

    public void sendCode(String email, String message){

    }

    public void sendRandomCode(String email){
        int randomCode = getRandomInteger();
        randomCodes.put(email,randomCode);
        sendCode(email,Integer.toString(randomCode));
    }

    public int getUsersCode(String userName){
        return randomCodes.get(userName);
    }

    private int getRandomInteger(){
        Random rand = new Random();
        int randInteger = rand.nextInt(1000000) + 100000;
        return randInteger;
    }
}
