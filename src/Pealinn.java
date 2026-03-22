/* Instances: riik, vihje, kontinent
Meetodid: konstruktor, getterid-setterid, toString()
 */
public class Pealinn {
    private String riigiNimi;
    private String pealinn;
    //private String pildiTee hiljem juurde + konstruktorisse ja getter/setter

    public Pealinn(String riigiNimi, String pealinn) {
        this.riigiNimi = riigiNimi;
        this.pealinn = pealinn;
    }

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
    public String toString() {
        return riigiNimi + " " + pealinn;
    }
}
