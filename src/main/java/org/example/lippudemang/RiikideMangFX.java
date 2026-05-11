package org.example.lippudemang;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Uus UI'ga peaklass
public class RiikideMangFX extends Application {

    private Mang mang;
    private Kusimus aktiivneKusimus;

    private Label küsimusLabel;
    private Label punktidLabel;
    private Label tagasisideLabel;
    private Button[] vastuseNupud;

    @Override
    public void start(Stage lava) {
        mang = new Mang();

        küsimusLabel = new Label();
        punktidLabel = new Label("Punktid: 0");
        tagasisideLabel = new Label("Vali õige vastus.");

        vastuseNupud = new Button[4];

        GridPane nuppudePaneel = new GridPane();
        nuppudePaneel.setHgap(10);
        nuppudePaneel.setVgap(10);

        for (int i = 0; i < vastuseNupud.length; i++) {
            final int vastus = i + 1;

            vastuseNupud[i] = new Button();
            vastuseNupud[i].setMaxWidth(Double.MAX_VALUE);
            vastuseNupud[i].setOnAction(e -> kontrolliVastus(vastus));

            nuppudePaneel.add(vastuseNupud[i], i % 2, i / 2);
        }

        VBox juur = new VBox(15);
        juur.setStyle("-fx-padding: 20;");
        juur.getChildren().addAll(
                new Label("Riikide mäng"),
                küsimusLabel,
                nuppudePaneel,
                punktidLabel,
                tagasisideLabel
        );

        Scene stseen = new Scene(juur, 500, 350);

        // Klaviatuuriga vastamine
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

    private void kuvaUusKüsimus() {
        aktiivneKusimus = mang.järgmineKüsimus();

        küsimusLabel.setText(aktiivneKusimus.getKüsimuseTekst());

        for (int i = 0; i < vastuseNupud.length; i++) {
            vastuseNupud[i].setText(
                    aktiivneKusimus.getVariandid().get(i).getRiigiNimi()
            );
        }

        punktidLabel.setText("Punktid: " + mang.getPunktid());
    }

    private void kontrolliVastus(int vastus) {
        boolean õige = mang.kontrolliVastus(aktiivneKusimus, vastus);

        if (õige) {
            tagasisideLabel.setText("Õige!");
            kuvaUusKüsimus();
        } else {
            tagasisideLabel.setText(
                    "Vale! Õige vastus oli: "
                            + aktiivneKusimus.getÕigePealinn().getRiigiNimi()
                            + ". Punktid: " + mang.getPunktid()
            );

            mang.alustaUuesti();
            punktidLabel.setText("Punktid: 0");
            kuvaUusKüsimus();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}