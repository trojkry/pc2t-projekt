import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public abstract class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int id;
    protected String jmeno;
    protected String prijmeni;
    protected int rokNarozeni;
    protected List<Integer> znamky;

    public Student(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
        this.znamky = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public int getRokNarozeni() {
        return rokNarozeni;
    }

    public void pridejZnamku(int znamka) {
        znamky.add(znamka);
    }

    public double vypocitejPrumer() {
        if (znamky.isEmpty()) return 0.0;
        int soucet = 0;
        for (int z : znamky) soucet += z;
        return (double) soucet / znamky.size();
    }

    public String getZnamkyAsString() {
        return String.join(",", znamky.stream().map(String::valueOf).toArray(String[]::new));
    }

    public void nactiZnamkyZeStringu(String znamkyStr) {
        znamky.clear();
        if (znamkyStr == null || znamkyStr.isEmpty()) return;
        for (String z : znamkyStr.split(",")) znamky.add(Integer.parseInt(z));
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String spustDovednost();
}
