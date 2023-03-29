package com.example.azertgabjava.entities;

import java.util.ArrayList;

public class Poule {
    private char lettre;
    private final ArrayList<Equipe> equipes = new ArrayList<>();
    private final ArrayList<Match> matchs = new ArrayList<>();

    /**
     * constructeur
     * @param lettre la lettre (repère) de la poule
     */
    public Poule(char lettre) {
        this.lettre = lettre;
    }

    /**
     * fonction d'ajout d'une equipe dans une poule
     * @param equipe l'equipe à ajouter
     */
    public void addEquipe(Equipe equipe) {
        this.equipes.add(equipe);
    }

    /**
     * fonction de création des matchs de la poule (tous ceux possibles)
     */
    public void createMatchs() {
        // création de chaque match possible (toute combinaison possible)
        for (int i = 0; i < this.equipes.size(); i++) {
            for (int j = i + 1; j < this.equipes.size(); j++) {
                matchs.add(new Match(this.equipes.get(i), this.equipes.get(j)));
                matchs.get(i).setPoule(this.lettre);
            }
        }
    }
    public Poule() {}

    public ArrayList<Equipe> getEquipes() {
        return equipes;
    }

    public ArrayList<Match> getMatchs() {
        return matchs;
    }
}
