package com.example.azertgabjava;

import com.example.azertgabjava.entities.Equipe;
import com.example.azertgabjava.entities.Match;
import com.example.azertgabjava.entities.Tournoi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

public class HelloController {
    public VBox EquipeBox;
    public TextField inputNomEquipe;
    public Text NbTotalEquipe;
    public VBox TournoiBox;
    public TextField inputNomTournoi;
    public Button btnStartNewTournoiDeathMatch;
    public Button btnStartNewTournoiBrackets;
    public VBox EquipeBox1;
    public Label scoreLocaux;
    public Label scoreVisiteurs;
    public TextField inputScoreEquipe1;
    public TextField inputScoreEquipe2;
    public VBox TournoiBox1;
    public Text listClassement;
    public Text listMatch;
    private ArrayList<Equipe> equipes;
    private ArrayList<Match> matchs;
    private Tournoi tournoi;
    private int actualMatch;
    private char actualPoule;



    @FXML
    protected void initialize() {
        // on init les variables
        equipes = new ArrayList<>();
        tournoi = new Tournoi();
        matchs = new ArrayList<>();

        // on disable les boutons de validation
        allowClicButton();


    }

    public void addNewEquipe() {
        if(!Objects.equals(inputNomEquipe.getText(), "")) {
            equipes.add(new Equipe(inputNomEquipe.getText()));
            inputNomEquipe.clear();
            NbTotalEquipe.setText("total : " + equipes.size());
            this.allowClicButton();
            this.actualiserClassement();
        }
    }

    /**
     * StartNewTournoiDeathMatch : créée un nouveau tournoi en mode deathmatch
     * avec la liste actuelle des équipes
     */
    public void startNewTournoiDeathMatch() throws Exception {
        if(!Objects.equals(inputNomTournoi.getText(), "")) {
            if(equipes.size() > 2) {
                tournoi.init("nomTournoi.getText()", "deathmatch", equipes);
                actualMatch = 0;
                actualiserClassement();
                actualiserMatchs();
            }
        }

    }

    public void startNewTournoiBrackets() throws Exception {
        if(!Objects.equals(inputNomTournoi.getText(), "")) {
            if(equipes.size() > 8) {
                tournoi.init("nomTournoi.getText()", "brackets", equipes);
                actualMatch = 0;
                actualiserClassement();
                actualiserMatchs();
            }
        }
    }

    // enable/disable buttons
    public void allowClicButton() {
        btnStartNewTournoiBrackets.setDisable(true);
        btnStartNewTournoiDeathMatch.setDisable(true);

        if(equipes.size() > 2) {
            btnStartNewTournoiDeathMatch.setDisable(false);
            if(equipes.size() >= 8) {
                btnStartNewTournoiBrackets.setDisable(false);
            }
        }
    }


    public void validerMatch() {

        int scoreE1 = Integer.parseInt(inputScoreEquipe1.getText());
        int scoreE2 = Integer.parseInt(inputScoreEquipe2.getText());

        tournoi.registerMatch(actualMatch, scoreE1, scoreE2);
        actualMatch++;
        actualiserClassement();
        actualiserMatchs();
    }

    public void actualiserClassement() {
        StringBuilder classement = new StringBuilder();
        ArrayList<Equipe> listEquipes;

        if(this.tournoi.getClassement().size() != 0) {
            listEquipes = this.tournoi.getClassement();
        } else {
            listEquipes = equipes;
        }

        for( Equipe equipe : listEquipes) {
            classement.append("\n")
                    .append(equipe.getScore())
                    .append("pts : ")
                    .append(equipe.getNom());
        }

        listClassement.setText(classement.toString());
    }

    public void actualiserMatchs() {
        StringBuilder historique = new StringBuilder();
        ArrayList<Match> listMatchs;

        if(this.tournoi.getMatchs().size() != 0) {
            listMatchs = this.tournoi.getMatchs();
        } else {
            listMatchs = matchs;
        }

        for( Match match : listMatchs) {
            historique.append("\n")
                    .append(match.getEquipe1().getNom())
                    .append(" ")
                    .append(match.getScoreEquipe1())
                    .append(" - ")
                    .append(match.getScoreEquipe2())
                    .append(" ")
                    .append(match.getEquipe2().getNom());
        }
        listMatch.setText(historique.toString());
    }
}