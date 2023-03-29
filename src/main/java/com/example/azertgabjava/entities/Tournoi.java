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

    /**
     * fonction d'initialisation du tournoi
     * @param nom le nom du tournoi
     * @param type le type du tournoi (brackets ou deathmatch)
     * @param equipes la liste des équipes
     * @throws Exception
     */
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
                System.out.println("tournoi de type brackets cree");
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
                System.out.println("tournoi de type deathmatch cree");
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
        System.out.println("classement actualisé");
        this.equipes.sort(new classementComparator());
    }

    public void createMatchs() {
        System.out.println("matchs tournoi créés");
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
        System.out.println("demi finale créée");
        // création des match de quart de finales (8 equipes)
        actualiserClassement();
        matchs.add(new Match(id, this.equipes.get(0), this.equipes.get(2)));
        matchs.add(new Match(id, this.equipes.get(1), this.equipes.get(3)));
    }

    public void createFinale() {
        System.out.println("finale créée");
        // création des match de quart de finales (8 equipes)
        actualiserClassement();
        matchs.add(new Match(id, this.equipes.get(0), this.equipes.get(1)));
    }


    public void registerMatch(int idMatch, int scoreEquipe1, int scoreEquipe2) {

        System.out.println("enregistrement match " + idMatch);

        // on ne modifie seulement si le match n'est pas déjà validé
        if(!this.matchs.get(idMatch).isValide()) {
            // si tournoi de type bracket, on actualise les phase de poule si elles ne sont pas terminées
            if(TYPE_BRACKETS.equals(this.type)) {

                // si c'est un match de poule
                // et si les matchs de cette poule ne sont pas fini
                // on va chercher l'équipe concernée par le match
                // on update ses scores totaux
                if(matchs.get(idMatch).getPoule() == 'A') {
                    System.out.println("Match de poule A");
                    int i = 0;
                    // on va chercher l'équipe correspondante au match depuis les poules
                    for(Equipe equipe : this.getPouleA().getEquipes()) {
                        if(Objects.equals(equipe.getNom(), matchs.get(idMatch).getEquipe1().getNom())) {
                            equipe.addScore(scoreEquipe1);
                            System.out.println("locaux " + equipe.getNom()
                                    + " score " + scoreEquipe1
                                    + " total " + equipe.getScore());
                        }
                        if(Objects.equals(equipe.getNom(), matchs.get(idMatch).getEquipe2().getNom())) {
                            equipe.addScore(scoreEquipe2);
                            System.out.println("visiteurs " + equipe.getNom()
                                    + " score " + scoreEquipe2
                                    + " total " + equipe.getScore());
                        }
                        i++;
                    }
                }

                // même chose si poule B
                if(matchs.get(idMatch).getPoule() == 'B') {
                    System.out.println("Match de poule B");
                    int i = 0;
                    // on va chercher l'équipe correspondante au match depuis les poules
                    for(Equipe equipe : this.getPouleA().getEquipes()) {
                        if(Objects.equals(equipe.getNom(), matchs.get(idMatch).getEquipe2().getNom())) {
                            equipe.addScore(scoreEquipe2);
                            System.out.println("locaux " + equipe.getNom()
                                    + " score " + scoreEquipe1
                                    + " total " + equipe.getScore());
                        }
                        if(Objects.equals(equipe.getNom(), matchs.get(idMatch).getEquipe1().getNom())) {
                            equipe.addScore(scoreEquipe2);
                            System.out.println("visiteurs " + equipe.getNom()
                                    + " score " + scoreEquipe2
                                    + " total " + equipe.getScore());
                        }
                        i++;
                    }
                }
            }

            System.out.println("Actualisation des scores");
            // on met à jour le match dans la liste des match du tournoi
            matchs.get(idMatch).setScoreEquipe1(scoreEquipe1);
            matchs.get(idMatch).getEquipe1().addScore(scoreEquipe1);

            matchs.get(idMatch).setScoreEquipe2(scoreEquipe2);
            matchs.get(idMatch).getEquipe2().addScore(scoreEquipe2);

            matchs.get(idMatch).setValide(true);
        }

        actualiserClassement();
    }


    public boolean checkIfAllMatchCompleted() {
        boolean allCompleted = true;
        for(Match match : this.matchs) {
            if (!match.isValide()) {
                allCompleted = false;
                break;
            }
        }
        return  allCompleted;
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

    public boolean isDemie() {
        return demie;
    }

    public void setDemie(boolean demie) {
        this.demie = demie;
    }

    public boolean isFinale() {
        return finale;
    }

    public void setFinale(boolean finale) {
        this.finale = finale;
    }
}
