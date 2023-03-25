import com.example.azertgabjava.entities.Equipe;
import com.example.azertgabjava.entities.Tournoi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TournoiTest {

    private Tournoi tournoi;

    @BeforeEach
    public void setup() {
        tournoi = new Tournoi();
    }

    /*
    inactive, need exeption handling

    @Test
    public void should_init_tournoi_name() throws Exception {
        tournoi.init("Tournoi 1", null, new ArrayList<Equipe>());
        assertThat(tournoi.getNom().equals("Tournoi 1"));
    }

    @Test
    public void should_init_tournoi_type() throws Exception {
        tournoi.init("Tournoi 1", "brackets", new ArrayList<Equipe>());
        assertThat(tournoi.getType().equals("brackets"));

        tournoi.init("Tournoi 1", "deathmatch", new ArrayList<Equipe>());
        assertThat(tournoi.getType().equals("deathmatch"));
    }
    */

    @Test
    public void should_init_tournoi_brackets() throws Exception {

        ArrayList<Equipe> equipes = new ArrayList<>();
        equipes.add(new Equipe("equipe1"));
        equipes.add(new Equipe("equipe2"));
        equipes.add(new Equipe("equipe3"));
        equipes.add(new Equipe("equipe4"));

        tournoi.init("Tournoi 1", "brackets", equipes);
        assertThat(tournoi.getType().equals("brackets"));


    }




}
