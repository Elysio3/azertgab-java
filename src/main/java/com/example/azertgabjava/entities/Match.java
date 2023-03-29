package com.example.azertgabjava.entities;

public class Match {
    private Equipe equipe1;
    private Equipe equipe2;
    private int scoreEquipe1;
    private int scoreEquipe2;
    private char poule;

    public char getPoule() {
        return poule;
    }

    public void setPoule(char poule) {
        this.poule = poule;
    }

    private boolean valide;

    /**
     * constructeir
     * @param equipe1 l'equipe locale
     * @param equipe2 l'equipe visiteur
     */
    public Match(Equipe equipe1, Equipe equipe2) {
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.scoreEquipe1 = 0;
        this.scoreEquipe2 = 0;
        this.valide = false;
    }

    public Match() {}

    public Equipe getEquipe1() {
        return equipe1;
    }

    public Equipe getEquipe2() {
        return equipe2;
    }

    public int getScoreEquipe1() {
        return scoreEquipe1;
    }

    public void setScoreEquipe1(int scoreEquipe1) {
        this.scoreEquipe1 = scoreEquipe1;
    }

    public int getScoreEquipe2() {
        return scoreEquipe2;
    }

    public void setScoreEquipe2(int scoreEquipe2) {
        this.scoreEquipe2 = scoreEquipe2;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "\"Match [equipe1 "+ this.equipe1
                + ", equipe2 = " + this.equipe2
                + ", scoreEquipe1 "+ this.scoreEquipe1
                + ", scoreEquipe2 = " + this.scoreEquipe2
                + "]";
    }
}
