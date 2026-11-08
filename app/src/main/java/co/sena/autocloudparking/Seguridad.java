package co.sena.autocloudparking;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class Seguridad {

    public static String nuevaSal() {
    byte[] bytes = new byte[16];
    new SecureRandom().nextBytes(bytes);
    return Base64.encodeToString(bytes, Base64.NO_WRAP);
}

    public static String hash (String contraseña, String sal) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] resultado = md.digest ((sal+contraseña).getBytes(StandardCharsets.UTF_8));
        StringBuilder texto = new StringBuilder();
        for(byte b : resultado) {
        texto.append(String.format("%02x", b));
        }
        return texto.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);

    }
    }
}
