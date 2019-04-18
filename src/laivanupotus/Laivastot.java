package laivanupotus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Laivastot {

    private List<Laiva> omatLaivat;
    private List<Laiva> vihunLaivat;
    private int[][] omatVaratutRuudut;
    private int[][] vihunVaratutRuudut;



    public Laivastot() {
        this.omatLaivat = new ArrayList<>();
        this.vihunLaivat = new ArrayList<>();
        this.omatVaratutRuudut = new int[10][10];
        this.vihunVaratutRuudut = new int[10][10];
    }

    public int getLaivaTyypinMaara(int koko, List<Laiva> laivasto) {
        int maara = 0;
        for (Laiva laiva: laivasto) {
            if (laiva.getKoko() == koko)
                maara++;
        }
        return maara;
    }

    public List<Laiva> getOmatLaivat() {
        return omatLaivat;
    }


    public List<Laiva> getVihunLaivat() {
        return vihunLaivat;
    }


    public int[][] getOmatVaratutRuudut() {
        return omatVaratutRuudut;
    }


    public int[][] getVihunVaratutRuudut() {
        return vihunVaratutRuudut;
    }


    public void setOmatVaratutRuudut(int[] koordinaatti, int arvo) {
        omatVaratutRuudut[koordinaatti[0]][koordinaatti[1]] = arvo;
    }

    public void setVihunVaratutRuudut(int[] koordinaatti, int arvo) {
        vihunVaratutRuudut[koordinaatti[0]][koordinaatti[1]] = arvo;
    }


    public boolean onkoVihunLaivatTuhottu() {
        int tuhotut = 0;
        for (Laiva laiva: vihunLaivat) {
            if (laiva.onkoTuhoutunut())
                tuhotut++;
        }
        if (tuhotut == 6)
            return true;

        return false;
    }

    public boolean onkoOmatLaivatTuhottu() {
        int tuhotut = 0;
        for (Laiva laiva: omatLaivat) {
            if (laiva.onkoTuhoutunut())
                tuhotut++;
        }
        if (tuhotut == 6)
            return true;

        return false;
    }

    public int omienTuhottujenLaivojenMaara() {
        int tuhotut = 0;
        for (Laiva laiva: omatLaivat) {
            if (laiva.onkoTuhoutunut())
                tuhotut++;
        }
        return tuhotut;
    }







    // KÄSIN ASETTELUN METODI TÄHÄN
    public void asetteleOmatLaivat() {

        while (true) {







            if (omatLaivat.size() == 6)
                break;
        }
    }


    // laivojen arpominen satunnaisesti
    public void arvoOmatLaivat() {

        Random random = new Random();

        while(true) {

            // varattujen ruutujen alustaminen
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++)
                    this.omatVaratutRuudut[i][j] = 0;
            }
            // laivalistan alustaminen
            omatLaivat.clear();


            // sukellusveneitä 3 kpl
            for (int i = 0; i < 3; i++) {

                int x = random.nextInt(9);
                int y = random.nextInt(9);

                Laiva subi = new Laiva(1, 1);
                int[][] sijainti = {{0, 0}};

                if (omatVaratutRuudut[x][y] == 0 && getLaivaTyypinMaara(1, omatLaivat) < 3) {
                    sijainti[0][0] = x;
                    sijainti[0][1] = y;
                    subi.setSijainti(sijainti);
                    omatLaivat.add(subi);
                    omatVaratutRuudut[x][y] = 1;
                }
            }


            // risteilijät 2 kpl
            for (int i = 0; i < 2; i++) {

                int x = random.nextInt(9);
                int y = random.nextInt(9);
                int suunta = random.nextInt(2) + 1;

                Laiva risteilija = new Laiva(2, suunta);
                int[][] sijainti = {{0, 0}, {0, 0}};

                if (suunta == 1) {          // onko vaakasuunnassa tilaa
                    if (omatVaratutRuudut[x][y] == 0 && omatVaratutRuudut[x + 1][y] == 0 && getLaivaTyypinMaara(2, omatLaivat) < 3) {
                        sijainti[0][0] = x;
                        sijainti[0][1] = y;
                        sijainti[1][0] = x + 1;
                        sijainti[1][1] = y;
                        risteilija.setSijainti(sijainti);
                        omatLaivat.add(risteilija);
                        omatVaratutRuudut[x][y] = 1;
                        omatVaratutRuudut[x + 1][y] = 1;
                    }

                } else if (suunta == 2) {          // onko pystysuunnassa tilaa
                    if (omatVaratutRuudut[x][y] == 0 && omatVaratutRuudut[x][y + 1] == 0 && getLaivaTyypinMaara(2, omatLaivat) < 3) {
                        sijainti[0][0] = x;
                        sijainti[0][1] = y;
                        sijainti[1][0] = x;
                        sijainti[1][1] = y + 1;
                        risteilija.setSijainti(sijainti);
                        omatLaivat.add(risteilija);
                        omatVaratutRuudut[x][y] = 1;
                        omatVaratutRuudut[x][y + 1] = 1;
                    }
                }
            }


            // lentotukialus 1 kpl
            int x = random.nextInt(8);
            int y = random.nextInt(8);
            int suunta = random.nextInt(2) + 1;

            Laiva lentotuki = new Laiva(3, suunta);
            int[][] sijainti = {{0, 0}, {0, 0}, {0, 0}};

            if (suunta == 1) {          // onko vaakasuunnassa tilaa
                if (omatVaratutRuudut[x][y] == 0 && omatVaratutRuudut[x + 1][y] == 0 && omatVaratutRuudut[x + 2][y] == 0 && getLaivaTyypinMaara(3, omatLaivat) < 1) {
                    sijainti[0][0] = x;
                    sijainti[0][1] = y;
                    sijainti[1][0] = x + 1;
                    sijainti[1][1] = y;
                    sijainti[2][0] = x + 2;
                    sijainti[2][1] = y;
                    lentotuki.setSijainti(sijainti);
                    omatLaivat.add(lentotuki);
                    omatVaratutRuudut[x][y] = 1;
                    omatVaratutRuudut[x + 1][y] = 1;
                    omatVaratutRuudut[x + 2][y] = 1;
                }
            }
            else if (suunta == 2) {          // onko pystysuunnassa tilaa
                if (omatVaratutRuudut[x][y] == 0 && omatVaratutRuudut[x][y + 1] == 0 && omatVaratutRuudut[x][y + 2] == 0 && getLaivaTyypinMaara(3, omatLaivat) < 1) {
                    sijainti[0][0] = x;
                    sijainti[0][1] = y;
                    sijainti[1][0] = x;
                    sijainti[1][1] = y + 1;
                    sijainti[2][0] = x;
                    sijainti[2][1] = y + 2;
                    lentotuki.setSijainti(sijainti);
                    omatLaivat.add(lentotuki);
                    omatVaratutRuudut[x][y] = 1;
                    omatVaratutRuudut[x][y + 1] = 1;
                    omatVaratutRuudut[x][y + 2] = 1;
                }
            }

            // jos arvonta ei onnistunut, niin while uusiksi kunnes onnistuu
            if (omatLaivat.size() == 6)
                break;

        }
    }

    // vihollisen laivat arvotaan aina
    public void arvoVihunLaivat() {

        Random random = new Random();

        while(true) {

            // varattujen ruutujen alustaminen
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++)
                    this.vihunVaratutRuudut[i][j] = 0;
            }
            // laivalistan alustaminen
            vihunLaivat.clear();


            // sukellusveneitä 3 kpl
            for (int i = 0; i < 3; i++) {

                int x = random.nextInt(9);
                int y = random.nextInt(9);

                Laiva subi = new Laiva(1, 1);
                int[][] sijainti = {{0, 0}};

                if (vihunVaratutRuudut[x][y] == 0 && getLaivaTyypinMaara(1, vihunLaivat) < 3) {
                    sijainti[0][0] = x;
                    sijainti[0][1] = y;
                    subi.setSijainti(sijainti);
                    vihunLaivat.add(subi);
                    vihunVaratutRuudut[x][y] = 1;
                }
            }


            // risteilijät 2 kpl
            for (int i = 0; i < 2; i++) {

                int x = random.nextInt(9);
                int y = random.nextInt(9);
                int suunta = random.nextInt(2) + 1;

                Laiva risteilija = new Laiva(2, suunta);
                int[][] sijainti = {{0, 0}, {0, 0}};

                if (suunta == 1) {          // onko vaakasuunnassa tilaa
                    if (vihunVaratutRuudut[x][y] == 0 && vihunVaratutRuudut[x + 1][y] == 0 && getLaivaTyypinMaara(2, vihunLaivat) < 3) {
                        sijainti[0][0] = x;
                        sijainti[0][1] = y;
                        sijainti[1][0] = x + 1;
                        sijainti[1][1] = y;
                        risteilija.setSijainti(sijainti);
                        vihunLaivat.add(risteilija);
                        vihunVaratutRuudut[x][y] = 1;
                        vihunVaratutRuudut[x + 1][y] = 1;
                    }

                } else if (suunta == 2) {          // onko pystysuunnassa tilaa
                    if (vihunVaratutRuudut[x][y] == 0 && vihunVaratutRuudut[x][y + 1] == 0 && getLaivaTyypinMaara(2, vihunLaivat) < 3) {
                        sijainti[0][0] = x;
                        sijainti[0][1] = y;
                        sijainti[1][0] = x;
                        sijainti[1][1] = y + 1;
                        risteilija.setSijainti(sijainti);
                        vihunLaivat.add(risteilija);
                        vihunVaratutRuudut[x][y] = 1;
                        vihunVaratutRuudut[x][y + 1] = 1;
                    }
                }
            }


            // lentotukialus 1 kpl
            int x = random.nextInt(8);
            int y = random.nextInt(8);
            int suunta = random.nextInt(2) + 1;

            Laiva lentotuki = new Laiva(3, suunta);
            int[][] sijainti = {{0, 0}, {0, 0}, {0, 0}};

            if (suunta == 1) {          // onko vaakasuunnassa tilaa
                if (vihunVaratutRuudut[x][y] == 0 && vihunVaratutRuudut[x + 1][y] == 0 && vihunVaratutRuudut[x + 2][y] == 0 && getLaivaTyypinMaara(3, vihunLaivat) < 1) {
                    sijainti[0][0] = x;
                    sijainti[0][1] = y;
                    sijainti[1][0] = x + 1;
                    sijainti[1][1] = y;
                    sijainti[2][0] = x + 2;
                    sijainti[2][1] = y;
                    lentotuki.setSijainti(sijainti);
                    vihunLaivat.add(lentotuki);
                    vihunVaratutRuudut[x][y] = 1;
                    vihunVaratutRuudut[x + 1][y] = 1;
                    vihunVaratutRuudut[x + 2][y] = 1;
                }
            }
            else if (suunta == 2) {          // onko pystysuunnassa tilaa
                if (vihunVaratutRuudut[x][y] == 0 && vihunVaratutRuudut[x][y + 1] == 0 && vihunVaratutRuudut[x][y + 2] == 0 && getLaivaTyypinMaara(3, vihunLaivat) < 1) {
                    sijainti[0][0] = x;
                    sijainti[0][1] = y;
                    sijainti[1][0] = x;
                    sijainti[1][1] = y + 1;
                    sijainti[2][0] = x;
                    sijainti[2][1] = y + 2;
                    lentotuki.setSijainti(sijainti);
                    vihunLaivat.add(lentotuki);
                    vihunVaratutRuudut[x][y] = 1;
                    vihunVaratutRuudut[x][y + 1] = 1;
                    vihunVaratutRuudut[x][y + 2] = 1;
                }
            }

            // jos arvonta ei onnistunut, niin while uusiksi kunnes onnistuu
            if (vihunLaivat.size() == 6)
                break;

        }
    }




}
