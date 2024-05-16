package com.example.hangman_graafiline;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Locale;
import static com.example.hangman_graafiline.HangmanMäng.*;

public class Meetodid {

    /**
     * kontrollib, kas kasutaja poolt pakutud täht sobib (ühekohaline ja on täht) ja lisab listi
     * kui ei sobi siis kutsub välja pildi joonistamise
     */
    public static void pakutudTähed(String pakutudTäht){
        if (pakutudTäht.length() == 1 && Character.isLetter(pakutudTäht.charAt(0))) {
            pakutudTähedPihtas.add(pakutudTäht);
            if (!arvatavSõna.toLowerCase(Locale.ROOT).contains(pakutudTäht)) {
                valestiArvamisiKokku++;
                joonistaPilt();
            }
        }
    }

    /**
     * ehitab arvatavast sõnast peidetud variandi (ja asendab tähti jooksvalt pakkumisi tehes)
     */
    public static String sõnaPeidetud() {
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

    /**
     * kontrollib, kas failist genereeritud sõna on sama, mis sõnapeidetud meetodis kokkupandud sõna.
     */

    private static boolean kontrolliKasSamad(String esimene, String teine) {
        if (esimene.equals(teine)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param arvatavaSõnaRida pealkirja lahter, mille teksti uuendab
     */

    public static void uuendaRida(Label arvatavaSõnaRida) {
        String peidetudSõna = sõnaPeidetud();
        String võrdlus = peidetudSõna.replace(" ", "");

        if (kontrolliKasSamad(arvatavSõna, võrdlus)) { //kui sõna arvatakse enne elude lõppemist ära
            arvatavaSõnaRida.setText("Arvasid sõna " + arvatavSõna + " ära");
            võidud += 1;
        }
        else if (valestiArvamisiKokku < 7) { //üldine
            arvatavaSõnaRida.setText("Sõna: " + sõnaPeidetud());
        }
        else { //näitab, mis õige sõna oli
            arvatavaSõnaRida.setText("Õige sõna oli: " + arvatavSõna);
            kaotused += 1;
        }
    }

    /**
     * joonistab pildi vastavalt mitu korda on valesti pakutud tähti.
     */

    private static void joonistaPilt() {
        if (valestiArvamisiKokku >= 1) {
            Line vasakPost = new Line(150, 50, 150, 120);
            hangmanPane.getChildren().add(vasakPost);
        }
        if (valestiArvamisiKokku >= 2) {
            Circle pea = new Circle(150, 120, 20);
            pea.setStroke(Color.BLACK);
            hangmanPane.getChildren().add(pea);
        }
        if (valestiArvamisiKokku >= 3) {
            Line keha = new Line(150, 140, 150, 200);
            hangmanPane.getChildren().add(keha);
        }
        if (valestiArvamisiKokku >= 4) {
            Line vasakKäsi = new Line(150, 150, 120, 180);
            hangmanPane.getChildren().add(vasakKäsi);
        }
        if (valestiArvamisiKokku >= 5) {
            Line paremKäsi = new Line(150, 150, 180, 180);
            hangmanPane.getChildren().add(paremKäsi);
        }
        if (valestiArvamisiKokku >= 6) {
            Line vasakJalg = new Line(150, 200, 130, 240);
            hangmanPane.getChildren().add(vasakJalg);
        }
        if (valestiArvamisiKokku >= 7) {
            Line paremJalg = new Line(150, 200, 170, 240);
            Text lõpp = new Text("Kaotasid!");
            lõpp.setFont(Font.font(70));
            lõpp.setLayoutY(200);
            lõpp.setLayoutX(20);
            hangmanPane.getChildren().addAll(paremJalg, lõpp);
        }
    }
}
