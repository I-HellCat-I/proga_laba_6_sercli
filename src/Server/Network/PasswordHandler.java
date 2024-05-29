package Server.Network;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHandler {
    private static String pepper = "J婫aÛGݛݾl\uDACA\uDC84ზ\uDA67\uDE1D΄\uDB89卐\uDE36ɰ\uDA2E\uDEBF";

    public static String hashPassword(char[] password, String salt) {
        String input = pepper + String.valueOf(password) + salt;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 hashing algorithm not available.", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}