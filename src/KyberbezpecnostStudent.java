import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class KyberbezpecnostStudent extends Student {
    public KyberbezpecnostStudent(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public String spustDovednost() {
        String text = jmeno + prijmeni;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(text.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            return "Chyba při generování hashe";
        }
    }
}
