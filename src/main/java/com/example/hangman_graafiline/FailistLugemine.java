package com.example.hangman_graafiline;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.example.hangman_graafiline.HangmanMäng.punktisumma;

/**
 * rühmatöö 1 raames tehtud klass
 */

public class FailistLugemine {
    public static String failinimi = "lemmad.txt";
    public static long failiPikkus = 104188;
    public static int võidud;
    public static int kaotused;
    //public static int punktisumma;

    public FailistLugemine(String failinimi) {
        FailistLugemine.failinimi = failinimi;
    }

    // loeme juhusliku sõna failist ja tagastame
    public static String annaJuhuslikSõna() {
        String sõna = "";
        long millineRida = (long) (Math.random() * failiPikkus + 1); //genereerib suvalise numbri terve failipikkuse ulatuses
        try {
            BufferedReader scanner = new BufferedReader(new FileReader(failinimi, StandardCharsets.UTF_8));
            for (int i = 0; i < millineRida; i++) {
                scanner.readLine();
            } //kerib läbi kuni millineRida eelneva reani
            sõna = scanner.readLine(); //ja siis salvestab selle järgmise sõna
            scanner.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sõna;
    }

    //võtab failist skoorid ja tagastab String skooridega
    public static String annaSkoori(String failinimi) {
        String skoorid;
        try {
            BufferedReader scanner = new BufferedReader(new FileReader(failinimi)); //sest failis on ainult üks rida
            String[] skoorideMassiiv = scanner.readLine().split(" ");
            skoorid = "võidud " + skoorideMassiiv[0];
            skoorid = skoorid + " kaotatud " + skoorideMassiiv[1];
            //skoorid = "punktisumma" + skoorideMassiiv[2];
            scanner.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return skoorid;
    }

    //võtab vastu skoorid, mida koguskoorile lisada ja lisab need faili
    public static void lisaSkoorile(int võidud, int kaotatud, int punktisumma, String failinimi) {
        String failinimi2 = "skoor.txt";
        int uusVõidud = võidud; //kui need ära võtta siis hakkab valesti skoori arvutama
        int uusKaotatud = kaotatud;
        int uusPunktisumma = punktisumma;

        //loeb failist vana skoori ja lisab uute skooride arvestusse ka vana skoori
        try {
            BufferedReader scanner = new BufferedReader(new FileReader(failinimi2));
            String[] skoorid = scanner.readLine().split(" ");
            uusVõidud = võidud + Integer.valueOf(skoorid[0]);
            uusKaotatud = kaotatud + Integer.valueOf(skoorid[1]);
            uusPunktisumma = punktisumma + Integer.valueOf(skoorid[2]);
            scanner.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //kirjutab faili uue skoori (kus sees varasemalt olnud skoorid ka)
        String midaFaili = uusVõidud + " " + uusKaotatud + " " + uusPunktisumma;
        try {
            BufferedWriter kirjutaja = new BufferedWriter(new FileWriter(failinimi2, false)); //kui true siis hakkab kõrvale kirjutama (valesid) numbreid
            kirjutaja.write(midaFaili);
            kirjutaja.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *ükskõik mis failis enne oli, see muudetakse ära 0:0
     */
    public static void nulliSkoor(int võidud, int kaotatud, String failinimi) {
        String failinimi2 = "skoor.txt";
        int uusVõidud = 0;
        int uusKaotatud = 0;
        int uusPunktisumma = 100;

        String uuedAndmed = uusVõidud + " " + uusKaotatud + " " + uusPunktisumma;
        try {
            BufferedWriter kirjutaja = new BufferedWriter(new FileWriter(failinimi2, false));
            kirjutaja.write(uuedAndmed);
            kirjutaja.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getVõidud() {
        return võidud;
    }

    public static int getKaotused() {
        return kaotused;
    }

    public static int getPunktisumma() {
        return punktisumma;
    }
}


