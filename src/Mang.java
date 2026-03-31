// Mängu loogika

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Mang {
    private ArrayList<Pealinn> pealinnad;
    private int punktid;
    private Random random;

    public Mang() {
        pealinnad = new ArrayList<>();
        random = new Random();

        punktid = 0;
        lisaPealinnad();
    }

    // Abimeetod, mis lisab pealinnad järjendisse
    private void lisaPealinnad() {
        pealinnad.add(new Pealinn("Eesti", "Tallinn"));
        pealinnad.add(new Pealinn("Kreeka", "Ateena"));
        pealinnad.add(new Pealinn("Ühendkuningriik", "London"));
        pealinnad.add(new Pealinn("Prantsusmaa", "Pariis"));
        pealinnad.add(new Pealinn("Saksamaa", "Berliin"));
        pealinnad.add(new Pealinn("Holland", "Amsterdam"));
        pealinnad.add(new Pealinn("Taani", "Kopenhaagen"));
        pealinnad.add(new Pealinn("Norra", "Oslo"));
        pealinnad.add(new Pealinn("Rootsi", "Stockholm"));
        pealinnad.add(new Pealinn("Soome", "Helsinki"));
        pealinnad.add(new Pealinn("Läti", "Riia"));
        pealinnad.add(new Pealinn("Leedu", "Vilnius"));
        pealinnad.add(new Pealinn("Poola", "Varssavi"));
        pealinnad.add(new Pealinn("Belgia", "Brüssel"));
        pealinnad.add(new Pealinn("Hispaania", "Madrid"));
        pealinnad.add(new Pealinn("Portugal", "Lissabon"));
        pealinnad.add(new Pealinn("Itaalia","Rooma"));
        pealinnad.add(new Pealinn("Tšehhi","Praha"));
        pealinnad.add(new Pealinn("Austria","Viin"));
        pealinnad.add(new Pealinn("Iirimaa", "Dublin"));
        pealinnad.add(new Pealinn("Rumeenia", "Bukarest"));
        pealinnad.add(new Pealinn("Bulgaaria", "Sofia"));
        pealinnad.add(new Pealinn("Island", "Reykjavík"));
        pealinnad.add(new Pealinn("Slovakkia", "Bratislava"));
    }

    // Käivitab mängu
    public void alustaMäng() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Tere tulemast pealinnade äraarvamisemängu!🔥");
        System.out.println("Sulle näidatakse pealinna nime ja sa pead ära arvama, mis riigi pealinnaga on tegemist.");
        System.out.println();
        while (true) {
            for (int i = 1; i <= 15; i++) {
                System.out.println(i + ". küsimus:");
                boolean õigeVastus = esitaKüsimus(sc);
                System.out.println();

                if (!õigeVastus) {
                    System.out.println("Vastasid valesti. Alustame mängu uuesti!");
                    punktid = 0;
                    System.out.println();
                    pealinnad.clear();
                    lisaPealinnad();
                    break;
                }
            }
            if (punktid == 15) {
                System.out.println("Vastasid kõikidele küsimustele õigesti ja kogusid kokku " + punktid + " punkti!\uD83C\uDF89");
                System.out.println();
                break;
            }
        }
    }

    // Kuvab küsimuse ja loeb kasutaja antud vastuse
    private boolean esitaKüsimus(Scanner sc) {
        Kusimus kusimus = looKüsimus();

        System.out.println(kusimus.getKüsimuseTekst());

        for (int i = 0; i < kusimus.getVariandid().size(); i++) {
            System.out.println((i + 1) + ". " + kusimus.getVariandid().get(i).getRiigiNimi());
        }
        System.out.println("Sisesta vastuse number: ");
        int vastus = sc.nextInt();
        if (kusimus.onÕigeVastus(vastus)) {
            System.out.println("Õige!");
            punktid++;
            return true;
        } else {
            System.out.println("Vale! Õige vastus on hoopis " + kusimus.getÕigePealinn().getRiigiNimi());
            System.out.println("Kogusid kokku " + punktid + " punkti!\uD83C\uDF89");
            return false;
        }
    }

    //Loob uue küsimuse isendi
    private Kusimus looKüsimus() {
        Pealinn õigePealinn = valiJuhuslikPealinn();
        ArrayList<Pealinn> variandid = looVariandid(õigePealinn);
        int õigeVastus = -1;
        for (int i = 0; i < variandid.size(); i++) {
            if (variandid.get(i).getRiigiNimi().equals(õigePealinn.getRiigiNimi())) {
                õigeVastus = i + 1;
            }
        }
        return new Kusimus(õigePealinn, variandid, õigeVastus);
    }

    //Genereerib vastusevariandid
    private ArrayList<Pealinn> looVariandid(Pealinn õigePealinn) {
        ArrayList<Pealinn> pealinnKoopia = new ArrayList<>(pealinnad);
        Collections.shuffle(pealinnKoopia);

        ArrayList<Pealinn> variandid = new ArrayList<>();
        variandid.add(õigePealinn);

        int i = 0;
        while (variandid.size() < 4) {
            Pealinn pealinn = pealinnKoopia.get(i);
            if (!variandid.contains(pealinn)) {
                variandid.add(pealinn);
            }
            i++;
        }
        Collections.shuffle(variandid);
        return variandid;
    }

    // Valib juhusliku pealinna
    private Pealinn valiJuhuslikPealinn() {
        int indeks = random.nextInt(pealinnad.size());
        return pealinnad.remove(indeks);
    }

    public int getPunktid() {
        return punktid;
    }

    public void setPunktid(int punktid) {
        this.punktid = punktid;
    }

    public ArrayList<Pealinn> getPealinnad() {
        return pealinnad;
    }

    public void setPealinnad(ArrayList<Pealinn> pealinnad) {
        this.pealinnad = pealinnad;
    }

}
