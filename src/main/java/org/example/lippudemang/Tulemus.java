package org.example.lippudemang;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tulemus {
    private String nimi;
    private int punktid;
    private LocalDateTime aeg;
    private static final DateTimeFormatter VORMISTUS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Konstant

    // Konstruktor
    public Tulemus(String nimi, int punktid, LocalDateTime aeg) {
        this.nimi = nimi;
        this.punktid = punktid;
        this.aeg = aeg;
    }
    // Getterid
    public String getNimi() {
        return nimi;
    }

    public int getPunktid() {
        return punktid;
    }

    public LocalDateTime getAeg() {
        return aeg;
    }
    // Teisendab tulemuse failireaks
    public String tulemusFailireaks() {
        return aeg.format(VORMISTUS) + ";" + nimi + ";" + punktid;
    }
    // Loob failireast Tulemuse objekti
    public static Tulemus failireastObjektiks(String rida) {
        String[] osad = rida.split(";");

        LocalDateTime aeg = LocalDateTime.parse(osad[0], VORMISTUS);
        String nimi = osad[1];
        int punktid = Integer.parseInt(osad[2]);

        return new Tulemus(nimi, punktid, aeg);
    }
    // Tagastab tulemuse tekstina
    @Override
    public String toString() {
        return nimi + " - " + punktid + " punkti (" + aeg + ")";
    }
}
