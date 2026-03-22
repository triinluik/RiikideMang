import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/* Mängu loogika
alustamang()
esitaKusimus()
kontrolliVastus()
kuvaTulemus()
 */
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
    }

    public void alustaMäng() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Tere tulemast pealinnade äraarvamisemängu!🔥");
        System.out.println("Sulle näidatakse pealinna nime ja sa pead ära arvama, mis riigi pealinnaga on tegemist.");
        System.out.println();

        for (int i = 1; i <= pealinnad.size(); i++) {
            System.out.println(i + ". küsimus:");
            esitaKüsimus(sc);
            System.out.println();
        }
    }

    private void esitaKüsimus(Scanner sc) {
        Pealinn õigePealinn = valiJuhuslikPealinn();
        ArrayList<Pealinn> variandid = looVariandid(õigePealinn);

        System.out.println("Mis riigi pealinn on " + õigePealinn.getPealinn() + "?");
        int õigeVastus = -1;
        for (int i = 0; i < variandid.size(); i++) {
            System.out.println((i + 1) + ". " + variandid.get(i).getRiigiNimi());
            if (variandid.get(i).getRiigiNimi().equals(õigePealinn.getRiigiNimi())) {
                õigeVastus = i + 1;
            }
        }
        System.out.println("Sisesta vastuse number: ");
        int vastus = sc.nextInt();

        if (vastus == õigeVastus) {
            System.out.println("Õige!");
            punktid++;
        } else {
            System.out.println("Vale! Õige vastus on hoopis " + õigePealinn.getRiigiNimi() + "!");
        }
    }

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

    private Pealinn valiJuhuslikPealinn() {
        int indeks = random.nextInt(pealinnad.size());
        return pealinnad.get(indeks);
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
