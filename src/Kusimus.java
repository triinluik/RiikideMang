import java.util.ArrayList;

// Hoiab konkreetse küsimuse andmeid

public class Kusimus {
    private Pealinn õigePealinn;
    private ArrayList<Pealinn> variandid;
    private int õigeVastus;

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

    public int getÕigeVastus() {
        return õigeVastus;
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
