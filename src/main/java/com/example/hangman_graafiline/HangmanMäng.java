package com.example.hangman_graafiline;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Locale;

public class HangmanMäng extends Application {
    private static int valestiArvamisiKokku;
    private static ArrayList<String> pakutudTähedPihtas = new ArrayList<>();
    private static String arvatavSõna;
    private static Pane hangman;
    private int võidud;
    private int kaotused;

    @Override
    public void start(Stage peaLava) {
        peaLava.setTitle("Hangman mäng");

        VBox esimeneJuur = new VBox(20);
        esimeneJuur.setAlignment(Pos.CENTER);
        esimeneJuur.setPadding(new Insets(30));

        esimeneJuur.setStyle("-fx-background-color: pink");

        Text pealkiri = new Text("HANGMAN");
        pealkiri.setY(50);
        pealkiri.setFont(Font.font(40));
        Text valiTegevus = new Text("Vali tegevus: ");
        valiTegevus.setY(100);
        valiTegevus.setFont(Font.font(20));

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(30));
        hbox.setSpacing(20);

        Button alustaNupp = new Button("Alusta mängu");
        alustaNupp.setLayoutX(20);
        alustaNupp.setFont(Font.font(20));
        alustaNupp.setOnAction(event -> {
            uusMäng(peaLava);
        });
        Button näitaSkoori = new Button("Näita skoori");
        näitaSkoori.setLayoutX(40);
        näitaSkoori.setFont(Font.font(20));
        näitaSkoori.setOnAction(event -> {
            näitabSkoori();
        });
        Button kustutaSkoor = new Button("Kustuta skoor");
        kustutaSkoor.setLayoutX(40);
        kustutaSkoor.setLayoutY(100);
        kustutaSkoor.setFont(Font.font(20));
        kustutaSkoor.setOnAction(event -> {
            FailistLugemine.nulliSkoor(0, 0, "skoor.txt");
        });
        Button tühistaNupp = new Button("Katkesta mäng");
        tühistaNupp.setFont(Font.font(20));
        tühistaNupp.setOnAction(event -> {
            peaLava.close();
        });
        hbox.getChildren().addAll(alustaNupp, näitaSkoori, kustutaSkoor, tühistaNupp);

        esimeneJuur.getChildren().addAll(pealkiri, valiTegevus, hbox);

