package com.example.hangman_graafiline;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * rühmatöö 1 raames tehtud klass
 */

public class FailistLugemine {
    public static String failinimi = "lemmad.txt";
    public static int võidud;
    public static int kaotused;
    public static int punktisumma;

    /**
     *
     * @param failinimi
     * @return failipikkus suvalise faili puhul (seega saab nüüd faili vahetada)
     * @throws IOException
     */

    private static long loendaRead(String failinimi) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(failinimi), StandardCharsets.UTF_8)) {
            return reader.lines().count();
        }
    }

    public FailistLugemine(String failinimi) {
        FailistLugemine.failinimi = failinimi;
    }

    // loeme juhusliku sõna failist ja tagastame
    public static String annaJuhuslikSõna() {
        String sõna = "";
        try {
            long failiPikkus = loendaRead(failinimi);
            System.out.println(failiPikkus);
            long millineRida = (long) (Math.random() * failiPikkus + 1); //genereerib suvalise numbri terve failipikkuse ulatuses
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

    //võtab failist skoorid ja tagastab String skooridega
    public static String annaSkoori(String failinimi) {
        String skoorid;
        try {
            BufferedReader scanner = new BufferedReader(new FileReader(failinimi));
            String[] skoorideMassiiv = scanner.readLine().split(" ");
            skoorid = "võidud " + skoorideMassiiv[0];
            skoorid = skoorid + " kaotatud " + skoorideMassiiv[1];
            scanner.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return skoorid;
    }

    public static void arvutaSkoor(int võidud, int kaotatud, int punktisumma, boolean kasNullib) {
        String failinimi2 = "skoor.txt";
        int uusVõidud = võidud;
        int uusKaotatud = kaotatud;
        int uusPunktisumma = punktisumma;
        String uuedAndmed = "";

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
        if (!kasNullib) {
            uuedAndmed = uusVõidud + " " + uusKaotatud + " " + uusPunktisumma;
        }
        else if (kasNullib) {
            uuedAndmed = "0 0 100";
        }
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
}