package ge.edu.freeuni.Models;

import ge.edu.freeuni.Mail.JavaMailUtil;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Random;

public class Email{
    private final String verificationString = "Your verification code is :";
    private HashMap<String,Integer> randomCodes;    // key = email, value = random integer
    public Email(){
        randomCodes = new HashMap<>();
    }
    public boolean verifyRandomCode(String email, int code){
        if(!randomCodes.containsKey(email)) return false;
        return randomCodes.get(email) == code;
    }

    public void sendCode(String email, String subject, String message) throws MessagingException {
        JavaMailUtil.sendMail(email, subject, message);
    }

    public void sendRandomCode(String email) throws MessagingException {
        int randomCode = getRandomInteger();
        randomCodes.put(email,randomCode);
        sendCode(email,"Email Verification Code", verificationString + Integer.toString(randomCode));
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
