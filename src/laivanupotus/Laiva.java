package laivanupotus;


public class Laiva {

    private int koko;                   // 1 = sukellusvene, 2 = risteilijä, 3 = lentotukialus
    private int suunta;                 // 1 = vaakasuunta, 2 = pystysuunta
    private int osumat;
    private boolean tuhoutunut;

    private int[][] subSijainti;
    private int[][] ristSijainti;
    private int[][] lentoSijainti;



    public Laiva(int koko, int suunta) {
        this.koko = koko;
        this.suunta = suunta;
        this.tuhoutunut = false;
    }


    public void setSijainti(int[][] sijainti) {
        if (sijainti.length == 1)
            subSijainti = sijainti;
        else if (sijainti.length == 2)
            ristSijainti = sijainti;
        else if (sijainti.length == 3)
            lentoSijainti = sijainti;
    }


    public int[][] getSubSijainti() {
        return subSijainti;
    }

    public int[][] getRistSijainti() {
        return ristSijainti;
    }

    public int[][] getLentoSijainti() {
        return lentoSijainti;
    }

    public int getKoko() { return koko; }


    public String setOsuma() {
        osumat++;
        if (osumat >= koko) {
            tuhoutunut = true;
            if (koko == 1)
                return "sukellusvene tuhoutui!";
            else if (koko == 2)
                return "risteilijä tuhoutui!";
            else if (koko == 3)
                return "lentotukialus tuhoutui!";
        }

        return "laiva sai osuman!";
    }

    public boolean onkoTuhoutunut() {
        return tuhoutunut;
    }


}
