public class TelekomunikaceStudent extends Student {
    public TelekomunikaceStudent(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public String spustDovednost() {
        String text = jmeno + " " + prijmeni;
        StringBuilder morse = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            morse.append(prevedNaMorse(c)).append(" ");
        }
        return morse.toString().trim();
    }

    private String prevedNaMorse(char c) {
        switch (c) {
            case 'A': return ".-";
            case 'B': return "-...";
            case 'C': return "-.-.";
            case 'D': return "-..";
            case 'E': return ".";
            case 'F': return "..-.";
            case 'G': return "--.";
            case 'H': return "....";
            case 'I': return "..";
            case 'J': return ".---";
            case 'K': return "-.-";
            case 'L': return ".-..";
            case 'M': return "--";
            case 'N': return "-.";
            case 'O': return "---";
            case 'P': return ".--.";
            case 'Q': return "--.-";
            case 'R': return ".-.";
            case 'S': return "...";
            case 'T': return "-";
            case 'U': return "..-";
            case 'V': return "...-";
            case 'W': return ".--";
            case 'X': return "-..-";
            case 'Y': return "-.--";
            case 'Z': return "--..";
            case ' ': return "/";
            default: return "";
        }
    }
}
