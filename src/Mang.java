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
    private ArrayList<Lipp> lipud;
    private int punktid;
    private Random random;

    public Mang() {
        lipud = new ArrayList<>();
        random = new Random();

        punktid = 0;
        lisaLipud();
    }

    private void lisaLipud() {
        lipud.add(new Lipp("Eesti", "\uD83C\uDDEA\uD83C\uDDEA"));
        lipud.add(new Lipp("Kreeka", "\uD83C\uDDEC\uD83C\uDDF7"));
        lipud.add(new Lipp("Ühendkuningriik", "\uD83C\uDDEC\uD83C\uDDE7"));
        lipud.add(new Lipp("Prantsusmaa", "\uD83C\uDDEB\uD83C\uDDF7"));
        lipud.add(new Lipp("Saksamaa", "\uD83C\uDDE9\uD83C\uDDEA"));
        lipud.add(new Lipp("Holland", "\uD83C\uDDF3\uD83C\uDDF1"));
        lipud.add(new Lipp("Taani", "\uD83C\uDDE9\uD83C\uDDF0"));
        lipud.add(new Lipp("Norra", "\uD83C\uDDF3\uD83C\uDDF4"));
        lipud.add(new Lipp("Rootsi", "\uD83C\uDDF8\uD83C\uDDEA"));
        lipud.add(new Lipp("Soome", "\uD83C\uDDEB\uD83C\uDDEE"));
        lipud.add(new Lipp("Läti", "\uD83C\uDDF1\uD83C\uDDFB"));
        lipud.add(new Lipp("Leedu", "\uD83C\uDDF1\uD83C\uDDF9"));
        lipud.add(new Lipp("Poola", "\uD83C\uDDF5\uD83C\uDDF1"));
        lipud.add(new Lipp("Belgia", "\uD83C\uDDE7\uD83C\uDDEA"));
        lipud.add(new Lipp("Hispaania", "\uD83C\uDDEA\uD83C\uDDF8"));
        lipud.add(new Lipp("Portugal", "\uD83C\uDDF5\uD83C\uDDF9"));
    }

    public void alustaMäng() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Tere tulemast lippude äraarvamisemängu!🔥");
        System.out.println("Sulle näidatakse lippu ja sa pead ära arvama, mis riigi lipuga on tegemist.");
        System.out.println();

        for (int i = 1; i <= lipud.size(); i++) {
            System.out.println(i + ". küsimus:");
            esitaKüsimus(sc);
            System.out.println();
        }
    }

    private void esitaKüsimus(Scanner sc) {
        Lipp õigeLipp = valiJuhuslikLipp();
        ArrayList<Lipp> variandid = looVariandid(õigeLipp);

        System.out.println("Mis riigi lipp on " + õigeLipp.getEmoji() + "?");
        int õigeVastus = -1;
        for (int i = 0; i < variandid.size(); i++) {
            System.out.println((i + 1) + ". " + variandid.get(i).getRiigiNimi());
            if (variandid.get(i).getRiigiNimi().equals(õigeLipp.getRiigiNimi())) {
                õigeVastus = i + 1;
            }
        }
        System.out.println("Sisesta vastuse number: ");
        int vastus = sc.nextInt();

        if (vastus == õigeVastus) {
            System.out.println("Õige!");
            punktid++;
        } else {
            System.out.println("Vale! Õige vastus on hoopis " + õigeLipp.getRiigiNimi() + "!");
        }
    }

    private ArrayList<Lipp> looVariandid(Lipp õigeLipp) {
        ArrayList<Lipp> lippudeKoopia = new ArrayList<>(lipud);
        Collections.shuffle(lippudeKoopia);

        ArrayList<Lipp> variandid = new ArrayList<>();
        variandid.add(õigeLipp);

        int i = 0;
        while (variandid.size() < 4) {
            Lipp lipp = lippudeKoopia.get(i);
            if (!variandid.contains(lipp)) {
                variandid.add(lipp);
            }
            i++;
        }
        Collections.shuffle(variandid);
        return variandid;
    }

    private Lipp valiJuhuslikLipp() {
        int indeks = random.nextInt(lipud.size());
        return lipud.get(indeks);
    }
    public int getPunktid() {
        return punktid;
    }
    public void setPunktid(int punktid) {
        this.punktid = punktid;
    }
    public ArrayList<Lipp> getLipud() {
        return lipud;
    }
    public void setLipud(ArrayList<Lipp> lipud) {
        this.lipud = lipud;
    }

}
