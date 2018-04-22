package model;

import java.security.*;
import java.nio.charset.*;

public class Server_Side_Hash {
    
    public String hashThis(String username, String unhashedPassword) {
        //Using user name as the salt, and the first hashed value as the input this time
        //Very simple SHA512
        //If a more complex version is wanted, simply replace this method with the wanted version, keeping inputs and String return
        String hashedPass = null;
        try {
            MessageDigest mess = MessageDigest.getInstance("SHA-512");
            mess.update(username.getBytes(StandardCharsets.UTF_8));
            byte[] holds = mess.digest(unhashedPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder strung = new StringBuilder();
            int i = 0;
            while (i < holds.length) {
                strung.append(Integer.toString((holds[i] & 0xff) + 0x100, 16).substring(1));
                i++;
            }
            hashedPass = strung.toString();
        } catch (NoSuchAlgorithmException e) {
            //Shouldn't happen
            //But if it does...
            e.printStackTrace();
            //Show how we got to this messed up location
        }
        return hashedPass;
    }
}
