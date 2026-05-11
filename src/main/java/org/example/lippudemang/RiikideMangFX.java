package org.example.lippudemang;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

// Uus UI'ga peaklass
public class RiikideMangFX extends Application {

    private Mang mang; //Mängu loogika objekt
    private Kusimus praeguneKusimus; // Hetkel kuvatav küsimus
    // UI komponendid
    private Label küsimusLabel;
    private Label punktidLabel;
    private Label tagasisideLabel;
    private Button[] vastuseNupud; //Nupud vastusevariantidega
    private Button uusMängNupp;
    private boolean kasVastatud; // Kontrollimaks, et ainult üks klaviatuuri või ekraanisündmus läheb kirja

    @Override
    public void start(Stage lava) {
        // Loob uue mängu
        mang = new Mang();
        kasVastatud = false; //Hetkel veel pole küsimusele vastatud
        // Loob tekstiväljad
        küsimusLabel = new Label();
        punktidLabel = new Label("Punktid: 0");
        tagasisideLabel = new Label("Vali õige vastus.");
        // loob massiivi nelja nupuga
        vastuseNupud = new Button[4];
        uusMängNupp = new Button("Alusta uuesti");
        uusMängNupp.setVisible(false);
        uusMängNupp.setOnAction(e -> alustaUutMängu());
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
        juur.setStyle("-fx-padding: 20;"); //ääris
        // Lisab elemendid aknasse
        juur.getChildren().addAll(
                new Label("Riikide mäng"),
                küsimusLabel,
                nuppudePaneel,
                punktidLabel,
                tagasisideLabel,
                uusMängNupp
        );

        Scene stseen = new Scene(juur, 500, 350);

        // Klaviatuuriga vastamine nagu terminalis varem
        stseen.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DIGIT1 -> kontrolliVastus(1);
                case DIGIT2 -> kontrolliVastus(2);
                case DIGIT3 -> kontrolliVastus(3);
                case DIGIT4 -> kontrolliVastus(4);
            }
        });

        lava.setTitle("Riikide mäng");
        lava.setScene(stseen);
        lava.show();

        kuvaUusKüsimus();
    }

    private void alustaUutMängu() {
        mang.alustaUuesti();
        kasVastatud = false;
        punktidLabel.setText("Punktid: 0");
        tagasisideLabel.setText("Vali õige vastus.");
        uusMängNupp.setVisible(false);
        uusMängNupp.setDisable(false);

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
            // Küsib kasutajalt nime tulemuste salvestamiseks
            String nimi = küsiNimi();

            if (nimi != null) {
                mang.salvestaPunktid(nimi);
            }
            // Näitab uue mängu nuppu
            uusMängNupp.setDisable(false);
            uusMängNupp.setVisible(true);
            // Keelab nupud, sest rohkem pole neid vaja
            for (Button nupp : vastuseNupud) {
                nupp.setVisible(false);
            }
            return;
        }
        //Kuvad küsimuse teksti
        küsimusLabel.setText(praeguneKusimus.getKüsimuseTekst());
        //Kuvad vastusevariandid nuppudel
        for (int i = 0; i < vastuseNupud.length; i++) {
            vastuseNupud[i].setText(
                    praeguneKusimus.getVariandid().get(i).getRiigiNimi()
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
                            + ". Punktid: " + mang.getPunktid()
            );
            for (Button nupp : vastuseNupud) {
                nupp.setDisable(true);
            }
            // Küsib kasutajalt nime punktide salvestamiseks
            String nimi = küsiNimi();

            if (nimi != null) {
                mang.salvestaPunktid(nimi);
            }
            uusMängNupp.setVisible(true);
        }
    }
    private String küsiNimi() {
        TextInputDialog dialoog = new TextInputDialog();
        dialoog.setTitle("Sisesta oma nimi või initsiaalid tulemuse salvestamiseks.");
        dialoog.setContentText("Nimi:");

        Optional<String> tulemus = dialoog.showAndWait();

        if (tulemus.isPresent() && !tulemus.get().isBlank()) {
            return tulemus.get();
        } return null;
    }
}
