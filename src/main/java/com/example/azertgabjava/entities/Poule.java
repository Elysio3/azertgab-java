package com.example.azertgabjava.entities;

import java.util.ArrayList;
import java.util.List;

public class Poule {

    private int id;
    private String nom;
    private ArrayList<Equipe> equipes = new ArrayList<>();
    private ArrayList<Match> matchs = new ArrayList<>();

    public Poule(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public void addEquipe(Equipe equipe) {
        this.equipes.add(equipe);
    }

    public void createMatchs() {
        // création de chaque match possible (toute combinaison possible)
        int id = 0;
        for (int i = 0; i < this.equipes.size(); i++) {
            for (int j = i + 1; j < this.equipes.size(); j++) {
                id++;
                matchs.add(new Match(id, this.equipes.get(i), this.equipes.get(j)));
            }
        }
    }

    public void registerMatch(int idMatch, int scoreEquipe1, int scoreEquipe2) {

        // on ne modifie seulement si le match n'est pas déjà validé
        if(!this.matchs.get(idMatch).isValide()) {
            this.matchs.get(idMatch).setScoreEquipe1(scoreEquipe1);
            this.matchs.get(idMatch).getEquipe1().addScore(scoreEquipe1);

            this.matchs.get(idMatch).setScoreEquipe2(scoreEquipe2);
            this.matchs.get(idMatch).getEquipe2().addScore(scoreEquipe2);

            this.matchs.get(idMatch).setValide(true);
        }
        actualiserClassement();
    }

    public void actualiserClassement() {
        this.equipes.sort(new classementComparator());
    }

    public boolean checkIfAllMatchCompleted() {
        boolean allCompleted = true;
        for(Match match : this.matchs) {
            if(!match.isValide()) allCompleted = false;
        }
        return  allCompleted;
    }

    public Poule() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(ArrayList<Equipe> equipes) {
        this.equipes = equipes;
    }

    public ArrayList<Match> getMatchs() {
        return matchs;
    }

    public void setMatchs(ArrayList<Match> matchs) {
        this.matchs = matchs;
    }
}
