package com.example.hangman_graafiline;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class PildiJoonistamine extends Application {
    private int valestiArvamisiKokku;

    public static void näitaPilti(int valestiArvamisiKokku) {
        Pane pane = new Pane();

        if (valestiArvamisiKokku >= 1) {
            Line vasakPost = new Line(150, 50, 150, 120);
            vasakPost.setVisible(true);
            pane.getChildren().add(vasakPost);
        }
        else if (valestiArvamisiKokku >= 2) {
            Circle pea = new Circle(150, 120, 20);
            pea.setStroke(Color.BLACK);
            pea.setVisible(true);
            pane.getChildren().add(pea);
        }
        else if (valestiArvamisiKokku >= 3) {
            Line keha = new Line(150, 140, 150, 200);
            keha.setVisible(true);
            pane.getChildren().add(keha);
        }
        else if (valestiArvamisiKokku >= 4) {
            Line vasakKäsi = new Line(150, 150, 120, 180);
            vasakKäsi.setVisible(true);
            pane.getChildren().add(vasakKäsi);
        }
        else if (valestiArvamisiKokku >= 5) {
            Line paremKäsi = new Line(150, 150, 180, 180);
            paremKäsi.setVisible(true);
            pane.getChildren().add(paremKäsi);
        }
        else if (valestiArvamisiKokku >= 6) {
            Line vasakJalg = new Line(150, 200, 130, 240);
            vasakJalg.setVisible(true);
            pane.getChildren().add(vasakJalg);
        }
        else if (valestiArvamisiKokku == 7) {
            Line paremJalg = new Line(150, 200, 170, 240);
            paremJalg.setVisible(true);
            pane.getChildren().add(paremJalg);
        }
    }
    @Override
        public void start(Stage primaryStage) {

            Pane hangmanJoonistus = new Pane();
            //ülesehitus (alati nähtavad)
            Line alumineRida = new Line(250, 300, 350, 300);
            alumineRida.setVisible(true);

            Line paremPost = new Line(300, 50, 300, 300);
            paremPost.setVisible(true);

        Line ülemineRida = new Line(150, 50, 300, 50);
        ülemineRida.setVisible(true);

        hangmanJoonistus.getChildren().addAll(alumineRida, paremPost, ülemineRida);
        näitaPilti(valestiArvamisiKokku);

            Scene stseen = new Scene(hangmanJoonistus, 400, 400);
            primaryStage.setTitle("Joonistus");
            primaryStage.setScene(stseen);
            primaryStage.show();

        }
    }