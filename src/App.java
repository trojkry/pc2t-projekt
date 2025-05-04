import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Univerzita univerzita = new Univerzita();
        univerzita.nacistZDatabaze();
        Scanner scanner = new Scanner(System.in, "UTF-8");
        boolean konec = false;
        
        while (!konec) {
            System.out.println("=============MENU=============");
            System.out.println("Vyberte možnost:");
        	System.out.println("1. Seznam studentů podle oboru (Abecedně)");
        	System.out.println("2. Přidat studenta");
        	System.out.println("3. Zobrazit studenta podle ID");
        	System.out.println("4. Přidat známku studentovi");
        	System.out.println("5. Spustit dovednost studenta podle ID");
        	System.out.println("6. Smazat studenta");
        	System.out.println("7. Vypočítat průměr oborů");
        	System.out.println("8. Vypsat počet studentů v jednotlivých oborech");
        	System.out.println("9. Uložit studenta do souboru");
        	System.out.println("10. Načíst studenta ze souboru");
        	System.out.println("11. Ukončit program");
            System.out.print("Vyber možnost: ");
            int volba = scanner.nextInt();
            scanner.nextLine();

            switch(volba) {
            case 1:
                int idOboru3 = -1;
                while (true) {
                    System.out.print("Zadej ID oboru (Telekomunikace - 1, Kyberbezpecnost - 2): ");
                    if (scanner.hasNextInt()) {
                        idOboru3 = scanner.nextInt();
                        scanner.nextLine();
                        if (idOboru3 == 1 || idOboru3 == 2) {
                            break;
                        } else {
                            System.out.println("Chyba: Zadali jste špatné číslo oboru.");
                        }
                    } else {
                        System.out.println("Chyba: Musíte zadat ID oboru.");
                        scanner.nextLine();
                    }
                }
                List<Student> seznam = univerzita.getAbecedneSerazeni(idOboru3);
                for (Student s : seznam) {
                    System.out.println("ID: " + s.getId() + ", Příjmení: " + s.getPrijmeni() + ", Jméno: " + s.getJmeno() + ", Rok narození: " + s.getRokNarozeni() + ", Průměr: " + s.vypocitejPrumer());
                }
                break;
                case 2:
                    System.out.print("Jméno: ");
                    String jmeno = scanner.nextLine();
                    System.out.print("Příjmení: ");
                    String prijmeni = scanner.nextLine();
                    System.out.print("Rok narození: ");
                    int rokNarozeni = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Zadej ID oboru (Telekomunikace - 1, Kyberbezpečnost - 2): ");
                    int idOboru = scanner.nextInt();
                    univerzita.pridejStudenta(jmeno, prijmeni, rokNarozeni, idOboru);
                    System.out.println(jmeno + " " + prijmeni + " byl přidán do oboru " + (idOboru == 1 ? "Telekomunikace" : "Kyberbezpečnost") + ".");
                    System.out.println("Student byl přidán.");
                    break;
                case 3:
                    while (true) {
                        System.out.println("Zadej ID studenta, kterého chceš vyhledat:");
                        if (scanner.hasNextInt()) {
                            int idStudenta = scanner.nextInt();
                            Student student = univerzita.najdiStudenta(idStudenta);
                            if (student != null) {
                                System.out.println("ID: " + student.getId() + ", Příjmení: " + student.getPrijmeni() + ", Jméno: " + student.getJmeno() + ", Rok narození: " + student.getRokNarozeni() + ", Znamky: " + student.getZnamkyAsString() + ", Průměr: " + student.vypocitejPrumer());
                            } else {
                                System.out.println("Student s ID " + idStudenta + " nebyl nalezen.");
                            }
                            break;
                        } else {
                            System.out.println("Chyba: Musíte zadat ID studenta (číslo).");
                            scanner.nextLine();
                        }
                    }
                    break;
                case 4:
                    System.out.print("Zadej ID studenta: ");
                    int id = scanner.nextInt();
                    System.out.print("Zadej známku (1-5): ");
                    int znamka = scanner.nextInt();
                    univerzita.pridejZnamku(id, znamka);
                    break;
                
                case 5:
                    while (true) {
                        System.out.println("Zadej ID studenta, u kterého chceš spustit dovednost:");
                        if (scanner.hasNextInt()) {
                            int idStudenta = scanner.nextInt();
                            Student student = univerzita.najdiStudenta(idStudenta);
                            if (student != null) {
                                String dovednost = student.spustDovednost();
                                System.out.println("Výsledek dovednosti studenta " + student.getJmeno() + " " + student.getPrijmeni() +":"  + dovednost);
                            } else {
                                System.out.println("Student s ID " + idStudenta + " nebyl nalezen.");
                            }
                            break;
                        } else {
                            System.out.println("Chyba: Musíte zadat ID studenta (číslo).");
                            scanner.nextLine();
                        }
                    }
                    break;
                case 6:
                    while (true) {
                        System.out.println("Zadej ID studenta, kterého odstranit:");
                        if (scanner.hasNextInt()) {
                            int idStudenta = scanner.nextInt();
                            Student student = univerzita.najdiStudenta(idStudenta);
                            if (student != null) {
                                univerzita.smazStudenta(idStudenta);
                                System.out.println("Student s ID " + idStudenta + " byl úspěšně odstraněn.");
                            } else {
                                System.out.println("Student s ID " + idStudenta + " nebyl nalezen.");
                            }
                            break;
                        } else {
                            System.out.println("Chyba: Musíte zadat ID studenta (číslo).");
                            scanner.nextLine();
                        }
                    }
                    break;
                case 7:
                    System.out.println("Průměr oboru Telekomunikace: " + univerzita.vypocitejObecnyPrumerOboru(1));
                    System.out.println("Průmět oboru Kyberbezpečnost: " + univerzita.vypocitejObecnyPrumerOboru(2));
                    break;
                case 8:
                    int pocetStudentuTelekomunikace = univerzita.pocetStudentuTelekomunikace();
                    int pocetStudentuKyberbezpecnost = univerzita.pocetStudentuKyberbezpecnost();
                    System.out.println("Počet studentů v oboru Telekomunikace: " + pocetStudentuTelekomunikace);
                    System.out.println("Počet studentů v oboru Kyberbezpečnost: " + pocetStudentuKyberbezpecnost);
                    break;
                
                case 9:
                    while (true) {
                        System.out.println("Zadej ID studenta, kterého chceš uložit do souboru:");
                        if (scanner.hasNextInt()) {
                            int idStudenta = scanner.nextInt();
                            univerzita.ulozStudentaDoSouboru(idStudenta);
                            break;
                        } else {
                            System.out.println("Chyba: Musíte zadat ID studenta (číslo).");
                        }
                    }
                    break;
                case 10:
                    univerzita.nactiStudentaZeSouboruUI();
                    break;
                case 11:
                    univerzita.ulozDoDatabaze();
                    konec = true;
                    break;
                default:
                    System.out.println("Neplatná volba.");
            }
        }
        scanner.close();
    }
}
