package com.example.hangman_graafiline;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.hangman_graafiline.FailistLugemine.punktisumma;
import static com.example.hangman_graafiline.Meetodid.leiaVihje;
import static com.example.hangman_graafiline.Meetodid.sõnaPeidetud;
import static com.example.hangman_graafiline.Meetodid.vihje;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HangmanMäng extends Application {
    public static int valestiArvamisiKokku;
    public static ArrayList<String> pakutudTähedPihtas = new ArrayList<>();
    public static String arvatavSõna;
    public static Pane hangmanPane;
    public static int võidud;
    public static int kaotused;
    public static int punktisumma;


    @Override
    public void start(Stage peaLava) throws FileNotFoundException {
        VBox esimeneJuur = new VBox(20); //ülevalt alla
        esimeneJuur.setAlignment(Pos.CENTER);
        esimeneJuur.setPadding(new Insets(30));

        InputStream taust = new FileInputStream("hangman_taust.jpg");
        Image taustapilt = new Image(taust);
        BackgroundImage backgroundimage = new BackgroundImage(taustapilt,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        esimeneJuur.setBackground(background);

        Media sound;
        try {
            sound = new Media(Paths.get("hangmang_soundtrack_1min.mp3").toUri().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Text pealkiri = new Text("HÄNGMÄNG");
        pealkiri.setFont(Font.font(40));
        Text valiTegevus = new Text("Vali tegevus: ");
        valiTegevus.setFont(Font.font(20));

        HBox nuppudeHbox = new HBox(); //vasakult paremale
        nuppudeHbox.setAlignment(Pos.CENTER);
        nuppudeHbox.setSpacing(20);

        Button alustaNupp = new Button("Alusta mängu");
        alustaNupp.setFont(Font.font(20));
        alustaNupp.setOnAction(event -> {
            try {
                uusMäng(peaLava);
                näitaJuhend();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        Button näitaSkoori = new Button("Näita skoori");
        näitaSkoori.setFont(Font.font(20));
        näitaSkoori.setOnAction(event -> {
            näitabSkoori();
        });
        Button kustutaSkoor = new Button("Kustuta skoor");
        kustutaSkoor.setFont(Font.font(20));
        kustutaSkoor.setOnAction(event -> {
            FailistLugemine.nulliSkoor(0, 0, 100, "skoor.txt");
        });
        Button tühistaNupp = new Button("Katkesta mäng");
        tühistaNupp.setFont(Font.font(20));
        tühistaNupp.setOnAction(event -> {
            peaLava.close();
        });
        nuppudeHbox.getChildren().addAll(alustaNupp, näitaSkoori, kustutaSkoor, tühistaNupp);

        esimeneJuur.getChildren().addAll(pealkiri, valiTegevus, nuppudeHbox);

        Scene stseen = new Scene(esimeneJuur, 1000, 600);
        peaLava.setScene(stseen);
        peaLava.setY(20);
        peaLava.setX(150);

        peaLava.setTitle("Hängmäng ");
        peaLava.setMinHeight(300); //sellest väiksemaks ei lähe
        peaLava.setMinWidth(300);

        peaLava.show();
    }
    private void näitaJuhend() {
        Stage juhendiLava = new Stage();
        juhendiLava.setTitle("Juhend");
        juhendiLava.setResizable(false);
        juhendiLava.setX(500);
        juhendiLava.setY(200);
        Text text = new Text("Juhend: \n Kui arvad sõna ära enne elude otsa saamist" +
                " lisandub punktisummale 100 punkti. \n " +
                "kui kasutad vihjet läheb 30 punkti maha \n" +
                " kui sa ei arva sõna ära siis läheb 50 punkti maha");
        HBox kast = new HBox(text);
        kast.setStyle("-fx-background-color: beige"); //n
        Scene juhendiStseen = new Scene(kast);

        juhendiLava.setScene(juhendiStseen);
        juhendiLava.show();
    }

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
        skooriJuur.setStyle("-fx-background-color: beige");
        skooriLava.setScene(skooriStseen);
        skooriLava.show();
        PauseTransition paus = new PauseTransition(Duration.seconds(2.5));
        paus.setOnFinished(event -> {
            skooriLava.close();
        });
        paus.play();
    }

    private void uusMäng(Stage peaLava2) throws FileNotFoundException {
        arvatavSõna = FailistLugemine.annaJuhuslikSõna();
        leiaVihje(arvatavSõna.toString());
        valestiArvamisiKokku = 0;
        pakutudTähedPihtas.clear(); //kustutab varasemalt pakutud

        Text vihjeKast = new Text(vihje.toString());
        vihjeKast.setFont(Font.font(20));
        vihjeKast.setTextAlignment(TextAlignment.LEFT);
        vihjeKast.setFill(Color.BLACK);
        vihjeKast.setVisible(false);

        double wrappingWidth = 200;
        vihjeKast.setWrappingWidth(wrappingWidth);

        if (vihje.length() > 30) {
            String wrappedText = Meetodid.wrapText(vihje.toString(), 30);
            vihjeKast.setText(wrappedText);
        }
        else if (vihje == null)
            vihjeKast.setText("Vihje puudub");
        /////////////////////////////////////////////////////

        võidud = FailistLugemine.getVõidud();
        kaotused = FailistLugemine.getKaotused();

        VBox mänguPaikneminePane = new VBox();

        InputStream taust = new FileInputStream("hangman_taust.jpg");
        Image taustapilt = new Image(taust);
        BackgroundImage backgroundimage = new BackgroundImage(taustapilt,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        mänguPaikneminePane.setBackground(background);
        mänguPaikneminePane.setSpacing(20);
        mänguPaikneminePane.setAlignment(Pos.CENTER);

        Scene stseen2 = new Scene(mänguPaikneminePane, 1000, 600);
        peaLava2.setY(20);
        peaLava2.setX(150);
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
            try {
                start(peaLava2);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
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
            FailistLugemine.lisaSkoorile(võidud, kaotused, punktisumma, "skoor.txt");
            Stage uuslava = new Stage();
            try {
                uusMäng(uuslava);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            peaLava2.hide();
            uuslava.show();
        });
        nupud.getChildren().addAll(katkestaMäng, vaataSkoori, alustaUuesti);

        hangmanPane = new Pane();
        //ülesehitus (alati nähtavad postid)
        Line alumineRida = new Line(250, 300, 350, 300); //x algus, y algus, x lõpp, y lõpp//
        Line paremPost = new Line(300, 50, 300, 300);
        Line ülemineRida = new Line(150, 50, 300, 50);

        hangmanPane.getChildren().addAll(alumineRida, paremPost, ülemineRida);

        HBox hangmanPaneKeskele = new HBox();

        Button vihjenupp = new Button("Vihje (-30p)");
        vihjenupp.setLayoutX(30);
        vihjenupp.setAlignment(Pos.TOP_CENTER);
        vihjenupp.setFont(Font.font(20));
        vihjenupp.setOnAction(event -> {
            punktisumma -= 30;
            if (vihjeKast.isVisible())
            vihjeKast.setVisible(false);
            else
                vihjeKast.setVisible(true);
        });
        Text punktid = new Text();
        punktid.setFont(Font.font(20));
        punktid.setText("Punktisumma: " + String.valueOf(punktisumma));

        VBox vihjeElemendid = new VBox(punktid, vihjenupp, vihjeKast);
        //vihjeElemendid.setPadding(new Insets(50));
        vihjeElemendid.setSpacing(15);
        vihjeElemendid.setPrefSize(200, 50);

        hangmanPaneKeskele.getChildren().addAll(vihjeElemendid,hangmanPane);
        hangmanPaneKeskele.setAlignment(Pos.CENTER);

        mänguPaikneminePane.getChildren().addAll(sõnaPakutud, pakuTäht, nupud,hangmanPaneKeskele);
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
        peaLava2.setTitle("Hangmäng");
    }
    public static void main(String[] args) {
        launch(args);
    }
}