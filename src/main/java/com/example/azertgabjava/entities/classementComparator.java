package com.example.azertgabjava.entities;

import java.util.Comparator;

// utilisé pour trier les classement dans l'ordre des scores (paramètre comparator de la fonction sort())
public class classementComparator implements Comparator<Equipe> {

    // override the compare() method
    public int compare(Equipe e1, Equipe e2)
    {
        if (e1.getScore() == e2.getScore())
            return 0;
        else if (e1.getScore() > e2.getScore())
            return -1;
        else
            return 1;
    }
}
