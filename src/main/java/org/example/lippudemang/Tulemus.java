package org.example.lippudemang;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tulemus {
    private final String nimi;
    private final int punktid;
    private final LocalDateTime aeg;
    private int sekundid;
    private static final DateTimeFormatter VORMISTUS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Konstant

    // Konstruktor
    public Tulemus(String nimi, int punktid, LocalDateTime aeg) {
        this.nimi = nimi;
        this.punktid = punktid;
        this.aeg = aeg;
        this.sekundid = sekundid;
    }
    // Getter
    public int getPunktid() {
        return punktid;
    }

    // Teisendab tulemuse failireaks
    public String tulemusFailireaks() {
        return aeg.format(VORMISTUS) + ";" + nimi + ";" + punktid + ";" + sekundid;
    }
    // Loob failireast Tulemuse objekti
    public static Tulemus failireastObjektiks(String rida) {
        String[] osad = rida.split(";");

        LocalDateTime aeg = LocalDateTime.parse(osad[0], VORMISTUS);
        String nimi = osad[1];
        int punktid = Integer.parseInt(osad[2]);
        int sekundid = Integer.parseInt(osad[3]);

        return new Tulemus(nimi, punktid, aeg, sekundid);
    }
    // Tagastab tulemuse tekstina
    @Override
    public String toString() {
        return nimi + " - " + punktid + " punkti (" + aeg + ")" + sekundid;
    }
}
