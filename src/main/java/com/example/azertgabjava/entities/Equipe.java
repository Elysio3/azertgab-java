package com.example.azertgabjava.entities;

import java.util.ArrayList;

public class Equipe {

    private int id;
    private String nom;
    private ArrayList<Joueur> listeJoueurs;
    private int score;

    // ajouter un joueur dans l'équipe
    public void ajouterJoueur(Joueur joueur) {
        this.listeJoueurs.add(joueur);
    }

    public Equipe(String nom) {
        this.nom = nom;
    }
    public Equipe() {}

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

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

    public ArrayList<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public void setListeJoueurs(ArrayList<Joueur> listeJoueurs) {
        this.listeJoueurs = listeJoueurs;
    }
}
