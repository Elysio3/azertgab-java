package com.example.azertgabjava.entities;

import java.util.ArrayList;
import java.util.Objects;

public class Tournoi {

    private final String TYPE_BRACKETS = "brackets";
    private String type;
    private boolean demie;
    private boolean finale;
    private ArrayList<Equipe> equipes = new ArrayList<>();
    private final ArrayList<Match> matchs = new ArrayList<>();
    private final Poule pouleA = new Poule();
    private final Poule pouleB = new Poule();

    /**
     * fonction d'initialisation d'un tournoi
     * initialise les variables, les poules (si besoin)
     * instancie les matchs et équipes
     * @param type le type du tournoi
     * @param equipes la liste des équipes participantes
     * @throws Exception
     */
    public void init(String type, ArrayList<Equipe> equipes) throws Exception {

        this.type = type;
        // pas de tournoi si moins de 3 équipes
        if(equipes.size() > 2) {

            // pas de tournoi brackets sant minimum de 8 équipes
            if(TYPE_BRACKETS.equals(type) && equipes.size() >= 8) {
                System.out.println("tournoi de type brackets cree");
                this.equipes = equipes;
                this.type = TYPE_BRACKETS;
                this.demie = false;
                this.finale = false;

                // création des deux poules
                Poule pouleA = new Poule('A');
                Poule pouleB = new Poule('B');

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

                // on génère les match de chaque poule
                pouleA.createMatchs();
                pouleB.createMatchs();

                // on récupère les matchs générés
                matchs.addAll(pouleA.getMatchs());
                matchs.addAll(pouleB.getMatchs());
            } else {

                // tournoi classique deathmatch
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

    /**
     * fonction d'actualisation du classement
     * (dans l'ordre des scores)
     */
    public void actualiserClassement() {
        System.out.println("classement actualisé");
        // on range la liste dans l'ordre des scores
        this.equipes.sort(new classementComparator());
    }

    /**
     * fonction de création des matchs du tournoi (tous ceux possibles)
     */
    public void createMatchs() {
        System.out.println("matchs tournoi créés");
        // création de chaque match possible (toute combinaison possible)
        for (int i = 0; i < this.equipes.size(); i++) {
            for (int j = i + 1; j < this.equipes.size(); j++) {
                matchs.add(new Match(this.equipes.get(i), this.equipes.get(j)));
            }
        }
    }
    /**
     * fonction de création des matchs de demi-finale
     */
    public void createDemieFinale() {
        System.out.println("demi finale créée");
        // création des match de quart de finales (8 equipes)
        actualiserClassement();
        matchs.add(new Match(this.equipes.get(0), this.equipes.get(2)));
        matchs.add(new Match(this.equipes.get(1), this.equipes.get(3)));
        this.demie = true;
    }

    /**
     * fonction de création du matchs final
     */
    public void createFinale() {
        System.out.println("finale créée");
        // création des match de quart de finales (8 equipes)
        actualiserClassement();
        matchs.add(new Match(this.equipes.get(0), this.equipes.get(1)));
        this.finale = true;
    }


    /**
     * fonction d'enregistrement des scores d'un match
     * @param idMatch l'id du match
     * @param scoreEquipe1 le score de l'equipe locale
     * @param scoreEquipe2 le score de l'equipe visiteur
     */
    public void registerMatch(int idMatch, int scoreEquipe1, int scoreEquipe2) {

        System.out.println("enregistrement match " + idMatch);

        // on ne modifie seulement si le match n'est pas déjà validé
        if(!this.matchs.get(idMatch).isValide()) {

            // si c'est un tournoi de type brackets, on va chercher l'équipe concernée pour
            // update son score (seulement si c'est un match de poule
            if(TYPE_BRACKETS.equals(this.type)) {

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
                    for(Equipe equipe : this.getPouleB().getEquipes()) {
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

            // on update les scores
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


    /**
     * fonction de test si l'ensemble des matchs sont complétés
     * @return true|false
     */
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

    /**
     * constructeur
     */
    public Tournoi() {}

    public ArrayList<Equipe> getEquipes() {
        return equipes;
    }

    public ArrayList<Match> getMatchs() {
        return matchs;
    }

    public Poule getPouleA() {
        return pouleA;
    }

    public Poule getPouleB() {
        return pouleB;
    }

    public String getType() {
        return type;
    }

    public boolean isDemie() {
        return demie;
    }

    public boolean isFinale() {
        return finale;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "\"Tournoi [type "+ this.type + "]";
    }
}
