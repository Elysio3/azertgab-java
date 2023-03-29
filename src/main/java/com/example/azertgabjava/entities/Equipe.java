package com.example.azertgabjava.entities;

public class Equipe {
    private String nom;

    private int score;

    /**
     * constructeur
     * on set le score à 0 (init)
     * @param nom le nom de l'equipe
     */
    public Equipe(String nom) {
        this.nom = nom;
        this.score = 0;
    }
    public Equipe() {}

    /**
     * fonction d'ajout d'un score au score total de l'equipe
     * @param score le score à ajouter
     */
    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "\"Equipe [Nom "+ this.nom + ", Score = " + this.score + "]";
    }
}
