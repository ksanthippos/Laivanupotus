package laivanupotus;

public class Logiikka {

    private Laivastot laivastot;
    private Tekoaly tekoaly;


    public Logiikka(Tekoaly tekoaly) {
        this.laivastot = new Laivastot();
        this.tekoaly = tekoaly;

    }


    public void setLaivastot(Laivastot laivastot) {
        this.laivastot = laivastot;
    }



    // annetaan laiva-oliolle osuma ja palautetaan merkkijono, joka kertoo miten osuma vaikutti
    public String pelaajaOsuu(int x, int y) {

        for (Laiva laiva: laivastot.getVihunLaivat()) {
            if (laiva.getKoko() == 1) {
                if (laiva.getSubSijainti()[0][0] == x && laiva.getSubSijainti()[0][1] == y)
                    return laiva.setOsuma();
            }
            else if (laiva.getKoko() == 2) {
                if (laiva.getRistSijainti()[0][0] == x && laiva.getRistSijainti()[0][1] == y || laiva.getRistSijainti()[1][0] == x &&
                        laiva.getRistSijainti()[1][1] == y)
                    return laiva.setOsuma();
            }
            else if (laiva.getKoko() == 3) {
                if (laiva.getLentoSijainti()[0][0] == x && laiva.getLentoSijainti()[0][1] == y || laiva.getLentoSijainti()[1][0] == x &&
                        laiva.getLentoSijainti()[1][1] == y || laiva.getLentoSijainti()[2][0] == x && laiva.getLentoSijainti()[2][1] == y)
                    return laiva.setOsuma();
            }
        }

        return "";

    }


    // pitäisiköhän täällä tarkistaa osuman yhteydessä, tuhosiko vihollinen laivan? sen tiedon voisi antaa tekoälylle

    public String vihuOsuu(int x, int y) {


        for (Laiva laiva: laivastot.getOmatLaivat()) {
            if (laiva.getKoko() == 1) {
                if (laiva.getSubSijainti()[0][0] == x && laiva.getSubSijainti()[0][1] == y) {
                        tekoaly.setViimeksiTuhoutui(true);
                        return laiva.setOsuma();
                }
            }
            else if (laiva.getKoko() == 2) {
                if (laiva.getRistSijainti()[0][0] == x && laiva.getRistSijainti()[0][1] == y || laiva.getRistSijainti()[1][0] == x &&
                        laiva.getRistSijainti()[1][1] == y) {

                            String palautus = laiva.setOsuma();
                            //if (!palautus.equals("laiva sai osuman!")) {
                            if (laiva.onkoTuhoutunut()) {
                                tekoaly.setViimeksiTuhoutui(true);
                                return palautus;
                            }
                            else {
                                tekoaly.setViimeksiTuhoutui(false);
                                return palautus;
                            }
                }
            }
            else if (laiva.getKoko() == 3) {
                if (laiva.getLentoSijainti()[0][0] == x && laiva.getLentoSijainti()[0][1] == y || laiva.getLentoSijainti()[1][0] == x &&
                        laiva.getLentoSijainti()[1][1] == y || laiva.getLentoSijainti()[2][0] == x && laiva.getLentoSijainti()[2][1] == y) {

                            String palautus = laiva.setOsuma();
                            //if (!palautus.equals("laiva sai osuman!")) {
                            if (laiva.onkoTuhoutunut()) {
                                tekoaly.setViimeksiTuhoutui(true);
                                return palautus;
                            }
                            else {
                                tekoaly.setViimeksiTuhoutui(false);
                                return palautus;
                            }
                }
            }
        }

        return "";



    }







}
