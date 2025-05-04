import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.Collator;
import java.util.*;

public class Univerzita {
    private Map<Integer, Student> studenti;
    private int dalsiId;

    public Univerzita() {
        studenti = new HashMap<>();
        dalsiId = 1;
    }

    private void aktualizujDalsiId() {
        if (studenti.isEmpty()) {
            dalsiId = 1;
        } else {
            dalsiId = Collections.max(studenti.keySet()) + 1;
        }
    }

    public void pridejStudenta(String jmeno, String prijmeni, int rokNarozeni, int obor) {
        Student student;
        if (obor == 1) {
            student = new TelekomunikaceStudent(dalsiId, jmeno, prijmeni, rokNarozeni);
        } else if (obor == 2) {
            student = new KyberbezpecnostStudent(dalsiId, jmeno, prijmeni, rokNarozeni);
        } else {
            System.out.println("Neznámý obor.");
            return;
        }
        studenti.put(dalsiId, student);
        dalsiId++;
    }

    public void pridejZnamku(int id, int znamka) {
        Student student = studenti.get(id);
        if (student == null) {
            System.out.println("Student s ID: " + id + " neexistuje.");
            return;
        }
        if (znamka < 1 || znamka > 5) {
            System.out.println("Neplatná známka. Zadávej 1 až 5.");
            return;
        }
        student.pridejZnamku(znamka);
    }

    public void smazStudenta(int id) {
        studenti.remove(id);
    }

    public Student najdiStudenta(int id) {
        return studenti.get(id);
    }

    public List<Student> getAbecedneSerazeni(int obor) {
        List<Student> seznam = new ArrayList<>();
        for (Student s : studenti.values()) {
            if (obor == 1 && s instanceof TelekomunikaceStudent) {
                seznam.add(s);
            } else if (obor == 2 && s instanceof KyberbezpecnostStudent) {
                seznam.add(s);
            }
        }
        Collator collator = Collator.getInstance(Locale.forLanguageTag("cs-CZ"));
        seznam.sort((s1, s2) -> collator.compare(s1.getPrijmeni(), s2.getPrijmeni()));
        return seznam;
    }

    public double vypocitejObecnyPrumerOboru(int idOboru) {
        double soucet = 0.0;
        int pocet = 0;
    
        if (idOboru == 1) {
            for (Student s : studenti.values()) {
                if (s instanceof TelekomunikaceStudent) {
                    String znamky = s.getZnamkyAsString();
                    if (znamky != null && !znamky.isEmpty()) {
                        String[] znamkyPole = znamky.split(",");
                        for (String z : znamkyPole) {
                            soucet += Integer.parseInt(z.trim());
                            pocet++;
                        }
                    }
                }
            }
        } else if (idOboru == 2) {
            for (Student s : studenti.values()) {
                if (s instanceof KyberbezpecnostStudent) {
                    String znamky = s.getZnamkyAsString();
                    if (znamky != null && !znamky.isEmpty()) {
                        String[] znamkyPole = znamky.split(",");
                        for (String z : znamkyPole) {
                            soucet += Integer.parseInt(z.trim());
                            pocet++;
                        }
                    }
                }
            }
        } else {
            System.out.println("Neplatný obor.");
            return -1;
        }
    
        return pocet > 0 ? soucet / pocet : 0.0;
    }

    public int pocetStudentuTelekomunikace() {
        int pocet = 0;
        for (Student s : studenti.values()) {
            if (s instanceof TelekomunikaceStudent) {
                pocet++;
            }
        }
        return pocet;
    }

    public int pocetStudentuKyberbezpecnost() {
        int pocet = 0;
        for (Student s : studenti.values()) {
            if (s instanceof KyberbezpecnostStudent) {
                pocet++;
            }
        }
        return pocet;
    }

