// Hoiab ühe riigi ja selle pealinna andmeid

public class Pealinn {
    private String riigiNimi;
    private String pealinn;
    //private String pildiTee hiljem juurde + konstruktorisse ja getter/setter

    //Konstruktor
    public Pealinn(String riigiNimi, String pealinn) {
        this.riigiNimi = riigiNimi;
        this.pealinn = pealinn;
    }

    //Getterid ja setterid
    public String getRiigiNimi() {
        return riigiNimi;
    }

    public String getPealinn() {
        return pealinn;
    }

    public void setRiigiNimi(String riigiNimi) {
        this.riigiNimi = riigiNimi;
    }

    public void setPealinn(String pealinn) {
        this.pealinn = pealinn;
    }

    // toString meetod
    public String toString() {
        return riigiNimi + " " + pealinn;
    }
}
