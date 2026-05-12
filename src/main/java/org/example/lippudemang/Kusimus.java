package org.example.lippudemang;
import java.util.ArrayList;

// Hoiab konkreetse küsimuse andmeid

public class Kusimus {
    private final Pealinn õigePealinn;
    private final ArrayList<Pealinn> variandid;
    private final int õigeVastus;

    // Konstruktor
    public Kusimus(Pealinn õigePealinn, ArrayList<Pealinn> variandid, int õigeVastus) {
        this.õigePealinn = õigePealinn;
        this.variandid = variandid;
        this.õigeVastus = õigeVastus;
    }

    // Getterid
    public Pealinn getÕigePealinn() {
        return õigePealinn;
    }

    public ArrayList<Pealinn> getVariandid() {
        return variandid;
    }

    // Tagastab küsimuse teksti
    public String getKüsimuseTekst() {
        return "Mis riigi pealinn on " + õigePealinn.getPealinn() + "?";
    }

    // Kontrollib, kas kasutaja antud vastus on õige
    public boolean onÕigeVastus(int kasutajaVastus) {
        return kasutajaVastus == õigeVastus;
    }
}
