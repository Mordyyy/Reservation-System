package ge.edu.freeuni.Hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenerateHash {
    private String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;
            if (val < 16) buff.append('0');
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    public String generateHash(String targ) {
        String s = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(targ.getBytes());
            s = hexToString(md.digest());
        } catch (NoSuchAlgorithmException e) {}
        return s;
    }
}