        Scene stseen = new Scene(esimeneJuur, 800, 600);
        peaLava.setScene(stseen);
        peaLava.setMinHeight(300);
        peaLava.setMinWidth(300);
        peaLava.show();
    }
    private void näitabSkoori() {
        Stage skooriLava = new Stage();
        skooriLava.setTitle("Punktiseis");
        skooriLava.setResizable(false);

        Text skoorid = new Text(FailistLugemine.annaSkoori("skoor.txt"));
        skoorid.setFont(Font.font(16));
        skoorid.setStroke(Color.DARKRED);

        HBox skooriJuur = new HBox();
        skooriJuur.setAlignment(Pos.CENTER);
        skooriJuur.getChildren().add(skoorid);

        Scene skooriStseen = new Scene(skooriJuur, 200, 200);
        skooriJuur.setStyle("-fx-background-color: pink");
        skooriLava.setScene(skooriStseen);
        skooriLava.show();
        PauseTransition paus = new PauseTransition(Duration.seconds(2.5));
        paus.setOnFinished(event -> {
            skooriLava.close();
        });
        paus.play();
    }
    private void uusMäng(Stage peaLava2) {
        arvatavSõna = FailistLugemine.annaJuhuslikSõna();
        valestiArvamisiKokku = 0;
        pakutudTähedPihtas.clear(); //kustutab varasemalt pakutud

       võidud = FailistLugemine.getVõidud();
       kaotused = FailistLugemine.getKaotused();

        FlowPane mängPaan = new FlowPane();

        mängPaan.setPadding(new Insets(40));
        mängPaan.setHgap(50);
        mängPaan.setVgap(20);
        mängPaan.setAlignment(Pos.CENTER);
        mängPaan.setStyle("-fx-background-color: pink");

        Scene stseen2 = new Scene(mängPaan, 800, 600);
        peaLava2.setResizable(true);
        peaLava2.setScene(stseen2);

        Label sõnaPakutud = new Label("Sõna: " + sõnaPeidetud());
        sõnaPakutud.setFont(Font.font(40));

        TextField pakuTäht = new TextField();
        pakuTäht.setOnKeyReleased(event -> {
            pakutudTähed(pakuTäht.getText().toLowerCase(Locale.ROOT));
            uuendaRida(sõnaPakutud);
            pakuTäht.clear(); //kustutab lahtrist just sisestatud tähe
        });
        HBox nupud = new HBox();
        nupud.setSpacing(20);
        Button katkestaMäng = new Button("Tagasi avaekraanile");
        katkestaMäng.setFont(Font.font(13));
        katkestaMäng.setTextFill(Color.DARKBLUE);
        katkestaMäng.setOnMouseClicked(event -> {
            peaLava2.close();
            start(peaLava2);
        });

        Button vaataSkoori = new Button("Vaata skoori");
        vaataSkoori.setFont(Font.font(13));
        vaataSkoori.setTextFill(Color.DARKBLUE);
        vaataSkoori.setOnAction(event -> {
            näitabSkoori();
        });

        Button alustaUuesti = new Button("Alusta uuesti");
        alustaUuesti.setFont(Font.font(12));
        alustaUuesti.setTextFill(Color.DARKBLUE);
        alustaUuesti.setOnAction(event -> {
            FailistLugemine.lisaSkoorile(võidud, kaotused, "skoor.txt");
            Stage uuslava = new Stage();
            uusMäng(uuslava);
            peaLava2.hide();
            uuslava.show();
        });
        nupud.getChildren().addAll(katkestaMäng, vaataSkoori, alustaUuesti);

        hangman = new Pane();

        //ülesehitus (alati nähtavad)
        Line alumineRida = new Line(250, 300, 350, 300);
        alumineRida.setVisible(true);

        Line paremPost = new Line(300, 50, 300, 300);
        paremPost.setVisible(true);

        Line ülemineRida = new Line(150, 50, 300, 50);
        ülemineRida.setVisible(true);

        hangman.getChildren().addAll(alumineRida, paremPost, ülemineRida);
        mängPaan.setAlignment(Pos.CENTER);
        mängPaan.getChildren().addAll(sõnaPakutud, pakuTäht, nupud, hangman);

        peaLava2.widthProperty().addListener((obs, vanaLaius, uusLaius) -> {
            hangman.setPrefWidth(uusLaius.doubleValue() * 0.5);
            sõnaPakutud.setFont(Font.font(uusLaius.doubleValue() / 20));
            pakuTäht.setPrefWidth(uusLaius.doubleValue() * 0.2);
            nupud.setPrefWidth(uusLaius.doubleValue() * 0.5);
        });
        peaLava2.heightProperty().addListener((obs, vanaKõrgus, uusKõrgus) -> {
            hangman.setPrefHeight(uusKõrgus.doubleValue() * 0.5);
            sõnaPakutud.setFont(Font.font(uusKõrgus.doubleValue() / 20));
            nupud.setPrefWidth(uusKõrgus.doubleValue() * 0.5);
        });

    }

    private void pakutudTähed(String pakutudTäht){
            if (pakutudTäht.length() == 1 && Character.isLetter(pakutudTäht.charAt(0))) {
                pakutudTähedPihtas.add(pakutudTäht);
                if (!arvatavSõna.toLowerCase(Locale.ROOT).contains(pakutudTäht)) {
                    valestiArvamisiKokku++;
                    joonistaPilt();
                }
            }
    }
    private static String sõnaPeidetud() {
        StringBuilder peidetavSõna = new StringBuilder();
        for (int i = 0; i < arvatavSõna.length(); i++) {
            String täht = String.valueOf(arvatavSõna.charAt(i));
            if (pakutudTähedPihtas.contains(täht.toLowerCase(Locale.ROOT))) {
                peidetavSõna.append(täht).append("");
            } else {
                peidetavSõna.append("_ ");
            }
        }
        String sõnaPeidetud = peidetavSõna.toString();
        return sõnaPeidetud;
    }

    private static boolean kontrolliKasSamad(String esimene, String teine) {
        if (esimene.equals(teine)) return true;
        else return false;
    }

    private void uuendaRida(Label arvatavaSõnaRida) {
        String peidetudSõna = sõnaPeidetud();
        String võrdlus = peidetudSõna.replace(" ", "");

        if (kontrolliKasSamad(arvatavSõna, võrdlus)) {
            arvatavaSõnaRida.setText("Arvasid sõna " + arvatavSõna + " ära");
            võidud += 1;
        }
        else if (valestiArvamisiKokku < 7) {
            arvatavaSõnaRida.setText("Sõna: " + sõnaPeidetud());
        }
        else {
            arvatavaSõnaRida.setText("Õige sõna oli: " + arvatavSõna);
            kaotused += 1;
        }
    }

    private static void joonistaPilt() {
        if (valestiArvamisiKokku >= 1) {
            Line vasakPost = new Line(150, 50, 150, 120);
            hangman.getChildren().add(vasakPost);
        }
        if (valestiArvamisiKokku >= 2) {
            Circle pea = new Circle(150, 120, 20);
            pea.setStroke(Color.BLACK);
            hangman.getChildren().add(pea);
        }
        if (valestiArvamisiKokku >= 3) {
            Line keha = new Line(150, 140, 150, 200);
            hangman.getChildren().add(keha);
        }
        if (valestiArvamisiKokku >= 4) {
            Line vasakKäsi = new Line(150, 150, 120, 180);
            hangman.getChildren().add(vasakKäsi);
        }
        if (valestiArvamisiKokku >= 5) {
            Line paremKäsi = new Line(150, 150, 180, 180);
            hangman.getChildren().add(paremKäsi);
        }
        if (valestiArvamisiKokku >= 6) {
            Line vasakJalg = new Line(150, 200, 130, 240);
            hangman.getChildren().add(vasakJalg);
        }
        if (valestiArvamisiKokku >= 7) {
            Line paremJalg = new Line(150, 200, 170, 240);
            Text lõpp = new Text("Kaotasid!");
            lõpp.setFont(Font.font(70));
            lõpp.setLayoutY(200);
            lõpp.setLayoutX(20);
            hangman.getChildren().addAll(paremJalg, lõpp);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
