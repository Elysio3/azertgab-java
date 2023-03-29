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
    public Button btnExportJson;
    public Text vainqueur;
    public Button btnValiderMatch;
    private ArrayList<Equipe> equipes;
    private ArrayList<Match> matchs;
    private Tournoi tournoi;
    private int actualMatch;



    @FXML
    protected void initialize() {
        System.out.println("génération de la fenêtre");
        System.out.println("instanciation des variables globales");
        // on init les variables
        equipes = new ArrayList<>();
        tournoi = new Tournoi();
        matchs = new ArrayList<>();
        btnExportJson.setDisable(true);

        // on disable les boutons de validation
        allowClicButton();
    }

    /**
     *
     */
    public void addNewEquipe() {
        System.out.println("clic ajouter Equipe");
        if(!Objects.equals(inputNomEquipe.getText(), "")) {

            System.out.println("Ajout de l'equipe " + inputNomEquipe.getText());
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
        System.out.println("clic startNewTournoiDeathMatch");
        if(!Objects.equals(inputNomTournoi.getText(), "")) {
            if(equipes.size() > 2) {

                System.out.println("Creation tournoi " + inputNomTournoi.getText());
                tournoi.init(inputNomTournoi.getText(), "deathmatch", equipes);

                actualMatch = 0;
                actualiserClassement();
                actualiserMatchs();
            }
        }

    }

    public void startNewTournoiBrackets() throws Exception {
        System.out.println("clic startNewTournoiBrackets");
        if(!Objects.equals(inputNomTournoi.getText(), "")) {
            if(equipes.size() >= 8) {

                System.out.println("Creation tournoi " + inputNomTournoi.getText());
                tournoi.init(inputNomTournoi.getText(), "brackets", equipes);

                actualMatch = 0;
                actualiserClassement();
                actualiserMatchs();
            }
        }
    }

    // enable/disable buttons



    public void validerMatch() {
        System.out.println("Validation Match n°" + actualMatch);


        int scoreE1 = Integer.parseInt(inputScoreEquipe1.getText());
        int scoreE2 = Integer.parseInt(inputScoreEquipe2.getText());

        System.out.println("scores " + scoreE1 + " à " + scoreE2);

        tournoi.registerMatch(actualMatch, scoreE1, scoreE2);
        actualMatch++;

        // on vérifie si c'est la fin des matchs
        // on vérifie si les matchs sont tous validés
        if(tournoi.checkIfAllMatchCompleted()) {
            // si on est sur un brackets
            if(Objects.equals(tournoi.getType(), "brackets")) {
                // si les demies finales ont été créées
                if(tournoi.isDemie()) {
                    // si la finale as été créée
                    if(tournoi.isFinale()) {
                        this.terminerTournoi();
                    } else {
                        // finale pas encore créée
                        // on créée la finale
                        tournoi.createFinale();
                    }
                } else {
                    // demies pas encore créée
                    // on créée les demies
                    tournoi.createDemieFinale();
                }
            } else {
                this.terminerTournoi();
            }
        }
        actualiserClassement();
        actualiserMatchs();
    }

    public void terminerTournoi() {
        btnExportJson.setDisable(false);
        btnValiderMatch.setDisable(true);
        actualiserClassement();
        vainqueur.setText(
                "vainqueur :"
                + tournoi.getClassement().get(0).getNom()
                + " avec "
                + tournoi.getClassement().get(0).getScore()
                + "pts"
        );

    }

    public void actualiserClassement() {
        System.out.println("actualisation du classement");
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
        System.out.println("actualisation liste des matchs");
        StringBuilder historique = new StringBuilder();
        ArrayList<Match> listMatchs;

        if(this.tournoi.getMatchs().size() != 0) {
            listMatchs = this.tournoi.getMatchs();
        } else {
            listMatchs = matchs;
        }

        int i = 0;
        for( Match match : listMatchs) {
            historique.append("\n")
                    .append(match.getPoule())
                    .append(" : ")
                    .append(match.getEquipe1().getNom())
                    .append(" ")
                    .append(match.getScoreEquipe1())
                    .append(" - ")
                    .append(match.getScoreEquipe2())
                    .append(" ")
                    .append(match.getEquipe2().getNom());

            if (actualMatch == i) {
                historique.append(" <-- match actuel en saisie");
            }
            i++;

        }
        listMatch.setText(historique.toString());
    }

    public void allowClicButton() {
        System.out.println("actualisation des boutons creation tournoi");
        btnStartNewTournoiBrackets.setDisable(true);
        btnStartNewTournoiDeathMatch.setDisable(true);

        if(equipes.size() > 2) {
            btnStartNewTournoiDeathMatch.setDisable(false);
            if(equipes.size() >= 8) {
                btnStartNewTournoiBrackets.setDisable(false);
            }
        }
    }

    public void exportJSON() {

    }
}