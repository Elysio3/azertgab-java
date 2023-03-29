package com.example.azertgabjava;

import com.example.azertgabjava.entities.Equipe;
import com.example.azertgabjava.entities.Match;
import com.example.azertgabjava.entities.Tournoi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws Exception {
        launch();

        /*
        Tournoi tournoi = new Tournoi();
        ArrayList<Equipe> equipes = new ArrayList<>();
        equipes.add(new Equipe("equipe1"));
        equipes.add(new Equipe("equipe2"));
        equipes.add(new Equipe("equipe3"));
        equipes.add(new Equipe("equipe4"));

        tournoi.init("Tournoi 1", "brackets", equipes);

        printMatches(tournoi);
        tournoi.registerMatch('X', 0, 1, 2);

        System.out.println("------------------------------------------");
        printMatches(tournoi);

        printClassement(tournoi);
        */

    }

    private static void printMatches(Tournoi tournoi) {
        for(Match match : tournoi.getMatchs()) {
            System.out.println(" Match :" + match.getEquipe1().getNom() + " contre " + match.getEquipe2().getNom());
            System.out.println(" score :" + match.getScoreEquipe1() + " à " + match.getScoreEquipe2());
            System.out.println(" totaux :" + match.getEquipe1().getScore() + " à " + match.getEquipe2().getScore());
            System.out.println("");
        }
    }

    private static void printClassement(Tournoi tournoi) {
        int i = 1;
        for(Equipe equipe : tournoi.getClassement()) {
            System.out.println(" Position :" + i + equipe.getNom() + " score : " + equipe.getScore());
            System.out.println("");
            i++;
        }
    }
}