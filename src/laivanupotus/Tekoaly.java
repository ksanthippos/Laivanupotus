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
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++)
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

/*    public int tutkiViereinen(int[] koordinaatti) {
        return varatutKopio[koordinaatti[0]][koordinaatti[1]];
    }*/

    // usein käytetty apumetodi
    public int[] ammuSatunnaiseenMaaliin() {

        Random random = new Random();
        int[] maali = new int[2];

        while (true) {
            maali[0] = random.nextInt(10);
            maali[1] = random.nextInt(10);
            if (mitaRuudussaOn(maali) < 0)  // arvotaan uusi maali
                continue;

            else if (mitaRuudussaOn(maali) == 1) {
                tuliOsuma = true;
                edellinenOsuma = maali;
                return maali;
            }

            else if (mitaRuudussaOn(maali) == 0) {
                tuliOsuma = false;
                return maali;
            }
        }
    }



    public int[] tekoAlyAmpuu() {

        Random random = new Random();
        List<int[]> arvottavat = new ArrayList<>();
        int[] maali = new int[2];
        int x, y;

        /*
        asetetaan varattujen ruutujen taulukko isomman 12x12 - taulukon sisään, jotta ruutujen ympäristön voi tutkia
        helposti ilman ongelmia taulukon rajojen ylityksestä
        */
/*        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++)
                varatutKopio[i + 1][j + 1] = laivastot.getOmatVaratutRuudut()[i][j];
        }*/

        // edellisellä kerralla ei osuttu mihinkään --> ammutaan satunnaiseen ruutuun
        if (!tuliOsuma) {
            return ammuSatunnaiseenMaaliin();
        }

        // edellisellä kerralla osutiin, mutta suunta ei vielä tiedossa --> ammutaan edellisen osumakohdan ympäröiviin ruutuihin satunnaisesti
        else if (tuliOsuma && ampumaSuunta == 0) {

            x = edellinenOsuma[0];
            y = edellinenOsuma[1];
            arvottavat.clear();

            // osumakohdasta ylös, alas, oikealle ja vasemmalle
            if (x > 0 && x < 9 && y > 0 && y < 9) {
                arvottavat.add(new int[]{x, y - 1});
                arvottavat.add(new int[]{x - 1, y});
                arvottavat.add(new int[]{x + 1, y});
                arvottavat.add(new int[]{x, y + 1});
            }
            // vasemmassa reunassa mutta ei nurkissa
            else if (x == 0 && y > 0 && y < 8) {
                arvottavat.add(new int[]{x + 1, y});
                arvottavat.add(new int[]{x, y - 1});
                arvottavat.add(new int[]{x, y + 1});
            }
            // oikeassa reunassa mutta ei nurkissa
            else if (x == 9 && y > 0 && y < 8) {
                arvottavat.add(new int[]{x - 1, y});
                arvottavat.add(new int[]{x, y - 1});
                arvottavat.add(new int[]{x, y + 1});
            }
            // yläreunassa mutta ei nurkissa
            else if (y == 0 && x > 0 && x < 8) {
                arvottavat.add(new int[]{x - 1, y});
                arvottavat.add(new int[]{x + 1, y});
                arvottavat.add(new int[]{x, y + 1});
            }
            // alareunassa mutta ei nurkissa
            else if (y == 9 && x > 0 && x < 8) {
                arvottavat.add(new int[]{x - 1, y});
                arvottavat.add(new int[]{x + 1, y});
                arvottavat.add(new int[]{x, y - 1});
            }
            // vasen ylänurkka
            else if (x == 0 && y == 0) {
                arvottavat.add(new int[]{x + 1, y});
                arvottavat.add(new int[]{x, y + 1});
            }
            // oikea ylänurkka
            else if (x == 9 && y == 0) {
                arvottavat.add(new int[]{x - 1, y});
                arvottavat.add(new int[]{x, y + 1});
            }
            // vasen alanurkka
            else if (x == 0 && y == 9) {
                arvottavat.add(new int[]{x + 1, y});
                arvottavat.add(new int[]{x, y - 1});
            }
            // oikea alanurkka
            else if (x == 9 && y == 9) {
                arvottavat.add(new int[]{x - 1, y});
                arvottavat.add(new int[]{x, y - 1});
            }



            // laitetaan osumakohtaa ympäröivät koordinaatit listaan
/*            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    // ei tutkita keskikohtaa eikä vinottain vierekkäisiä
                    if ((i == x && i == y) || (i == x - 1 && j == y - 1) || (i == x - 1 && j == y + 1) || (i == x + 1 && j == y - 1)
                    || (i == x + 1 && j == y + 1))
                        continue;
                    else {
                        int[] arvo = {i, j};
                        arvottavat.add(arvo);
                    }
                }
            }*/


            while (true) {

                // jos ei ole viereisiä ruutuja, niin jatketaan ampumista muualla
                if (arvottavat.size() == 0)
                    return ammuSatunnaiseenMaaliin();

                int valinta = random.nextInt(arvottavat.size());
                maali = arvottavat.get(valinta);

                if (mitaRuudussaOn(maali) < 0) {    // maaliin ammuttu jo aiemmin
                    arvottavat.remove(maali);
                    continue;   // arvotaan uusi maali jäljellä olevien joukosta
                }
                else if (mitaRuudussaOn(maali) == 0) {
                    tuliOsuma = false;
                    return maali;
                }
                /*                } else if (mitaRuudussaOn(maali) == 1) {


                 *//*suunnan asettaminen tähän
                    verrataan, miten tämän osuman ja edellisen koordinaatit poikkeavat toisistaan*//*

                    tuliOsuma = true;
                    edellinenOsuma = maali;

                    return maali;
                }*/


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
