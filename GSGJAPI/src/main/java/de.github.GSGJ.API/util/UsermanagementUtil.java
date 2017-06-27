package de.github.GSGJ.API.util;

import de.github.GSGJ.API.json.JSONCore;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Kojy on 27.06.2017.
 */
public class UsermanagementUtil {
    private static Logger logger = LoggerFactory.getLogger(UsermanagementUtil.class);

    public static boolean checkJSONUser(JSONObject jsonObject) {
        String username = (String) jsonObject.get(JSONCore.CORE.USERNAME);
        String password = (String) jsonObject.get(JSONCore.CORE_USERMANAGEMENT.PASSWORD);
        String email = (String) jsonObject.get(JSONCore.CORE_USERMANAGEMENT.EMAIL);

        return checkUsername(username) && checkPassword(password) && checkEmail(email);
    }

    public static boolean checkUsername(String username){
        if(username == null || username.trim().equals("")) return false;

        //checks whether the username consist of min. 6 and max. 16 characters
        if (username.length() < 6 || username.length() > 16) {
            return false;
        }

        //checks whether the username only consists of valid characters
        for (int x = 0; x < username.length(); x++) {
            if ((username.charAt(x) >= 47 && username.charAt(x) <= 58) ||
                    (username.charAt(x) >= 64 && username.charAt(x) <= 91) ||
                    (username.charAt(x) >= 97 && username.charAt(x) <= 122)) {
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPassword(String password){
        if(password == null || password.trim().equals("")) return false;

        //checks whether the password only consist of valid characters
        for (int x = 0; x < password.length(); x++) {
            if ((password.charAt(x) >= 47 && password.charAt(x) <= 58) ||
                    (password.charAt(x) >= 64 && password.charAt(x) <= 91) ||
                    (password.charAt(x) >= 97 && password.charAt(x) <= 122)) {

            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean checkEmail(String email){
        if(email == null || email.trim().equals("")) return false;

        //validation of the email (checks the regular spelling of an email)
        //source: http://howtodoinjava.com/regex/java-regex-validate-email-address/
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(email);
        if (!m.matches()) {
            return false;
        }

        return true;
    }

    public static String generateHashPassword(String password){
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(),e);
        }
        return generatedPassword;
    }
}
