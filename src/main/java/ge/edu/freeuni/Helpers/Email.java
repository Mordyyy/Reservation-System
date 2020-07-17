package ge.edu.freeuni.Helpers;

import ge.edu.freeuni.Helpers.JavaMailUtil;
import ge.edu.freeuni.Models.Randomizer;

import javax.mail.MessagingException;
import java.util.HashMap;

public class Email{
    private final String verificationString = "Your verification code is :";
    private HashMap<String,Integer> randomCodes;    // key = email, value = random integer
    private Randomizer randomizer;
    public Email(){
        randomCodes = new HashMap<>();
        randomizer = new Randomizer();
    }
    public boolean verifyRandomCode(String email, int code){
        if(!randomCodes.containsKey(email)) return false;
        return randomCodes.get(email) == code;
    }

    public void sendCode(String email, String subject, String message) throws MessagingException {
        JavaMailUtil.sendMail(email, subject, message);
    }

    public void sendRandomCode(String email) throws MessagingException {
        int randomCode = randomizer.getRandomInteger();
        randomCodes.put(email,randomCode);
        sendCode(email,"Email Verification Code", verificationString + Integer.toString(randomCode));
    }

    public int getUsersCode(String email){
        if(!randomCodes.containsKey(email)) return -1;
        return randomCodes.get(email);
    }
}
