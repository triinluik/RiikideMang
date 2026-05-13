package org.example.lippudemang;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private Timeline ajavõtt;
    private int sekundid = 0;
    
    @Override
    public void start(Stage lava) {
        // Loob uue mängu
        mang = new Mang();
        kasVastatud = false; //Hetkel veel pole küsimusele vastatud
        // Loob tekstiväljad
        juhendiLabel = new Label();
        küsimusLabel = new Label();
        punktidLabel = new Label("Punktid: 0");
        Label aeg = new Label("0");
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
        // Lisab aja tähise ekraanile
        ajavõtt = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            sekundid++;
            aeg.setText("Aeg: " + sekundid);
        })
        );
        ajavõtt.setCycleCount(Timeline.INDEFINITE);
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
                mänguAlustamiseNupud,
                aeg
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
        kuvaJuhtnupud(false);
    }

    // Käivitab mängu valitud režiimis
    private void alustaMäng(ManguReziim valitudReziim) {

        // Kuvab kasutajale juhised
        juhendiLabel.setText("Vastata saab hiirega või klahvidega 1-4.");
        reziim = valitudReziim;

        mang.lähtestaMäng();
        sekundid = 0;
        ajavõtt.play();

        kuvaVastuseNupud(true);
        keelaVastuseNupud(false);

        // Vastusenupud
        for (int i = 0; i < vastuseNupud.length; i++) {
            final int vastus = i + 1;
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
        sekundid = 0;
        ajavõtt.play();

        // Peidab juhtnupud
        kuvaJuhtnupud(false);
        kuvaVastuseNupud(true);
        keelaVastuseNupud(false);

        kuvaUusKüsimus();
    }

    // Kuvab kasutajale küsimuse
    private void kuvaUusKüsimus() {
        // Võtab mängust järgmise küsimuse
        kasVastatud = false;
        praeguneKusimus = mang.järgmineKüsimus();
        // Juhul kui kõik küsimused on vastatud
        if (praeguneKusimus == null) {
            ajavõtt.stop();
            küsimusLabel.setText("Palju õnne, vastasid kõikidele küsimustele õigesti!");
            tagasisideLabel.setText("Mäng sai läbi. Kogusid kokku " + mang.getPunktid() + " punkti.");
            // Kutsub välja meetodi tulemuse salvestamiseks
            salvestaTulemus();
            // Näitab juhtnuppe ja peidab vastusenupud
            kuvaJuhtnupud(true);
            kuvaVastuseNupud(false);
            
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
            ajavõtt.stop();
            tagasisideLabel.setText(
                    "Vale! Õige vastus oli: "
                            + praeguneKusimus.getÕigeRiik().getRiigiNimi()
            );

            keelaVastuseNupud(true);

            // Kutsub välja meetodi tulemuse salvestamiseks
            salvestaTulemus();

            kuvaJuhtnupud(true);
        }
    }

    // Meetod tulemuse salvestamiseks
    private void salvestaTulemus() {
        String nimi = küsiNimi();
        int kestus = sekundid;
        if (nimi != null) {
            mang.salvestaAeg(kestus);
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

    // Kuvab või peidab kõik vastusenupud
    private void kuvaVastuseNupud(boolean kuva) {
        for (Button nupp : vastuseNupud) {
            nupp.setVisible(kuva);
        }
    }

    // Lubab või keelab kõik vastusenupud
    private void keelaVastuseNupud(boolean keela) {
        for (Button nupp : vastuseNupud) {
            nupp.setDisable(keela);
        }
    }

    // Kuvab või peidab uue mängu ja režiimivaliku nupu
    private void kuvaJuhtnupud(boolean kuva) {
        uusMängNupp.setVisible(kuva);
        režiimiValikNupp.setVisible(kuva);
    }
}