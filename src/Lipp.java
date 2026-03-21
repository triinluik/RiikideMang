/* Instances: riik, vihje, kontinent
Meetodid: konstruktor, getterid-setterid, toString()
 */
public class Lipp {
    private String riigiNimi;
    private String emoji;
    //private String pildiTee hiljem juurde + konstruktorisse ja getter/setter

    public Lipp(String riigiNimi, String emoji) {
        this.riigiNimi = riigiNimi;
        this.emoji = emoji;
    }

    public String getRiigiNimi() {
        return riigiNimi;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setRiigiNimi(String riigiNimi) {
        this.riigiNimi = riigiNimi;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
    public String toString() {
        return riigiNimi + " " + emoji;
    }
}
