package org.example.lippudemang;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

// Uus UI'ga peaklass
public class RiikideMangFX extends Application {
    private ManguReziim reziim;

    private Mang mang; //Mängu loogika objekt
    private Kusimus praeguneKusimus; // Hetkel kuvatav küsimus
    private boolean kasVastatud; // Kontrollimaks, et ainult üks klaviatuuri või ekraanisündmus läheb kirja

    // UI komponendid
    private Label juhendiLabel;
    private Label küsimusLabel;
    private Label punktidLabel;
    private Label tagasisideLabel;

    private Button[] vastuseNupud; //Nupud vastusevariantidega
    private Button uusMängNupp;
    private Button režiimiValikNupp;


    @Override
    public void start(Stage lava) {
        // Loob uue mängu
        mang = new Mang();
        kasVastatud = false; //Hetkel veel pole küsimusele vastatud
        // Loob tekstiväljad
        juhendiLabel = new Label();
        küsimusLabel = new Label();
        punktidLabel = new Label("Punktid: 0");
        tagasisideLabel = new Label();
        // loob massiivi nelja nupuga
        vastuseNupud = new Button[4];

        uusMängNupp = new Button("Alusta uuesti");
        uusMängNupp.setVisible(false);
        uusMängNupp.setOnAction(e -> taaskäivitaMäng());

        režiimiValikNupp = new Button("Vali režiim");
        režiimiValikNupp.setVisible(false);
        režiimiValikNupp.setOnAction(e -> kuvaReziimiValik());

        // Paigutab nupud ruudustikuna
        GridPane nuppudePaneel = new GridPane();
        nuppudePaneel.setHgap(10);
        nuppudePaneel.setVgap(10);
        // Loob vastusenupud
        for (int i = 0; i < vastuseNupud.length; i++) {
            // Vastuse number
            final int vastus = i + 1;
            // Loob uue nupu
            vastuseNupud[i] = new Button();
            // Nupu laius on paindlik
            vastuseNupud[i].setMaxWidth(Double.MAX_VALUE);
            // Nupul vajutades kontrollitakse vastust
            vastuseNupud[i].setOnAction(e -> kontrolliVastus(vastus));
            // Lisab nupu gridpane'i
            nuppudePaneel.add(vastuseNupud[i], i % 2, i / 2);
        }
        // Paigutab elemendid vertikaalselt
        VBox juur = new VBox(15);
        HBox mänguAlustamiseNupud = new HBox(10);
        juur.setStyle("-fx-padding: 20;"); //ääris
        // Lisab elemendid aknasse
        juur.getChildren().addAll(
                new Label("Riikide mäng"),
                juhendiLabel,
                küsimusLabel,
                nuppudePaneel,
                punktidLabel,
                tagasisideLabel,
                mänguAlustamiseNupud
        );
        mänguAlustamiseNupud.getChildren().addAll(
                uusMängNupp,
                režiimiValikNupp
        );

        Scene stseen = new Scene(juur, 500, 350);

        // Klaviatuuriga vastamine nagu terminalis varem
        stseen.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DIGIT1 -> kontrolliVastus(1);
                case DIGIT2 -> kontrolliVastus(2);
                case DIGIT3 -> kontrolliVastus(3);
                case DIGIT4 -> kontrolliVastus(4);

                // Kui vajutatakse muid numbreid
                case DIGIT5, DIGIT6, DIGIT7,
                     DIGIT8, DIGIT9, DIGIT0 -> tagasisideLabel.setText("Palun vali number vahemikus 1–4.");
            }
        });

        lava.setTitle("Riikide mäng");
        lava.setScene(stseen);
        lava.show();

        kuvaReziimiValik();
    }

    // Kuvab mängurežiimi valiku
    private void kuvaReziimiValik() {

        // Lähtestab mängu andmed
        mang.lähtestaMäng();
        kasVastatud = false;
        praeguneKusimus = null;

        punktidLabel.setText("Punktid: 0");
        tagasisideLabel.setText("");

        küsimusLabel.setText("Vali mängurežiim:");
        juhendiLabel.setText("Kas soovid arvata pealinnu või lippe?");

        vastuseNupud[0].setText("1. Pealinnad");
        vastuseNupud[1].setText("2. Lipud");

        vastuseNupud[0].setDisable(false);
        vastuseNupud[1].setDisable(false);

        // Kuvab ainult kaks nuppu
        vastuseNupud[0].setVisible(true);
        vastuseNupud[1].setVisible(true);

        // Peidab alumised nupud 2x2 gridis
        vastuseNupud[2].setVisible(false);
        vastuseNupud[3].setVisible(false);

        // Käivitab pealinnade mängu
        vastuseNupud[0].setOnAction(e -> alustaMäng(ManguReziim.PEALINNAD));

        // Käivitab lippude mängu
        vastuseNupud[1].setOnAction(e -> alustaMäng(ManguReziim.LIPUD));

        // Peidab alumised juhtnupud
        uusMängNupp.setVisible(false);
        režiimiValikNupp.setVisible(false);
    }

    // Käivitab mängu valitud režiimis
    private void alustaMäng(ManguReziim valitudReziim) {

        // Kuvab kasutajale juhised
        juhendiLabel.setText("Vastata saab hiirega või klahvidega 1-4.");
        reziim = valitudReziim;

        mang.lähtestaMäng();
        // Vastusenupud
        for (int i = 0; i < vastuseNupud.length; i++) {

            final int vastus = i + 1;

            vastuseNupud[i].setVisible(true);
            vastuseNupud[i].setDisable(false);

            vastuseNupud[i].setOnAction(e -> kontrolliVastus(vastus));
        }

        punktidLabel.setText("Punktid: 0");
        tagasisideLabel.setText("Vali õige vastus.");

        kuvaUusKüsimus();
    }

    // Alustab uue mängu
    private void taaskäivitaMäng() {
        // Lähtestab mängu andmed
        mang.lähtestaMäng();
        kasVastatud = false;
        punktidLabel.setText("Punktid: 0");
        tagasisideLabel.setText("Vali õige vastus.");

        // Peidab alumised nupud
        uusMängNupp.setVisible(false);
        uusMängNupp.setDisable(false);
        režiimiValikNupp.setVisible(false);
        režiimiValikNupp.setDisable(false);

        // Kuvab vastusenupud
        for (Button nupp : vastuseNupud) {
            nupp.setDisable(false);
            nupp.setVisible(true);
        }

        kuvaUusKüsimus();
    }

    // Kuvab kasutajale küsimuse
    private void kuvaUusKüsimus() {
        // Võtab mängust järgmise küsimuse
        kasVastatud = false;
        praeguneKusimus = mang.järgmineKüsimus();
        // Juhul kui kõik küsimused on vastatud
        if (praeguneKusimus == null) {
            küsimusLabel.setText("Palju õnne, vastasid kõikidele küsimustele õigesti!");
            tagasisideLabel.setText("Mäng sai läbi. Kogusid kokku " + mang.getPunktid() + " punkti.");
            // Kutsub välja meetodi tulemuse salvestamiseks
            salvestaTulemus();
            // Näitab uue mängu nuppu
            uusMängNupp.setDisable(false);
            uusMängNupp.setVisible(true);
            //Näitab režiimivaliku nuppu
            režiimiValikNupp.setDisable(false);
            režiimiValikNupp.setVisible(true);
            // Keelab nupud, sest rohkem pole neid vaja
            for (Button nupp : vastuseNupud) {
                nupp.setVisible(false);
            }
            return;
        }
        //Kuvad küsimuse teksti olenevalt mängu režiimist
        if (reziim == ManguReziim.PEALINNAD) {
            küsimusLabel.setText(praeguneKusimus.getKüsimuseTekst());

        } else if (reziim == ManguReziim.LIPUD) {
            küsimusLabel.setText("Mis riigi lipuga on tegemist?");
        }
        //Kuvad vastusevariandid nuppudel
        for (int i = 0; i < vastuseNupud.length; i++) {
            vastuseNupud[i].setText((i + 1) + ". " + praeguneKusimus.getVariandid().get(i).getRiigiNimi()
            );
        }
        // Uuendab punkte
        punktidLabel.setText("Punktid: " + mang.getPunktid());
    }

    //Kontrollib kasutaja vastust
    private void kontrolliVastus(int vastus) {
        if (kasVastatud || praeguneKusimus == null) {
            return;
        }
        kasVastatud = true;

        boolean õige = mang.kontrolliVastus(praeguneKusimus, vastus);

        if (õige) {
            tagasisideLabel.setText("Õige!");
            punktidLabel.setText("Punktid: " + mang.getPunktid());
            // Kui oli õige, siis kuvab järgmise küsimuse
            kuvaUusKüsimus();
        } else {
            tagasisideLabel.setText(
                    "Vale! Õige vastus oli: "
                            + praeguneKusimus.getÕigePealinn().getRiigiNimi()
            );
            for (Button nupp : vastuseNupud) {
                nupp.setDisable(true);
            }
            // Kutsub välja meetodi tulemuse salvestamiseks
            salvestaTulemus();

            uusMängNupp.setVisible(true);
            režiimiValikNupp.setVisible(true);
        }
    }

    // Meetod tulemuse salvestamiseks
    private void salvestaTulemus() {
        String nimi = küsiNimi();

        if (nimi != null) {
            mang.salvestaPunktid(nimi);
        }
    }

    // Meetod kasutajalt tema nime küsimiseks
    private String küsiNimi() {
        TextInputDialog dialoog = new TextInputDialog();
        dialoog.setTitle("Tulemuse salvestamine");
        dialoog.setHeaderText("Sisesta oma nimi või initsiaalid");
        dialoog.setContentText("Nimi:");

        Optional<String> tulemus = dialoog.showAndWait();

        if (tulemus.isPresent() && !tulemus.get().isBlank()) {
            return tulemus.get().trim();
        }
        return null;
    }
}
