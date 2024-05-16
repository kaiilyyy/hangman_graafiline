package com.example.hangman_graafiline;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.layout.*;
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

import static com.example.hangman_graafiline.Meetodid.sõnaPeidetud;

public class HangmanMäng extends Application {
    public static int valestiArvamisiKokku;
    public static ArrayList<String> pakutudTähedPihtas = new ArrayList<>();
    public static String arvatavSõna;
    public static Pane hangmanPane;
    public static int võidud;
    public static int kaotused;

    /**
     * mängu avaaken
     */
    @Override
    public void start(Stage peaLava) {
        peaLava.setTitle("Hangman mäng");

        VBox esimeneJuur = new VBox(20);
        esimeneJuur.setAlignment(Pos.CENTER);
        esimeneJuur.setPadding(new Insets(30));

        esimeneJuur.setStyle("-fx-background-color: pink"); //css

        Text pealkiri = new Text("HANGMAN");
        pealkiri.setFont(Font.font(40));
        Text valiTegevus = new Text("Vali tegevus: ");
        valiTegevus.setFont(Font.font(20));

        HBox nuppudeHbox = new HBox();
        nuppudeHbox.setAlignment(Pos.CENTER);
        nuppudeHbox.setPadding(new Insets(30));
        nuppudeHbox.setSpacing(20);

        Button alustaNupp = new Button("Alusta mängu");
        alustaNupp.setFont(Font.font(20));
        alustaNupp.setOnAction(event -> {
            uusMäng(peaLava);
        });
        Button näitaSkoori = new Button("Näita skoori");
        näitaSkoori.setFont(Font.font(20));
        näitaSkoori.setOnAction(event -> {
            näitabSkoori();
        });
        Button kustutaSkoor = new Button("Kustuta skoor");
        kustutaSkoor.setFont(Font.font(20));
        kustutaSkoor.setOnAction(event -> {
            FailistLugemine.nulliSkoor(0, 0, "skoor.txt");
        });
        Button tühistaNupp = new Button("Katkesta mäng");
        tühistaNupp.setFont(Font.font(20));
        tühistaNupp.setOnAction(event -> {
            peaLava.close();
        });
        nuppudeHbox.getChildren().addAll(alustaNupp, näitaSkoori, kustutaSkoor, tühistaNupp);

        esimeneJuur.getChildren().addAll(pealkiri, valiTegevus, nuppudeHbox);

        Scene stseen = new Scene(esimeneJuur, 800, 600);
        peaLava.setScene(stseen);
        peaLava.setMinHeight(300); //sellest väiksemaks ei lähe
        peaLava.setMinWidth(300);
        peaLava.show();
    }

    /**
     * skoori aken, näitab võite ja kaotusi, ja läheb ise kinni
     */
    private void näitabSkoori() {
        Stage skooriLava = new Stage();
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

    /**
     * põhiline aken, kus toimub kogu mängu sisu
     */
    private void uusMäng(Stage peaLava2) {
        arvatavSõna = FailistLugemine.annaJuhuslikSõna();
        valestiArvamisiKokku = 0;
        pakutudTähedPihtas.clear(); //kustutab varasemalt pakutud

       võidud = FailistLugemine.getVõidud();
       kaotused = FailistLugemine.getKaotused();

        VBox mänguPaikneminePane = new VBox();

        mänguPaikneminePane.setPadding(new Insets(40));
        mänguPaikneminePane.setSpacing(20);
        mänguPaikneminePane.setAlignment(Pos.CENTER);
        mänguPaikneminePane.setStyle("-fx-background-color: pink");

        Scene stseen2 = new Scene(mänguPaikneminePane, 800, 600);
        peaLava2.setScene(stseen2);

        Label sõnaPakutud = new Label("Sõna: " + sõnaPeidetud());
        sõnaPakutud.setAlignment(Pos.CENTER);
        sõnaPakutud.setFont(Font.font(40));

        TextField pakuTäht = new TextField();
        pakuTäht.setMaxWidth(50); //muidu läheb terve akna suuruseks
        pakuTäht.setMaxHeight(20);
        pakuTäht.setAlignment(Pos.CENTER);
        pakuTäht.setOnKeyReleased(event -> {
            Meetodid.pakutudTähed(pakuTäht.getText().toLowerCase(Locale.ROOT));
            Meetodid.uuendaRida(sõnaPakutud);
            pakuTäht.clear(); //kustutab lahtrist just sisestatud tähe
        });
        HBox nupud = new HBox();
        nupud.setAlignment(Pos.CENTER);
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

        hangmanPane = new Pane();
        //ülesehitus (alati nähtavad postid)
        Line alumineRida = new Line(250, 300, 350, 300);
        Line paremPost = new Line(300, 50, 300, 300);
        Line ülemineRida = new Line(150, 50, 300, 50);
        hangmanPane.getChildren().addAll(alumineRida, paremPost, ülemineRida);

        mänguPaikneminePane.getChildren().addAll(sõnaPakutud, pakuTäht, nupud, hangmanPane);
        mänguPaikneminePane.setAlignment(Pos.CENTER);

        //muudab suurusi
        peaLava2.widthProperty().addListener((observableValue, vanaLaius, uusLaius) -> {
            hangmanPane.setPrefWidth(uusLaius.doubleValue() * 0.5);
            sõnaPakutud.setFont(Font.font(uusLaius.doubleValue() / 20));
            pakuTäht.setPrefWidth(uusLaius.doubleValue() * 0.2);
            nupud.setPrefWidth(uusLaius.doubleValue() * 0.5);
        });
        peaLava2.heightProperty().addListener((observableValue, vanaKõrgus, uusKõrgus) -> {
            hangmanPane.setPrefHeight(uusKõrgus.doubleValue() * 0.5);
            sõnaPakutud.setFont(Font.font(uusKõrgus.doubleValue() / 20));
            nupud.setPrefWidth(uusKõrgus.doubleValue() * 0.5);
        });
        peaLava2.setMinHeight(550); //sellest väiksemaks ei lähe
        peaLava2.setMinWidth(450);
        peaLava2.setTitle("Hangman mäng");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
