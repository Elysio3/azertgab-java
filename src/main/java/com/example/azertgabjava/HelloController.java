package com.example.azertgabjava;

import com.example.azertgabjava.entities.Equipe;
import com.example.azertgabjava.entities.Match;
import com.example.azertgabjava.entities.Tournoi;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HelloController {
    public VBox EquipeBox;
    public TextField inputNomEquipe;
    public Text NbTotalEquipe;
    public VBox TournoiBox;
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


    /**
     * à la création de la fenêtre
     */
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
     * au clic, ajouter une équipe dans la liste actuelle
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
        if(equipes.size() > 2) {

            System.out.println("Creation tournoi type deathmatch");
            tournoi.init( "deathmatch", equipes);

            actualMatch = 0;
            actualiserClassement();
            actualiserMatchs();
        }
    }

    /**
     * startNewTournoiBrackets : créée un nouveau tournoi en mode brackets
     * avec la liste actuelle des équipes
     */
    public void startNewTournoiBrackets() throws Exception {
        System.out.println("clic startNewTournoiBrackets");
        if(equipes.size() >= 8) {

            System.out.println("Creation tournoi type brackets");
            tournoi.init("brackets", equipes);

            actualMatch = 0;
            actualiserClassement();
            actualiserMatchs();
        }

    }

    /**
     * au clic, on récupère les scores en entrée et on update le match
     */
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

    /**
     * au clic, on termine le tournoi (et possibilité de export json)
     */
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

    /**
     * on actualise l'affichage du classement
     */
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

    /**
     * on actualise l'affichage des matchs
     */
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
        int j = 0;
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
            j++;
            if(j == 20) {
                j = 0;
                historique = new StringBuilder();
            }
        }
        listMatch.setText(historique.toString());


    }

    /**
     * on vérifie l'accessibilité des boutons (selon le nombre d'équipes)
     */
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

    /**
     * fonction d'export sous fichier json de résumé du tournoi
     */
    public void exportJSON() {
        ObjectMapper Obj = new ObjectMapper();
        try {
            StringBuilder jsonStr = new StringBuilder();
            // infos tournoi
            jsonStr.append("{");
            jsonStr.append(Obj.writeValueAsString(this.tournoi));
            jsonStr.append(",");
            // liste equipes (dans ordre classement
            for (Equipe equipe : tournoi.getEquipes()) {
                jsonStr.append(Obj.writeValueAsString(equipe));
            }
            jsonStr.append(",");
            // liste des matchs (ordre chronologique
            for (Match match : tournoi.getMatchs()) {
                jsonStr.append(Obj.writeValueAsString(match));
            }
            jsonStr.append("}");
            System.out.println(jsonStr);

            FileWriter file = new FileWriter("C:\\Users\\mkerviche\\Downloads\\tournoi.json");
            file.write(String.valueOf(jsonStr));

            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}