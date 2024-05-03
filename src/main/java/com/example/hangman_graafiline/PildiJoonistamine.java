package com.example.hangman_graafiline;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class PildiJoonistamine extends Application {
        private int valestiArvamisiKokku = 0;

        @Override
        public void start(Stage primaryStage) {

            Pane pane = new Pane();

            //ülesehitus (alati nähtavad)
            Line alumineRida = new Line(250, 300, 350, 300);
            alumineRida.setVisible(true);
            pane.getChildren().add(alumineRida);

            Line paremPost = new Line(300, 50, 300, 300);
            paremPost.setVisible(true);
            pane.getChildren().add(paremPost);

            Line ülemineRida = new Line(150, 50, 300, 50);
            ülemineRida.setVisible(true);
            pane.getChildren().add(ülemineRida);

            //noose/vasakPost
            Line vasakPost = new Line(150, 50, 150, 120);
            vasakPost.setVisible(false);
            pane.getChildren().add(vasakPost);

            Circle pea = new Circle(150, 120, 20);
            pea.setStroke(Color.BLACK);
            pea.setVisible(false);
            pane.getChildren().add(pea);

            Line keha = new Line(150, 140, 150, 200);
            keha.setVisible(false);
            pane.getChildren().add(keha);

            Line vasakKäsi = new Line(150, 150, 120, 180);
            vasakKäsi.setVisible(false);
            pane.getChildren().add(vasakKäsi);

            Line paremKäsi = new Line(150, 150, 180, 180);
            paremKäsi.setVisible(false);
            pane.getChildren().add(paremKäsi);

            Line vasakJalg = new Line(150, 200, 130, 240);
            vasakJalg.setVisible(false);
            pane.getChildren().add(vasakJalg);

            Line paremJalg = new Line(150, 200, 170, 240);
            paremJalg.setVisible(false);
            pane.getChildren().add(paremJalg);

            Scene stseen = new Scene(pane, 400, 400);
            primaryStage.setTitle("Joonistus");
            primaryStage.setScene(stseen);
            primaryStage.show();

            näitaPilti(vasakPost,pea, keha, vasakKäsi, paremKäsi, vasakJalg, paremJalg); //<-- see tuleb kuskil mujal välja kutsuda
        }

    /**
     * iga valestiarvamisega teeb midagi nähtavaks
     */
        private void näitaPilti(Line vasakPost, Circle pea, Line keha, Line vasakKäsi, Line paremKäsi, Line vasakJalg, Line paremJalg) {
            if (valestiArvamisiKokku == 1) vasakPost.setVisible(true);
            if (valestiArvamisiKokku == 2) pea.setVisible(true);
            if (valestiArvamisiKokku == 3) keha.setVisible(true);
            if (valestiArvamisiKokku == 4) vasakKäsi.setVisible(true);
            if (valestiArvamisiKokku == 5) paremKäsi.setVisible(true);
            if (valestiArvamisiKokku == 6) vasakJalg.setVisible(true);
            if (valestiArvamisiKokku == 7) paremJalg.setVisible(true);
        }
    }