package com.example.hangman_graafiline;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FailistLugemine {
        public static String failinimi = "lemmad.txt";
        public static long failiPikkus = 104230;
    public static ArrayList<String> sõnasOlevadTähed = new ArrayList<>();
        // kui on vaja saab Objekti luua, millel on teine fail salvestatud millega saab siis ka mängida kui formaat sobib
        public FailistLugemine(String failinimi) {
            FailistLugemine.failinimi = failinimi;
        }
        // loeme juhusliku sõna failist ja tagastame
        public static String annaJuhuslikSõna(){
            String sõna = "";
            long millineRida = (long)(Math.random()*failiPikkus+1);
            try {
                BufferedReader scanner = new BufferedReader(new FileReader(failinimi, StandardCharsets.UTF_8));
                for (int i = 0; i < millineRida; i++) {
                    scanner.readLine();
                }
                sõna = scanner.readLine();
                scanner.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return sõna;
        }
        //võtab failist skoorid ja tagastab String nendega
        public static String annaSkoori(String failinimi){
            String skoorid;
            try {
                BufferedReader scanner = new BufferedReader(new FileReader(failinimi));
                String[] skoorideMassiiv = scanner.readLine().split(" ");
                skoorid = "võidud " + skoorideMassiiv[0];
                skoorid = skoorid + " kaotatud "+ skoorideMassiiv[1];
                scanner.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return skoorid;
        }
        //võtab vastu skoorid mida kogu skoorile lisada ja lisab neid faili
        public static void lisaSkoorile(int võidud, int kaotatud, String failinimi){
            String failinimi2 = "skoor.txt";
            int uusVõidud = võidud;
            int uusKaotatud = kaotatud;
            //loeb failist vana skoori ja lisab sellele uued skoorid
            try {
                BufferedReader scanner = new BufferedReader(new FileReader(failinimi2));
                String[] skoorid = scanner.readLine().split(" ");
                uusVõidud = võidud + Integer.valueOf(skoorid[0]);
                uusKaotatud = kaotatud + Integer.valueOf(skoorid[1]);
                scanner.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //kirjutab faili uue skoori
            String midaFaili = uusVõidud+" "+uusKaotatud;
            try {
                BufferedWriter kirjutaja = new BufferedWriter(new FileWriter(failinimi2,false));
                kirjutaja.write(midaFaili);
                kirjutaja.close();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        public String getFailinimi() {
            return failinimi;
        }

        public void setFailinimi(String failinimi) {
            this.failinimi = failinimi;
        }

        public long getFailiPikkus() {
            return failiPikkus;
        }

        public void setFailiPikkus(long failiPikkus) {
            FailistLugemine.failiPikkus = failiPikkus;
        }
    }
