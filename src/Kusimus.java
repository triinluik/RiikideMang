import java.util.ArrayList;

/* Hoiab konkreetse küsimuse andmeid
 */
public class Kusimus {
    private Pealinn õigePealinn;
    private ArrayList<Pealinn> variandid;
    private int õigeVastus;

    public Kusimus(Pealinn õigePealinn, ArrayList<Pealinn> variandid, int õigeVastus) {
        this.õigePealinn = õigePealinn;
        this.variandid = variandid;
        this.õigeVastus = õigeVastus;
    }

    public Pealinn getÕigePealinn() {
        return õigePealinn;
    }

    public ArrayList<Pealinn> getVariandid() {
        return variandid;
    }

    public int getÕigeVastus() {
        return õigeVastus;
    }
    public String getKüsimuseTekst() {
        return "Mis riigi pealinn on " + õigePealinn.getPealinn() + "?";
    }
    public boolean onÕigeVastus(int kasutajaVastus) {
        return kasutajaVastus == õigeVastus;
    }
}
