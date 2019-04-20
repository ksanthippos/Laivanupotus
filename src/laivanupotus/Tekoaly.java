package laivanupotus;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tekoaly {

    private Laivastot laivastot;
    private int[][] varatutKopio;
    private int[] edellinenOsuma;
    private int ampumaSuunta;    // ei suuntaa = 0; 1 = ylös, 2 = oikea, 3 = alas, 4 = vasen

    private List<int[]> edellisetOsumat;
    private boolean tuliOsuma;
    private boolean tuhottiin;


    public Tekoaly(Laivastot laivastot) {
        this.laivastot = laivastot;
        this.edellisetOsumat = new ArrayList<>();
        this.ampumaSuunta = 0;

        varatutKopio = new int[12][12];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++)
                varatutKopio[i][j] = 0;
        }

    }

/*
    // tallennetaan edelliset osumat listaan
    public void tallennaOsuma(int[] koordinaatti) {
        tuliOsuma = true;
        edellisetOsumat.add(koordinaatti);
    }*/

    public void ammuttiinOhi() {
        tuliOsuma = false;
    }


/*    // tämän pitää tarkistaa, onko kahdella peräkkäisellä osumalla vierekkäisiä koordinaatteja
    public boolean tarkistaEdellisetOsumat() {
        if (edellisetOsumat.size() == 2) {
            return true;
            // jatkuu,,
        }
        return false;
    }*/


/*
    public void setAmpumaSuunta(int suunta) {
        ampumaSuunta = suunta;
    }

    public void setTuhottiin() {
        tuhottiin = true;
    }*/


    // palauttaa joko 1, -1 tai -2
    public int mitaRuudussaOn(int[] koordinaatti) {
        return laivastot.getOmatVaratutRuudut()[koordinaatti[0]][koordinaatti[1]];
    }



    public int[] tekoAlyAmpuu() {

        Random random = new Random();
        int[] maali = new int[2];
        maali[0] = random.nextInt(10);
        maali[1] = random.nextInt(10);

        /*
        asetetaan varattujen ruutujen taulukko isomman 12x12 - taulukon sisään, jotta ruutujen ympäristön voi tutkia
        helposti ilman ongelmia taulukon rajojen ylityksestä
        */
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++)
                varatutKopio[i + 1][j + 1] = laivastot.getOmatVaratutRuudut()[i][j];
        }

        // edellisellä kerralla ei osuttu mihinkään --> ammutaan satunnaiseen ruutuun
        if (!tuliOsuma) {
            while (true) {
                if (mitaRuudussaOn(maali) < 0) {    // arvotaan uusi maali
                    maali[0] = random.nextInt(10);
                    maali[1] = random.nextInt(10);
                    continue;
                } else if (mitaRuudussaOn(maali) == 1) {
                    tuliOsuma = true;
                    edellinenOsuma = maali;
                    return maali;
                }
                else if (mitaRuudussaOn(maali) == 0)
                    return maali;
            }
        }

        // edellisellä kerralla osutiin, mutta suunta ei vielä tiedossa --> ammutaan edellisen osumakohdan ympäröiviin ruutuihin satunnaisesti
        else if (tuliOsuma && ampumaSuunta == 0) {
            int x = edellinenOsuma[0];
            int y = edellinenOsuma[1];
            List<int[]> arvottavat = new ArrayList<>();

            // laitetaan osumakohtaa ympäröivät koordinaatit listaan
            for (int i = x -1; i < x + 2; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    if (i == x && j == y)
                        continue;

                    int[] arvo = {i, j};
                    arvottavat.add(arvo);
                }
            }

            // arvottavien joukosta valitaan ja palautetaan oikeanlainen luku (= ei vanha ruutu)
            while (true) {
                int valinta = random.nextInt(arvottavat.size() + 1);
                maali = arvottavat.get(valinta);

                if (mitaRuudussaOn(maali) < 0) {    // arvotaan uusi maali
                    arvottavat.remove(maali);
                    continue;

                } else if (mitaRuudussaOn(maali) == 1) {

                    tuliOsuma = false;
                    // suunnan asettaminen tähän
                    //tuliOsuma = true;
                    //edellinenOsuma = maali;

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
