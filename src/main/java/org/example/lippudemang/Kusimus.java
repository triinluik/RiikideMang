package org.example.lippudemang;
import java.util.ArrayList;

// Hoiab konkreetse küsimuse andmeid

public class Kusimus {
    private final Riik õigeRiik;
    private final ArrayList<Riik> variandid;
    private final int õigeVastus;

    // Konstruktor
    public Kusimus(Riik õigeRiik, ArrayList<Riik> variandid, int õigeVastus) {
        this.õigeRiik = õigeRiik;
        this.variandid = variandid;
        this.õigeVastus = õigeVastus;
    }

    // Getterid
    public Riik getÕigePealinn() {
        return õigeRiik;
    }

    public ArrayList<Riik> getVariandid() {
        return variandid;
    }

    // Tagastab küsimuse teksti
    public String getKüsimuseTekst() {
        return "Mis riigi pealinn on " + õigeRiik.getPealinn() + "?";
    }

    // Kontrollib, kas kasutaja antud vastus on õige
    public boolean onÕigeVastus(int kasutajaVastus) {
        return kasutajaVastus == õigeVastus;
    }
}
