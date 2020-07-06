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

    public void sendCode(String email, String subject, String message){

    }

    public void sendRandomCode(String email){
        int randomCode = getRandomInteger();
        randomCodes.put(email,randomCode);
        sendCode(email,"Email Verification Code", Integer.toString(randomCode));
    }

    public int getUsersCode(String email){
        if(!randomCodes.containsKey(email)) return -1;
        return randomCodes.get(email);
    }

    private int getRandomInteger(){
        Random rand = new Random();
        int randInteger = rand.nextInt(1000000) + 100000;
        return randInteger;
    }
}
