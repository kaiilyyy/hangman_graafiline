/*package com.example.hangman_graafiline;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Locale;


public class HangmanMäng extends Application {
    private static int valestiArvamisiKokku;
    private static ArrayList<String> pakutudTähed = new ArrayList<>();
    private static String arvatavSõna;
    private static String pakutudTäht;

    private static Pane hangman;

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
        Button muudaSõnadeFaili = new Button("Muuda sõnadefaili");
        muudaSõnadeFaili.setLayoutX(40);
        muudaSõnadeFaili.setFont(Font.font(20));
        muudaSõnadeFaili.setOnAction(event -> {
            //siia tegevus et saaks uue faili võtta.
            //või siis jätab selle nupu tegemata
        });
        Button näitaSkoori = new Button("Näita skoori");
        näitaSkoori.setLayoutX(40);
        näitaSkoori.setFont(Font.font(20));
        näitaSkoori.setOnAction(event -> {
            näitabSkoori();
        });
        Button tühistaNupp = new Button("Tühista");
        tühistaNupp.setFont(Font.font(20));
        tühistaNupp.setOnAction(event -> {
            peaLava.close();
        });
        hbox.getChildren().addAll(alustaNupp, muudaSõnadeFaili, näitaSkoori, tühistaNupp);

        esimeneJuur.getChildren().addAll(pealkiri, valiTegevus, hbox);

        Scene stseen = new Scene(esimeneJuur, 800, 600);
        peaLava.setScene(stseen);
        peaLava.show();
    }

    private void näitabSkoori() {
        Stage skooriLava = new Stage();
        skooriLava.setTitle("Skoorid");

        // Retrieve scores from file and display in a Text object
        Text skoorid = new Text(FailistLugemine.annaSkoori("skoor.txt"));
        skoorid.setFont(Font.font(16));

        // Create a layout for displaying scores
        HBox skooriJuur = new HBox();
        skooriJuur.setAlignment(Pos.CENTER);
        skooriJuur.getChildren().add(skoorid);

        Scene skooriStseen = new Scene(skooriJuur, 200, 200);
        skooriLava.setScene(skooriStseen);
        skooriLava.show();
    }

    private void uusMäng(Stage peaLava2) {
        arvatavSõna = FailistLugemine.annaJuhuslikSõna();
        valestiArvamisiKokku = 0;
        pakutudTähed.clear(); //kustutab varasemalt pakutud

        FlowPane mängPaan = new FlowPane();

        mängPaan.setPadding(new Insets(40));
        mängPaan.setHgap(50);
        mängPaan.setVgap(20);
        mängPaan.setAlignment(Pos.CENTER);
        mängPaan.setStyle("-fx-background-color: pink");

        Scene stseen2 = new Scene(mängPaan, 800, 600);
        peaLava2.setResizable(false);
        peaLava2.setScene(stseen2);

        Label sõnaPakutud = new Label("Sõna: " + sõnaPeidetud());
        sõnaPakutud.setFont(Font.font(40));
        TextField pakutudTäht = new TextField();
        pakutudTäht.setOnKeyReleased(event -> { //kui vajutatakse enterit siis töötab ka
            //pakutudTähed(pakutudTäht.getText().toLowerCase(Locale.ROOT));
            sõnaPeidetud(pakutudTäht.getText().toLowerCase(Locale.ROOT));
            uuendaRida(sõnaPakutud);
            pakutudTäht.clear(); //kustutab lahtrist tähe
        });
        Button pakuNupp = new Button("Paku täht");
        pakuNupp.setOnAction(event -> {
            //pakutudTähed(pakutudTäht.getText().toLowerCase(Locale.ROOT));
            sõnaPeidetud(pakutudTäht.getText().toLowerCase(Locale.ROOT));
            uuendaRida(sõnaPakutud);
            pakutudTäht.clear(); //kustutab lahtrist tähe
        });
        Button katkestaMäng = new Button("Tagasi avaekraanile");
        katkestaMäng.setOnAction(event -> {

        });
        Button salvestaMäng = new Button("Salvesta skoor");
        katkestaMäng.setOnAction(event -> {
            //FailistLugemine.lisaSkoorile();
        });
        Button alustaUuesti = new Button("Alusta uuesti");
        alustaUuesti.setOnAction(event -> {
            Stage uuslava = new Stage();
            uusMäng(uuslava);
            peaLava2.hide();
            uuslava.show();
        });

        hangman = new Pane();
        //ülesehitus (alati nähtavad)
        Line alumineRida = new Line(250, 300, 350, 300);
        alumineRida.setVisible(true);

        Line paremPost = new Line(300, 50, 300, 300);
        paremPost.setVisible(true);

        Line ülemineRida = new Line(150, 50, 300, 50);
        ülemineRida.setVisible(true);

        hangman.getChildren().addAll(alumineRida, paremPost, ülemineRida);

        mängPaan.getChildren().addAll(sõnaPakutud, pakutudTäht, pakuNupp, katkestaMäng, salvestaMäng, alustaUuesti, hangman);
    }


    /*private void pakutudTähed(String pakutudTäht) {
        if (pakutudTäht.length() == 1 && Character.isLetter(pakutudTäht.charAt(0))) {
            pakutudTähed.add(pakutudTäht);
            if (!arvatavSõna.toLowerCase(Locale.ROOT).contains(pakutudTäht)) {
                valestiArvamisiKokku++;
                joonistaPilt();
            }
        }
    }
    public static void sõnaPeidetud(String sõna) {
        boolean kasTähtOnSõnas = false;
        for (int indeks = 0; indeks < sõna.length(); indeks++) {
            if (pakutudTäht.equals(String.valueOf(sõna.charAt(indeks)))) {
                pakutudTähed.set(indeks, pakutudTäht);
                kasTähtOnSõnas = true;
                //kui tähte pole sõnas siis lisab arvatud tähtede hulga ekraanile
            } else {
                if (!pakutudTähed.contains(pakutudTäht)) {
                    pakutudTähed.add(pakutudTäht);
                }
            }
        }
        if (!kasTähtOnSõnas) {
            valestiArvamisiKokku++;
            joonistaPilt();
        }
    }

    private static void joonistaPilt() {

        if (valestiArvamisiKokku >= 1) {
            Line vasakPost = new Line(150, 50, 150, 120);
            hangman.getChildren().add(vasakPost);
        }
        if (valestiArvamisiKokku >= 2) {
            Circle pea = new Circle(150, 120, 20);
            pea.setFill(Color.RED);
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
            hangman.getChildren().add(paremJalg);
        }
    }


    private String sõnaPeidetud() {
        StringBuilder peidetavSõna = new StringBuilder();
        for (char c : arvatavSõna.toCharArray()) {
            String charStr = String.valueOf(c);
            if (pakutudTähed.contains(charStr.toLowerCase(Locale.ROOT))) {
                peidetavSõna.append(c).append(" ");
            } else {
                peidetavSõna.append("_ ");
            }
        }
        return peidetavSõna.toString();
    }
}
    /*public String arvatudJaArvamataRida() {
        String arvatudRida = "";
        for (int i = 0; i < pakutudTähed.size(); i++) {
            if (i< arvatud.length-1){
                arvatudRida = arvatudRida + arvatud[i]+ " ";
            }else {
                arvatudRida = arvatudRida + arvatud[i];
            }
        }
        return arvatudRida;
    }*&

    private void uuendaRida(Label wordLabel) { //uuendab ekraanil olevat arvatudJaArvamata rida
        if (valestiArvamisiKokku < 7) {
            wordLabel.setText("Sõna: " + sõnaPeidetud());
        }
        else {
            wordLabel.setText("Õige sõna oli: " + arvatavSõna);

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static boolean kasSõnaÄraArvatud(String[] arvatudÄra) {
        for (String täht : arvatudÄra) {
            if (täht.equals("_")) return false;
        }
        return true;
    }
    public static String arvatudJaArvamataRida(String[] arvatud) {
        String arvatudRida = "";
        for (int i = 0; i < arvatud.length; i++) {
            if (i < arvatud.length - 1) {
                arvatudRida = arvatudRida + arvatud[i] + " ";
            } else {
                arvatudRida = arvatudRida + arvatud[i];
            }
        }
        return arvatudRida;
    }
}
*/
