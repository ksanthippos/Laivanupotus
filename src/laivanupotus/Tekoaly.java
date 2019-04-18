package laivanupotus;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tekoaly {

    private Laivastot laivastot;
    private List<int[]> edellisetOsumat;
    private int ampumaSuunta;
    private boolean tuliOsuma;
    private boolean tuhottiin;


    public Tekoaly(Laivastot laivastot) {
        this.laivastot = laivastot;
        this.edellisetOsumat = new ArrayList<>();
    }


    // tallennetaan edelliset osumat listaan
    public void tallennaOsuma(int[] koordinaatti) {
        tuliOsuma = true;
        edellisetOsumat.add(koordinaatti);
    }

    public void ammuttiinOhi() {
        tuliOsuma = false;
    }


    // tämän pitää tarkistaa, onko kahdella peräkkäisellä osumalla vierekkäisiä koordinaatteja
    public boolean tarkistaEdellisetOsumat() {
        if (edellisetOsumat.size() == 2) {
            return true;
            // jatkuu,,
        }
        return false;
    }



    public void setAmpumaSuunta(int suunta) {
        ampumaSuunta = suunta;
    }

    public void setTuhottiin() {
        tuhottiin = true;
    }


    // palauttaa joko 1, -1 tai -2
    public int mitaRuudussaOn(int[] koordinaatti) {
        return laivastot.getOmatVaratutRuudut()[koordinaatti[0]][koordinaatti[1]];
    }



    public int[] tekoAlyAmpuu() {

        Random random = new Random();


        int[] maali = new int[2];
        maali[0] = random.nextInt(10);
        maali[1] = random.nextInt(10);

        // edellisellä kerralla ei osuttu mihinkään --> ammutaan satunnaiseen ruutuun
        if (!tuliOsuma) {
            while (true) {
                if (mitaRuudussaOn(maali) < 0) {    // arvotaan uusi maali
                    maali[0] = random.nextInt(10);
                    maali[1] = random.nextInt(10);
                    continue;
                } else if (mitaRuudussaOn(maali) == 1) {
                    return maali;
                }
                else if (mitaRuudussaOn(maali) == 0)
                    return maali;
            }
        }



        // miten saa tiedon siitä, että edellinen laiva tuhottiin
        // vertaillaan viimeksi tuhottujen ja nyt tuhottujen laivojen määrää

        // vai olisiko hyvä katsoa edellisen osumakohdan ympärille: tutkittaisiin laivastot-luokan
        // jo ammuttujen ruutujen taulukkoa, jos osumakohdan ympärillä on nollia, niin ammutaan ensin satunnaisesti
        // niistä sellaiseen, joka sijaitsee osumakohdan vieressä. jos ei tule uutta osumaa, niin jatketaan seuraavaan
        // vapaaseen. kun tulee osuma ja uppoaa --> lopetetaan tämän alueen ampuminen ja arvotaan uusi. jos tulee osuma
        // eikä uppoa, jatketaan samaan suuntaan kunnes upotetaan






        return new int[2];


    }


}