    public void ulozStudentaDoSouboru(int id) {
        Student student = studenti.get(id);
        if (student == null) {
            System.out.println("Student s ID: " + id + " neexistuje.");
            return;
        }
    
        String soubor = student.getJmeno() + "_" + student.getPrijmeni() + "_" + student.getRokNarozeni() + ".ser";
    
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(soubor))) {
            oos.writeObject(student);
            System.out.println("Student uložen do souboru " + soubor);
        } catch (Exception e) {
            System.out.println("Chyba při ukládání studenta.");
            e.printStackTrace();
        }
    }

    public void nactiStudentaZeSouboru(String cesta) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cesta))) {
            Student student = (Student) ois.readObject();
            student.setId(dalsiId);
            studenti.put(dalsiId, student);
            aktualizujDalsiId();
            System.out.println("Student načten ze souboru a přidán do univerzity.");
        } catch (Exception e) {
            System.out.println("Chyba při načítání studenta.");
            e.printStackTrace();
        }
    }
    
    public void nactiStudentaZeSouboruUI() {
        File folder = new File(".");
        File[] serSoubory = folder.listFiles((dir, name) -> name.endsWith(".ser"));
    
        if (serSoubory == null || serSoubory.length == 0) {
            System.out.println("Nenalezeny žádné .ser soubory.");
            return;
        }
    
        System.out.println("Dostupné soubory:");
        for (int i = 0; i < serSoubory.length; i++) {
            System.out.println((i + 1) + ": " + serSoubory[i].getName());
        }
    
        Scanner scanner = new Scanner(System.in, "UTF-8");
        int volba = -1;
        while (true) {
            System.out.print("Vyber číslo souboru pro import: ");
            if (scanner.hasNextInt()) {
                volba = scanner.nextInt();
                scanner.nextLine();
                if (volba >= 1 && volba <= serSoubory.length) {
                    break;
                } else {
                    System.out.println("Neplatné číslo. Zkuste znovu.");
                }
            } else {
                System.out.println("Musíte zadat číslo.");
                scanner.nextLine();
            }
        }
    
        String cesta = serSoubory[volba - 1].getName();
        nactiStudentaZeSouboru(cesta);
    }

    String cestaKSchema = "sql/schema.sql";

    private void spustSqlScript(Connection conn, String cestaKSouboru) throws SQLException, IOException {
        String sql = new String(Files.readAllBytes(Paths.get(cestaKSouboru)));
        Statement stmt = conn.createStatement();
        for (String prikaz : sql.split(";")) {
            if (!prikaz.trim().isEmpty()) {
                stmt.execute(prikaz.trim());
            }
        }
    }


    public void nacistZDatabaze() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:univerzita.db")) {
            spustSqlScript(conn, cestaKSchema);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM studenti");
            while (rs.next()) {
                int id = rs.getInt("id");
                String jmeno = rs.getString("jmeno");
                String prijmeni = rs.getString("prijmeni");
                int rokNarozeni = rs.getInt("rokNarozeni");
                int obor = rs.getInt("obor");
                String znamkyStr = rs.getString("znamky");

                Student student = (obor == 1)
                    ? new TelekomunikaceStudent(id, jmeno, prijmeni, rokNarozeni)
                    : new KyberbezpecnostStudent(id, jmeno, prijmeni, rokNarozeni);
                student.nactiZnamkyZeStringu(znamkyStr);
                studenti.put(id, student);
            }
            aktualizujDalsiId();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public void ulozDoDatabaze() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:univerzita.db")) {
            spustSqlScript(conn, cestaKSchema);
    
            Statement stmt = conn.createStatement();
            stmt.execute("DELETE FROM studenti"); 
    
            String sql = "INSERT INTO studenti (id, jmeno, prijmeni, rokNarozeni, obor, znamky) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
    
            for (Student s : studenti.values()) {
                pstmt.setInt(1, s.getId());
                pstmt.setString(2, s.getJmeno());
                pstmt.setString(3, s.getPrijmeni());
                pstmt.setInt(4, s.getRokNarozeni());
                pstmt.setInt(5, (s instanceof TelekomunikaceStudent) ? 1 : 2);
                pstmt.setString(6, s.getZnamkyAsString());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


}
