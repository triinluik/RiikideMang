package org.example.lippudemang;

// Mängu loogika

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class Mang {
    private final ArrayList<Riik> kõikRiigid; //Kõik pealinnad
    private final ArrayList<Riik> küsimataRiigid; // Veel küsimata pealinnad
    private int punktid;
    private final Random random;
    private static final String TULEMUSTE_FAIL = "tulemused.txt"; //Konstant
    private int sekundid;

    public Mang() {
        kõikRiigid = new ArrayList<>();
        küsimataRiigid = new ArrayList<>();
        random = new Random();

        punktid = 0;
        lisaPealinnad();
        küsimataRiigid.addAll(kõikRiigid);
    }
    // Getter
    public int getPunktid() {
        return punktid;
    }

    // Abimeetod, mis loeb pealinnad txt failist ja lisab need järjendisse
    private void lisaPealinnad() {
        try (Scanner sc = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream("/Pealinnad.txt")))) {

            while (sc.hasNextLine()) {
                String rida = sc.nextLine();

                String[] osad = rida.split(";");

                if (osad.length != 3) {
                    continue;
                }
                String riik = osad[0].trim();
                String pealinn = osad[1].trim();
                String riigiKood = osad[2].trim();

                kõikRiigid.add(new Riik(riik, pealinn, riigiKood));
            }

        } catch (Exception e) {
            System.out.println("Viga! Sellist faili ei leitud!");
        }
    }

    //Loob uue küsimuse isendi
    private Kusimus looKüsimus() {
        Riik õigeRiik = valiJuhuslikRiik();
        ArrayList<Riik> variandid = looVariandid(õigeRiik);
        int õigeVastus = -1;
        for (int i = 0; i < variandid.size(); i++) {
            if (variandid.get(i).getRiigiNimi().equals(õigeRiik.getRiigiNimi())) {
                õigeVastus = i + 1;
            }
        }
        return new Kusimus(õigeRiik, variandid, õigeVastus);
    }

    //Genereerib vastusevariandid
    private ArrayList<Riik> looVariandid(Riik õigeRiik) {
        ArrayList<Riik> riikKoopia = new ArrayList<>(kõikRiigid);
        riikKoopia.remove(õigeRiik);
        Collections.shuffle(riikKoopia);

        ArrayList<Riik> variandid = new ArrayList<>();
        variandid.add(õigeRiik);

        int i = 0;
        while (variandid.size() < 4) {
            variandid.add(riikKoopia.get(i));
            i++;
        }
        Collections.shuffle(variandid);
        return variandid;
    }

    // Valib juhusliku pealinna
    private Riik valiJuhuslikRiik() {
        int indeks = random.nextInt(küsimataRiigid.size());
        return küsimataRiigid.remove(indeks);
    }

    // Kontrollib, kas pealinnad järjendis on veel pealinnu
    public boolean küsimusedOtsas() {
        return küsimataRiigid.isEmpty();
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
        küsimataRiigid.clear();
        küsimataRiigid.addAll(kõikRiigid);
    }
    //Salvestab aja
     public void salvestaAeg(int sekundid) {
        this.sekundid=sekundid;
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
            tulemused.add(new Tulemus(nimi, punktid, LocalDateTime.now(), sekundid));
            // Sorteerib punktide järgi kahanevalt
            tulemused.sort(Comparator.comparingInt(Tulemus::getPunktid).reversed());
            // Kirjutab tulemused faili
            try (FileWriter fw = new FileWriter(TULEMUSTE_FAIL)) {
                for (Tulemus tulemus : tulemused) {
                    fw.write(tulemus.tulemusFailireaks() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Tulemuse salvestamine ebaõnnestus!" + e.getMessage());
        }
    }
}
