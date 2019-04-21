package laivanupotus;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tekoaly {

    private Laivastot laivastot;
    private int[] edellinenOsuma;
    private int ampumaSuunta;    // ei suuntaa = 0; 1 = ylös, 2 = oikea, 3 = alas, 4 = vasen
    private boolean tuliOsuma;

    private boolean viimeksiTuhoutui;


    public Tekoaly(Laivastot laivastot) {
        this.laivastot = laivastot;
        this.ampumaSuunta = 0;

    }

    public void ammuttiinOhi() {
        tuliOsuma = false;
    }

    public void setViimeksiTuhoutui(boolean arvo) {
        viimeksiTuhoutui = arvo;
    }


    // palauttaa joko 1, -1 tai -2
    public int mitaRuudussaOn(int[] koordinaatti) {
        return laivastot.getOmatVaratutRuudut()[koordinaatti[0]][koordinaatti[1]];
    }


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


        // edellisellä kerralla ei osuttu mihinkään --> ammutaan satunnaiseen ruutuun
        if (!tuliOsuma) {
            return ammuSatunnaiseenMaaliin();
        }

        // edellisellä kerralla osutiin, mutta suunta ei vielä tiedossa --> ammutaan edellisen osumakohdan ympäröiviin ruutuihin satunnaisesti
        else if (tuliOsuma && ampumaSuunta == 0 && !viimeksiTuhoutui) {

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

            // arvontojen läpikäynti, etsitään sopiva kohta ja ammutaan
            while (true) {

                // jos ei ole viereisiä ruutuja, niin jatketaan ampumista muualla
                if (arvottavat.size() <= 0)
                    return ammuSatunnaiseenMaaliin();

                // arvotaan ampumakohta
                int valinta = random.nextInt(arvottavat.size());
                maali = arvottavat.get(valinta);

                if (mitaRuudussaOn(maali) < 0) {    // maaliin ammuttu jo aiemmin
                    arvottavat.remove(maali);
                    continue;   // arvotaan uusi maali jäljellä olevien joukosta
                }
                else if (mitaRuudussaOn(maali) == 0) {  // ohilaukaus
                    tuliOsuma = false;
                    return maali;
                }
                else if (mitaRuudussaOn(maali) == 1) {  // osuttiin uudelleen laivaan, nyt otetaan siitä suunta seuraavaa vuoroa varten
                    tuliOsuma = true;

                    int edellinenX = edellinenOsuma[0];
                    int edellinenY = edellinenOsuma[1];
                    int nykyinenX = maali[0];
                    int nykyinenY = maali[1];

                    if (nykyinenX > edellinenX)
                        ampumaSuunta = 2;   // oikealle
                    else if (nykyinenX < edellinenX)
                        ampumaSuunta = 4;   // vasemmalle
                    else if (nykyinenY > edellinenY)
                        ampumaSuunta = 1;   // ylös
                    else if (nykyinenY < edellinenY)
                        ampumaSuunta = 2;   // alas

                    edellinenOsuma = maali;
                    return maali;

                }

                /*else
                    return ammuSatunnaiseenMaaliin();*/


            }

        }

        else if (tuliOsuma && ampumaSuunta != 0 && !viimeksiTuhoutui) {  // tiedetään, missä suunnassa pelaajan laiva on

            x = edellinenOsuma[0];
            y = edellinenOsuma[1];

            if (ampumaSuunta == 1)
                y--;

            else if (ampumaSuunta == 2)
                x++;

            else if (ampumaSuunta == 3)
                y++;

            else if (ampumaSuunta == 4)
                x--;

            maali = new int[]{x, y};
            return maali;

        }


        return new int[2];


    }


}
