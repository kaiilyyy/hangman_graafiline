package com.example.hangman_graafiline;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class Peaklass extends Application {
    private static int valestiArvamisiKokku;
    static ArrayList<String> pakutudTähed = new ArrayList<>();

    @Override
    public void start(Stage peaLava) throws Exception {
        //ENNE MÄNGU
        HBox juur = new HBox();
        juur.setSpacing(20);
        juur.setAlignment(Pos.CENTER);

        Scene stseen1 = new Scene(juur, 700, 500, Color.BLACK);
        peaLava.setScene(stseen1);
        peaLava.setTitle("Hangman mäng");
        peaLava.show();
        Text algus = new Text("HANGMAN");
        algus.setX(100);
        algus.setY(200);

        algus.setFont(Font.font(40));

        Button alustaNupp = new Button("Alusta mängu");
        alustaNupp.setAlignment(Pos.BASELINE_LEFT);
        alustaNupp.setFont(new Font(16));
        alustaNupp.setTextFill(Color.BLACK);
        alustaNupp.setLayoutX(120);
        alustaNupp.setLayoutY(150);

        Button tühistaNupp = new Button("Tühista");
        alustaNupp.setAlignment(Pos.BASELINE_RIGHT);

        tühistaNupp.setFont(new Font(16));
        tühistaNupp.setTextFill(Color.BLACK);
        tühistaNupp.setLayoutX(180);
        tühistaNupp.setLayoutY(150);

        juur.getChildren().addAll(algus, alustaNupp, tühistaNupp);

        alustaNupp.setOnMouseClicked(event -> {
            sõnadePakkumine(peaLava);
        });
        tühistaNupp.setOnMouseClicked(event -> {
            peaLava.close();
        });


    }
    public static void sõnadePakkumine(Stage peaLava) {
        Pane mänguPaan = new Pane();
        Line baseLine = new Line(50, 400, 200, 400);
        mänguPaan.getChildren().add(baseLine);

        boolean mängJookseb = true;
        FailistLugemine fail = new FailistLugemine("lemmad.txt");
        int selleSessiooniÕiged = 0;
        int selleSessiooniValed = 0;

        //mängu pealoop
        while (mängJookseb) {
            Scanner scan1 = new Scanner(System.in);
            System.out.println("");
            System.out.println("Mida sa tahad teha? Sisesta number.");
            System.out.println("1: mängida\n2: sõnadefaili muuta\n3: näita skoore\n4: lõpetada");
            int valitudAktsioon = scan1.nextInt();
            System.out.println("");

            //mängu ise (tähtede pakkumine jne) sooritamine
            if (valitudAktsioon == 1) {
                String sõna = FailistLugemine.annaJuhuslikSõna();
                System.out.println("Sõna pikkus on: " + sõna.length() + " tähte");
                System.out.println("NB! Mõnes sõnas võib olla ka '-'(sidekriips)");
                String[] tähedSõnas = new String[sõna.length()];
                for (int i = 0; i < sõna.length(); i++) {
                    tähedSõnas[i] = "_"; //teeb listi _ mida hakkab asendama hiljem
                }
                System.out.println("_ ".repeat(sõna.length()));
                valestiArvamisiKokku = 0;
                Scanner scan2 = new Scanner(System.in);
                while (valestiArvamisiKokku < 7) {
                    System.out.println("Paku täht: ");
                    String pakutudTäht = scan2.nextLine();

                    //kutsub välja meetodi et kontrollida kas täht on sõnas
                    mäng(sõna, pakutudTäht.toLowerCase(Locale.ROOT), tähedSõnas);

                    //võitmise tingimus ja skoori salvestamine
                    if (kasSõnaÄraArvatud(tähedSõnas)) {
                        System.out.println("Arvasid õige sõna ära!");
                        selleSessiooniÕiged++;
                        FailistLugemine.lisaSkoorile(1,0, "skoor.txt");
                        pakutudTähed.clear();
                        break;
                    }
                }
                // see on kaotamistingimus ja skoori salvestamine
                if (valestiArvamisiKokku == 7) {
                    System.out.println("Mäng läbi.\nÕige sõna oli: " + sõna);
                    selleSessiooniValed++;
                    FailistLugemine.lisaSkoorile(0,1, "skoor.txt");
                    pakutudTähed.clear();
                    //System.exit(0);
                }
                //siin saab teksti faili muuta. Nii saab ka mängija ise otsustada mis sõnad võivad tulla.
            } else if (valitudAktsioon == 2) {
                Scanner scan2 = new Scanner(System.in);
                System.out.println("Aktuaalne sõnadefail on "+ fail.getFailinimi()+".\n Selles failis on "+fail.getFailiPikkus()+" sõnad.");
                System.out.println("Fail peab selle mängu kaustas, .txt formaatis olema ja iga rida peab olema üks sõna.\nKas oled kindel et tahad faili muuta? (jah/ei)");
                String tõesti = scan2.nextLine();
                if (tõesti.equals("jah")) {
                    System.out.println("Sisesta failinimi:");
                    String uusFailinimi = scan2.nextLine();
                    System.out.println("Sisesta faili sõnadearv");
                    long uueFailipikkus = scan2.nextInt();
                    fail.setFailinimi(uusFailinimi);
                    fail.setFailiPikkus(uueFailipikkus);
                    System.out.println("Fail muudetud!");
                }
                // siin lõpetakse mängu ja antakse sessiooni skoori välja
            } else if (valitudAktsioon == 3) {
                System.out.println("Skoorid on:\n" + FailistLugemine.annaSkoori("skoor.txt"));

                //siin saab mängu lõpetada ja sessiooni skoori
            } else if (valitudAktsioon == 4) {
                System.out.println("Selles sessioonis sa:\narvasid ära "+selleSessiooniÕiged+" korda!\nkaotasid "+selleSessiooniValed+" korda!");
                System.out.println("Mäng lõpetatakse...");
                break;
                //kui vale sisend
            } else System.out.println("Tundub et sa kirjutasid midagi valesti.");
        }
    }
    public static void mäng(String sõna, String täht, String[] arvatudÄra) {
        boolean kasTähtOnSõnas = false;
        for (int indeks = 0; indeks < sõna.length(); indeks++) {
            if (täht.equals(String.valueOf(sõna.charAt(indeks)))) {
                arvatudÄra[indeks] = täht;
                kasTähtOnSõnas = true;
                //kui tähte pole sõnas siis lisab arvatud tähtede hulga ekraanile
            } else {
                if (!pakutudTähed.contains(täht)) {
                    pakutudTähed.add(täht);
                }
            }
        }
        //kasutab joonistamise meetodit, et pilti joonistada
        if (!kasTähtOnSõnas) {
            valestiArvamisiKokku++;
            PildiJoonistamine.näitaPilti(valestiArvamisiKokku);
            System.out.println("Seda tähte selles sõnas ei ole...");
        }
        System.out.println(arvatudJaArvamataRida(arvatudÄra));

        System.out.println("Sinu pakutud tähed: " + pakutudTähed);
    }

    //meetod, mis vaatab kas kogu sõna on ära arvatud
    public static boolean kasSõnaÄraArvatud(String[] arvatudÄra) {
        for (String täht : arvatudÄra) {
            if (täht.equals("_")) return false;
        }
        return true;
    }

    //tagastab sõne milles on sõna ainult ära arvatud tähed ja muidu "_"
    public static String arvatudJaArvamataRida(String[] arvatud){
        String arvatudRida = "";
        for (int i = 0; i < arvatud.length; i++) {
            if (i<arvatud.length-1){
                arvatudRida = arvatudRida + arvatud[i]+ " ";
            }else {
                arvatudRida = arvatudRida + arvatud[i];
            }
        }
        return arvatudRida;
    }

    public static void main(String[] args) {
        launch(args);
    }
}


