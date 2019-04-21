package laivanupotus;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Arrays;


public class Kontrolleri {

    private Button[][] omatRuudut;
    private Button[][] vihunRuudut;
    private Logiikka logiikka;
    private Laivastot laivastot;
    private Tekoaly tekoaly;

    // nappien CSS-värityylit
    private String omaLaivaSt = "-fx-background-color: #A0522D; ";
    private String vihuLaivaSt = "-fx-background-color: #B8860B; ";
    private String vesiSt = "-fx-background-color: #66CDAA; " ;
    private String vihuOhiSt = "-fx-background-color: yellow; ";
    private String osumaSt = "-fx-background-color: red; ";


    // kirjanpito vihollisten laivojen lukumääristä


    public Kontrolleri() {
        this.omatRuudut = new Button[10][10];
        this.vihunRuudut = new Button[10][10];
        this.laivastot = new Laivastot();
        this.tekoaly = new Tekoaly(this.laivastot);
        this.logiikka = new Logiikka(this.tekoaly);

    }


    public Parent getRuudukko() {

        //StackPane runko = new StackPane();
        BorderPane runko = new BorderPane();
        runko.setPrefSize(650, 400);

        Label infoTeksti = new Label("Peli-infoa tänne");
        infoTeksti.setFont((Font.font("Monospaced", 20)));

        TextArea outputTeksti = new TextArea();
        outputTeksti.setPrefSize(300, 100);

        BorderPane infoRuutu = new BorderPane();
        infoRuutu.setPrefSize(300, 100);
        VBox tekstit = new VBox();
        Label lblVeneOtsikko = new Label("Vihollisen laivasto:");
        Label lblSubit = new Label("Sukellusveneet: 3");
        Label lblRist = new Label("Risteilijät: 2");
        Label lblLento = new Label("Lentotukialukset: 1");
        tekstit.getChildren().addAll(lblVeneOtsikko, lblSubit, lblRist, lblLento);
        infoRuutu.setRight(tekstit);

        GridPane omaPeliAlue = new GridPane();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button ruutu = new Button(" ");
                ruutu.setStyle(vesiSt);
                omatRuudut[i][j] = ruutu;
                ruutu.setFont(Font.font("Monospaced", 15));
                omaPeliAlue.add(ruutu, i, j);
            }
        }

        GridPane vihunPeliAlue = new GridPane();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button ruutu = new Button(" ");
                //ruutu.setStyle(vesiSt);
                vihunRuudut[i][j] = ruutu;
                ruutu.setFont(Font.font("Monospaced", 15));
                vihunPeliAlue.add(ruutu, i, j);
            }
        }

        // laivastojen satunnainen asettelu OLETUS TOISTAISEKSI
        laivastot.arvoOmatLaivat();
        laivastot.arvoVihunLaivat();


        // omien laivojen piirtäminen varattuihin ruutuihin ja näyttäminen, vihollislaivat pidetään piilossa

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                if (laivastot.getOmatVaratutRuudut()[i][j] == 1)
                    omatRuudut[i][j].setStyle(omaLaivaSt);
            }
        }


        logiikka.setLaivastot(laivastot);   // annetaan logiikalle asetellut laivastot

        HBox peliAlue = new HBox();
        HBox alaOsa = new HBox();
        peliAlue.setSpacing(30);
        peliAlue.getChildren().add(omaPeliAlue);
        peliAlue.getChildren().add(vihunPeliAlue);
        alaOsa.getChildren().addAll(outputTeksti, infoRuutu);
        runko.setTop(infoTeksti);
        runko.setCenter(peliAlue);
        runko.setBottom(alaOsa);




        // ruutuihin ampuminen vuorotellen: pelaajan napin toiminnallisuuden sisään on leivottu tekoälyn oma siirto,
        // joka tapahtuu 2 sekunnin viiveellä (pelaaja voi klikata nopeamminkin, jos tuntuu liian hitaalta)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int x = i;
                int y = j;
                vihunRuudut[i][j].setOnAction(e -> {

                    // *************************
                    // pelaajan vuoro

                    if (laivastot.getVihunVaratutRuudut()[x][y] == 1) {
                        outputTeksti.appendText("Vihollisen " + logiikka.pelaajaOsuu(x, y) + "\n");
                        vihunRuudut[x][y].setStyle(osumaSt);
                        laivastot.setVihunVaratutRuudut(new int[]{x, y}, -2);

                    } else if (laivastot.getVihunVaratutRuudut()[x][y] < 0)
                        outputTeksti.appendText("Ammuit uudelleen vanhaan kohtaan!\n");

                    else {
                        vihunRuudut[x][y].setStyle(vesiSt);
                        laivastot.setVihunVaratutRuudut(new int[]{x, y}, -1);
                        outputTeksti.appendText("Ammuit ohi.\n");
                    }

                    if (laivastot.onkoVihunLaivatTuhottu())
                        outputTeksti.setText(   "***********************************************\n" +
                                                "Kaikki vihollisen laivat tuhottu, voitit pelin!");


                    lblSubit.setText("Sukellusveneet: " + laivastot.getVihunSubit());
                    lblRist.setText("Risteilijät: " + laivastot.getVihunRist());
                    lblLento.setText("Lentotukialukset: " + laivastot.getVihunLento());


                    // *********************
                    // vihollisen vuoro
                    vihunPeliAlue.setDisable(true);

                    int maali[] = tekoaly.tekoAlyAmpuu();
                    int a = maali[0];
                    int b = maali[1];

                    Button nappi = omatRuudut[a][b];
                    nappi.setOnAction(eV -> {

                        if (laivastot.getOmatVaratutRuudut()[a][b] == 1) {
                            outputTeksti.appendText("Pelaajan " + logiikka.vihuOsuu(a, b) + "\n");
                            omatRuudut[a][b].setStyle(osumaSt);
                            laivastot.setOmatVaratutRuudut(maali, -2);
                            vihunPeliAlue.setDisable(false);
                        }

                        else {
                            omatRuudut[a][b].setStyle(vihuOhiSt);
                            laivastot.setOmatVaratutRuudut(maali, -1);
                            tekoaly.ammuttiinOhi();
                            outputTeksti.appendText("Vihollinen ampui ohi.\n");
                            vihunPeliAlue.setDisable(false);
                        }
                    });

                    // lisätään keinotekoinen "mietintätauko"
                    Timeline tauko = new Timeline(
                            new KeyFrame(
                                    Duration.seconds(1),
                                    event -> nappi.fire()
                            )
                    );

                    tauko.play();


                    if (laivastot.onkoOmatLaivatTuhottu())
                        outputTeksti.appendText(    "****************************************\n" +
                                                    "Kaikki laivasi tuhottiin, hävisit pelin! ");



                });

            }
        }

        return runko;


    }




}
