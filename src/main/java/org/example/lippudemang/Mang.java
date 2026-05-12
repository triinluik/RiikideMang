package org.example.lippudemang;

// Mängu loogika

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class Mang {
    private ArrayList<Pealinn> kõikPealinnad; //Kõik pealinnad
    private final ArrayList<Pealinn> küsimataPealinnad; // Veel küsimata pealinnad
    private int punktid;
    private final Random random;
    private static final String TULEMUSTE_FAIL = "tulemused.txt"; //Konstant

    public Mang() {
        kõikPealinnad = new ArrayList<>();
        küsimataPealinnad = new ArrayList<>();
        random = new Random();

        punktid = 0;
        lisaPealinnad();
        küsimataPealinnad.addAll(kõikPealinnad);
    }
    // Getterid ja setterid
    public int getPunktid() {
        return punktid;
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
    public void lähtestaMäng() {
        punktid = 0;
        küsimataPealinnad.clear();
        küsimataPealinnad.addAll(kõikPealinnad);
    }

    // Salvestab punktid faili
    public void salvestaPunktid(String nimi) {
        try {
            // Hoiab kõiki faili salvestatud tulemusi
            List<Tulemus> tulemused = new ArrayList<>();
            // Kontrollib, kas tulemustefail on olemas
            if (Files.exists(Paths.get(TULEMUSTE_FAIL))) {
                // Loeb kõik read
                List<String> read = Files.readAllLines(Paths.get(TULEMUSTE_FAIL));
               // Teeb ridadest Tulemus objektid
                for (String rida : read) {
                    if (!rida.isBlank()) {
                        tulemused.add(Tulemus.failireastObjektiks(rida));
                    }
                }
            }
            //Lisab uue tulemuse
            tulemused.add(new Tulemus(nimi, punktid, LocalDateTime.now()));
            // Sorteerib punktide järgi kahanevalt
            tulemused.sort(Comparator.comparingInt(Tulemus::getPunktid).reversed());
            // Kirjutab tulemused faili
            try (FileWriter fw = new FileWriter(TULEMUSTE_FAIL)) {
                for (Tulemus tulemus : tulemused) {
                    fw.write(tulemus.tulemusFailireaks() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Tulemuse salvestamine ebaõnnestus!");
        }
    }
}
