package laivanupotus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Tekoaly {

    private Laivastot laivastot;
    private int[] edellinenOsuma;
    private List<int[]> arvottavat;
    private int ampumaSuunta;    // ei suuntaa = 0; 1 = ylös, 2 = oikea, 3 = alas, 4 = vasen
    private boolean tuliOsuma;
    private boolean viimeksiTuhoutui;
    private boolean jatketaanViela;



    public Tekoaly(Laivastot laivastot) {

        this.laivastot = laivastot;
        this.arvottavat = new ArrayList<>();
    }

    public void ammuttiinOhi() {
        tuliOsuma = false;
    }

    public void setViimeksiTuhoutui(boolean arvo) {
        viimeksiTuhoutui = arvo;
    }


    // TESTIMETODI
    public void getArvottavat() {
        if (arvottavat.size() == 0)
            System.out.println(0);

        arvottavat.stream().forEach(a -> System.out.println(a.toString()));
    }


    // palauttaa joko 1, -1 tai -2
    public int mitaRuudussaOn(int[] koordinaatti) {
        return laivastot.getOmatVaratutRuudut()[koordinaatti[0]][koordinaatti[1]];
    }


    // usein käytetty apumetodi
    public int[] ammuSatunnaiseenMaaliin() {

        jatketaanViela = false;
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

    public void lisaaArvottavat(int x, int y) {

        // osumakohdasta ylös, alas, oikealle ja vasemmalle
        if (x > 0 && x < 9 && y > 0 && y < 9) {
            arvottavat.add(new int[]{x, y - 1});
            arvottavat.add(new int[]{x - 1, y});
            arvottavat.add(new int[]{x + 1, y});
            arvottavat.add(new int[]{x, y + 1});
        }
        // vasemmassa reunassa mutta ei nurkissa
        else if (x == 0 && y > 0 && y < 9) {
            arvottavat.add(new int[]{x + 1, y});
            arvottavat.add(new int[]{x, y - 1});
            arvottavat.add(new int[]{x, y + 1});
        }
        // oikeassa reunassa mutta ei nurkissa
        else if (x == 9 && y > 0 && y < 9) {
            arvottavat.add(new int[]{x - 1, y});
            arvottavat.add(new int[]{x, y - 1});
            arvottavat.add(new int[]{x, y + 1});
        }
        // yläreunassa mutta ei nurkissa
        else if (y == 0 && x > 0 && x < 9) {
            arvottavat.add(new int[]{x - 1, y});
            arvottavat.add(new int[]{x + 1, y});
            arvottavat.add(new int[]{x, y + 1});
        }
        // alareunassa mutta ei nurkissa
        else if (y == 9 && x > 0 && x < 9) {
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

    }



    public int[] tekoAlyAmpuu() {

        Random random = new Random();

        int[] maali = new int[2];
        int x, y, valinta;


        if (!tuliOsuma)
            return ammuSatunnaiseenMaaliin();

        else if (viimeksiTuhoutui) {
            viimeksiTuhoutui = false;
            arvottavat.clear();
            return ammuSatunnaiseenMaaliin();
        }

        // ongelman avaoin piilee tuliOsumassa: jos laivan ympräillä ammutaan ohi, niin silti pit'isi päästä tähän metodiin takaisin


        // edellisellä kerralla osutiin, mutta suunta ei vielä tiedossa --> ammutaan edellisen osumakohdan ympäröiviin ruutuihin satunnaisesti
        else if (tuliOsuma && ampumaSuunta == 0 && !viimeksiTuhoutui && !jatketaanViela) {

            x = edellinenOsuma[0];
            y = edellinenOsuma[1];
            lisaaArvottavat(x, y);

            Iterator haku = arvottavat.iterator();
            while (haku.hasNext()) {
                int[] koordinaatti = (int[])haku.next();
                if (mitaRuudussaOn(koordinaatti) < 0)
                    haku.remove();
            }

            jatketaanViela = true;

            // arvontojen läpikäynti, etsitään sopiva kohta ja ammutaan


                // jos ei ole viereisiä ruutuja, niin jatketaan ampumista muualla
                if (arvottavat.size() <= 0) {
                    return ammuSatunnaiseenMaaliin();
                }


                // TARVITAAN ALGORITMI, JOKA POISTAA ARVOTTAVISTA HUONOT RUUDUT ELI JO AMMUTUT!



                // arvotaan ampumakohta
                valinta = random.nextInt(arvottavat.size());
                maali = arvottavat.get(valinta);

/*                if (mitaRuudussaOn(maali) < 0) {    // maaliin ammuttu jo aiemmin
                    arvottavat.remove(maali);
                    continue;   // arvotaan uusi maali jäljellä olevien joukosta
                }*/

                if (mitaRuudussaOn(maali) == 0) {  // ohilaukaus
                    arvottavat.remove(maali);
                    //tuliOsuma = false;
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
        }

        // jatketaan osumakohdan ympärille ampumista
        else if (jatketaanViela) {


            // jos ei ole viereisiä ruutuja, niin jatketaan ampumista muualla
            if (arvottavat.size() <= 0) {
                jatketaanViela = false;
                return ammuSatunnaiseenMaaliin();
            }

            // arvotaan ampumakohta
            valinta = random.nextInt(arvottavat.size());
            maali = arvottavat.get(valinta);

            if (mitaRuudussaOn(maali) == 0) {  // ohilaukaus
                arvottavat.remove(maali);
                //tuliOsuma = false;
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


        // jos kaikki muu feilaa, niin ammu sokkona!
        return ammuSatunnaiseenMaaliin();


    }


}
