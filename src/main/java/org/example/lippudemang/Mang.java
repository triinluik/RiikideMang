package org.example.lippudemang;

// Mängu loogika

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Mang {
    private ArrayList<Pealinn> kõikPealinnad; //Kõik pealinnad
    private ArrayList<Pealinn> küsimataPealinnad; // Veel küsimata pealinnad
    private int punktid;
    private Random random;

    public Mang() {
        kõikPealinnad = new ArrayList<>();
        küsimataPealinnad = new ArrayList<>();
        random = new Random();

        punktid = 0;
        lisaPealinnad();
        küsimataPealinnad.addAll(kõikPealinnad);
    }

    // Abimeetod, mis loeb pealinnad txt failist ja lisab need järjendisse
    private void lisaPealinnad() {
        try (Scanner sc = new Scanner(new File("Pealinnad.txt"))) {

            while (sc.hasNextLine()) {
                String rida = sc.nextLine();

                String[] osad = rida.split(";");

                String riik = osad[0];
                String pealinn = osad[1];

                kõikPealinnad.add(new Pealinn(riik, pealinn));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Viga! Sellist faili ei leitud!");
        }
    }

    // Käivitab mängu
    public void alustaMäng() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Tere tulemast pealinnade äraarvamisemängu!🔥");
        System.out.println("Sulle näidatakse pealinna nime ja sa pead ära arvama, mis riigi pealinnaga on tegemist.");
        System.out.println();
        // Välimine tsükkel võimaldab mängu vale vastuse korral uuesti alustada
        boolean mängKäib = true;

        while (mängKäib) {
            for (int i = 1; i <= 15; i++) {
                System.out.println(i + ". küsimus:");
                boolean õigeVastus = esitaKüsimus(sc);
                System.out.println();

                if (!õigeVastus) {
                    System.out.println("Vastasid valesti. Alustame mängu uuesti!");
                    alustaUuesti();
                    // Kui vastus on vale, siis katkestab küsimuste küsimuse ja alustab uuesti
                    break;
                }
            }
            if (küsimusedOtsas()) {
                System.out.println("Vastasid kõikidele küsimustele õigesti ja kogusid kokku " + punktid + " punkti!\uD83C\uDF89");
                System.out.println();
                // Kui kõikidele küsimustele on vastatud, siis paneb programmi kinni.
                mängKäib = false;
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

        int vastus;
        while (true) {
            if (!sc.hasNextInt()) {
                System.out.println("Palun sisesta number vahemikus 1-4.");
                sc.next();
                continue;
            }

            vastus = sc.nextInt();

            if (vastus >= 1 && vastus <= 4) {
                break;
            }
            System.out.println("Palun sisesta number vahemikus 1-4.");
        }

        if (kontrolliVastus(kusimus, vastus)) {
            System.out.println("Õige!");
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
        ArrayList<Pealinn> pealinnKoopia = new ArrayList<>(kõikPealinnad);
        pealinnKoopia.remove(õigePealinn);
        Collections.shuffle(pealinnKoopia);

        ArrayList<Pealinn> variandid = new ArrayList<>();
        variandid.add(õigePealinn);

        int i = 0;
        while (variandid.size() < 4) {
           variandid.add(pealinnKoopia.get(i));
            i++;
        }
        Collections.shuffle(variandid);
        return variandid;
    }

    // Valib juhusliku pealinna
    private Pealinn valiJuhuslikPealinn() {
        int indeks = random.nextInt(küsimataPealinnad.size());
        return küsimataPealinnad.remove(indeks);
    }
    // Kontrollib, kas pealinnad järjendis on veel pealinnu
    public boolean küsimusedOtsas() {
        return küsimataPealinnad.isEmpty();
    }

    // Tagastab uue küsimuse objekti
    public Kusimus järgmineKüsimus() {
        if (küsimusedOtsas()) {
            return null;
        }
        return looKüsimus();
    }

    // Kontrollib kasutaja vastust
    public boolean kontrolliVastus(Kusimus kusimus, int vastus) {
        if (kusimus.onÕigeVastus(vastus)) {
            punktid++;
            return true;
        }
        return false;
    }

    // Alustab mängu vale vastuse korral uuesti
    public void alustaUuesti() {
        punktid = 0;
        küsimataPealinnad.clear();
        küsimataPealinnad.addAll(kõikPealinnad);
    }

    public int getPunktid() {
        return punktid;
    }

    public void setPunktid(int punktid) {
        this.punktid = punktid;
    }

    public ArrayList<Pealinn> getKõikPealinnad() {
        return kõikPealinnad;
    }

    public void setKõikPealinnad(ArrayList<Pealinn> kõikPealinnad) {
        this.kõikPealinnad = kõikPealinnad;
    }
}
