package org.example.lippudemang;
// Hoiab ühe riigi ja selle pealinna andmeid

import javafx.scene.image.Image;

import java.io.InputStream;

public class Riik {
    private final String riigiNimi;
    private final String pealinn;
    private final String riigiKood;

    //Konstruktor
    public Riik(String riigiNimi, String pealinn, String riigiKood) {
        this.riigiNimi = riigiNimi;
        this.pealinn = pealinn;
        this.riigiKood = riigiKood;
    }

    //Getterid
    public String getRiigiNimi() {
        return riigiNimi;
    }

    public String getPealinn() {
        return pealinn;
    }

    public String getRiigiKood() {
        return riigiKood;
    }
    // Tagastab riigi lipu
    public Image getLipp() {
        String tee = "/EUlipud/" + riigiKood + ".png";
        InputStream sisend = getClass().getResourceAsStream(tee);

        if (sisend == null) {
            throw new IllegalArgumentException("Lippu ei leitud: " + tee);
        }
        return new Image(sisend);
    }
    // toString meetod
    public String toString() {
        return riigiNimi + " " + pealinn;
    }
}
