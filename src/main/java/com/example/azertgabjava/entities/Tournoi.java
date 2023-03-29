package com.example.azertgabjava.entities;

import java.util.ArrayList;
import java.util.Objects;

public class Tournoi {

    private final String TYPE_BRACKETS = "brackets";
    private int id;
    private String nom;
    private String type;
    private boolean demie;
    private boolean finale;
    private ArrayList<Equipe> equipes = new ArrayList<>();
    private ArrayList<Match> matchs = new ArrayList<>();
    private Poule pouleA = new Poule();
    private Poule pouleB = new Poule();

    // on initialise le tournoi
    // le type (arbre ou deathmatch)
    // les équipes
    // les matchs
    public void init(String nom, String type, ArrayList<Equipe> equipes) throws Exception{
        this.nom = nom;
        this.type = type;
        // liste d'équipe définie
        if(equipes.size() > 2) {
            // initialise les scores à 0
            for (Equipe equipe : equipes) {
                equipe.setScore(0);
            }

            // on ne peut faire un tournoi brackets sans un minimum d'équipes
            if(TYPE_BRACKETS.equals(type) && equipes.size() >= 8) {
                this.equipes = equipes;
                this.type = TYPE_BRACKETS;
                this.demie = false;
                this.finale = false;
                Poule pouleA = new Poule(1, "Poule A", 'A');
                Poule pouleB = new Poule(2, "Poule B", 'B');

                int i = 1;
                for (Equipe equipe : equipes) {
                    // on ajoute les équipes dans les poules (pair = A, impair = B, start i=1)
                    if (i % 2 == 0) {
                        pouleA.addEquipe(equipe);
                    } else {
                        pouleB.addEquipe(equipe);
                    }
                    i++;
                }
                // on créée les match de chaque poule
                pouleA.createMatchs();
                pouleB.createMatchs();

                // on ajoute ces matchs généré dans
                // la liste des match du tournoi
                matchs.addAll(pouleA.getMatchs());
                matchs.addAll(pouleB.getMatchs());
            } else {
                this.equipes = equipes;
                this.type = "deathmatch";
                this.createMatchs();
            }
            // on actualise le classement au score (0 à l'init)
            actualiserClassement();

        // erreur sur (ArrayList equipes <= 2)
        } else {
            throw new Exception("Aucune / pas assez d'équipes en entrée");
        }
    }

    public ArrayList<Equipe> getClassement() {
        actualiserClassement();
        return equipes;
    }

    public void actualiserClassement() {
        this.equipes.sort(new classementComparator());
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
    public void createDemieFinale() {
        // création des match de quart de finales (8 equipes)
        actualiserClassement();
        matchs.add(new Match(id, this.equipes.get(0), this.equipes.get(2)));
        matchs.add(new Match(id, this.equipes.get(1), this.equipes.get(3)));
    }

    public void createFinale() {
        // création des match de quart de finales (8 equipes)
        actualiserClassement();
        matchs.add(new Match(id, this.equipes.get(0), this.equipes.get(1)));
    }


    public void registerMatch(int idMatch, int scoreEquipe1, int scoreEquipe2) {

        // on ne modifie seulement si le match n'est pas déjà validé
        if(!this.matchs.get(idMatch).isValide()) {
            // si tournoi de type bracket, on actualise les phase de poule si elles ne sont pas terminées
            if(TYPE_BRACKETS.equals(this.type)) {

                // si c'est un match de poule
                // si les matchs de cette poule ne sont pas fini
                // on update classement Poule
                if(matchs.get(idMatch).getPoule() == 'A' && !this.pouleA.checkIfAllMatchCompleted()) {
                    int i = 0;
                    for(Equipe equipe : this.getPouleA().getEquipes()) {
                        if(Objects.equals(equipe.getNom(), matchs.get(idMatch).getEquipe1().getNom())) {
                            this.pouleA.getEquipes().get(i).addScore(scoreEquipe1);
                        }
                        if(Objects.equals(equipe.getNom(), matchs.get(idMatch).getEquipe2().getNom())) {
                            this.pouleA.getEquipes().get(i).addScore(scoreEquipe2);
                        }
                        i++;
                    }
                }

                if(matchs.get(idMatch).getPoule() == 'B' && !this.pouleB.checkIfAllMatchCompleted()) {
                    int i = 0;
                    for(Equipe equipe : this.getPouleB().getEquipes()) {
                        if(Objects.equals(equipe.getNom(), matchs.get(idMatch).getEquipe1().getNom())) {
                            this.pouleB.getEquipes().get(i).addScore(scoreEquipe1);
                        }
                        if(Objects.equals(equipe.getNom(), matchs.get(idMatch).getEquipe2().getNom())) {
                            this.pouleB.getEquipes().get(i).addScore(scoreEquipe2);
                        }
                        i++;
                    }
                }

                // on met à jour le match dans la liste des match du tournoi
                matchs.get(idMatch).setScoreEquipe1(scoreEquipe1);
                matchs.get(idMatch).getEquipe1().addScore(scoreEquipe1);

                matchs.get(idMatch).setScoreEquipe2(scoreEquipe2);
                matchs.get(idMatch).getEquipe2().addScore(scoreEquipe2);

                matchs.get(idMatch).setValide(true);
            }
        }
        actualiserClassement();
    }


    public Tournoi() {}

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

    public Poule getPouleA() {
        return pouleA;
    }

    public void setPouleA(Poule pouleA) {
        this.pouleA = pouleA;
    }

    public Poule getPouleB() {
        return pouleB;
    }

    public void setPouleB(Poule pouleB) {
        this.pouleB = pouleB;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
