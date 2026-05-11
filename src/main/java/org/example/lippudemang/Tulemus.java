package org.example.lippudemang;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tulemus {
    private String nimi;
    private int punktid;
    private LocalDateTime aeg;

    public Tulemus(String nimi, int punktid, LocalDateTime aeg) {
        this.nimi = nimi;
        this.punktid = punktid;
        this.aeg = aeg;
    }

    public String getNimi() {
        return nimi;
    }

    public int getPunktid() {
        return punktid;
    }

    public LocalDateTime getAeg() {
        return aeg;
    }
    public String tulemusFailireaks() {
        DateTimeFormatter vormistus = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return aeg.format(vormistus) + ";" + nimi + ";" + punktid;
    }
    public static Tulemus failireastObjektiks(String rida) {
        String[] osad = rida.split(";");

        DateTimeFormatter vorming = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime aeg = LocalDateTime.parse(osad[0], vorming);
        String nimi = osad[1];
        int punktid = Integer.parseInt(osad[2]);

        return new Tulemus(nimi, punktid, aeg);
    }
    @Override
    public String toString() {
        return nimi + " - " + punktid + " punkti (" + aeg + ")";
    }
}
