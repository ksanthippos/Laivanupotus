package laivanupotus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Paaohjelma extends Application {

    public void start(Stage ikkuna) {

        // introikkuna, jossa asetukset ennen peliä
    /*    BorderPane introAsettelu = new BorderPane();
        introAsettelu.setPrefSize(650, 400);
        Scene introNakyma = new Scene(introAsettelu);

        Label infoTeksti = new Label();
        infoTeksti.setText("Tervetuloa pelaamaan laivanupotusta! Valitse itsellesi sopiva pelimuoto ja paina alhaalta aloita-nappia.");

        RadioButton radioPeliMuoto = new RadioButton();
        RadioButton radioPelinKoko = new RadioButton();
        Button aloitaPeli = new Button("Aloita peli");

        radioPeliMuoto.setText("Pelimuodot:");
        radioPelinKoko.setText("Pelilaudan koko:");

        VBox asettelu = new VBox();
        asettelu.setSpacing(20);
        asettelu.getChildren().addAll(infoTeksti, radioPeliMuoto, radioPelinKoko, aloitaPeli);
        introAsettelu.setCenter(asettelu);

        ikkuna.setScene(introNakyma);
        ikkuna.show();*/

        // valikko päänäkymään
        MenuBar menuBar = new MenuBar();

        Menu menuPeli = new Menu("Peli");
        Menu uusiPeli = new Menu("Uusi peli");
        MenuItem manuaali = new MenuItem(("Manuaali"));
        MenuItem auto = new MenuItem(("Auto"));
        MenuItem lopetaPeli = new MenuItem("Lopeta");
        uusiPeli.getItems().addAll(manuaali, auto);
        menuPeli.getItems().addAll(uusiPeli, lopetaPeli);

        Menu menuOhje = new Menu(("Ohje"));
        MenuItem peliOhje = new MenuItem("Peliohjeet");
        MenuItem peliTiedot = new MenuItem("Tiedot..");
        menuOhje.getItems().addAll(peliOhje, peliTiedot);

        menuBar.getMenus().addAll(menuPeli, menuOhje);


        uusiPeli.setOnAction(e -> {

                    auto.setOnAction(e1 -> {
                        BorderPane paaAsettelu = new BorderPane();
                        Kontrolleri kontrolli = new Kontrolleri();
                        paaAsettelu.setTop(menuBar);
                        paaAsettelu.setCenter(kontrolli.getRuudukko());
                        Scene paaNakyma = new Scene(paaAsettelu);
                        ikkuna.setScene(paaNakyma);
                        ikkuna.show();
                    });

        });

        lopetaPeli.setOnAction(e -> {
            System.exit(0);
        });

        peliOhje.setOnAction(e -> {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("Peliohjeet");
            info.setContentText("Tässä laivanupotuksen versiossa kamppaillaan tekoälyä vastaan ja pelilauta generoidaan " +
                                "automaattisesti. Jos laivamuodostelmaa haluaa vaihtaa, sen saa tehtyä " +
                                "nopeasti pelivalikosta. Pelialueen koko on aina 10 x 10 ruutua ja laivojen " +
                                "määrä on kummallakin osapuolella seuraavanlainen: \n\n" +
                                "Sukellusveneitä 3 kpl\n" +
                                "Risteilijöitä 2 kpl\n" +
                                "Lentotukialuksia 1 kpl\n\n" +
                                "Aloitusvuoro on pelaajalla. Peli päättyy, kun toisen osapuolen kaikki laivat " +
                                "on upotettu.\n\n" +
                                "Hyviä pelejä!");
            info.showAndWait();
        });

        peliTiedot.setOnAction(e -> {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("MR-Laivanupotus v.1.0");
            info.setContentText("(c) Mikael Rauhala, 2019");
            info.showAndWait();

        });

/*        aloitaPeli.setOnAction(e -> {
            ikkuna.setScene(paaNakyma);
            ikkuna.show();
        });*/

        // varsinainen peli-ikkuna, jossa upotettuna pelin kontrolleri (ja logiikka)
        BorderPane paaAsettelu = new BorderPane();
        Kontrolleri kontrolli = new Kontrolleri();
        paaAsettelu.setTop(menuBar);
        paaAsettelu.setCenter(kontrolli.getRuudukko());
        Scene paaNakyma = new Scene(paaAsettelu);
        ikkuna.setScene(paaNakyma);
        ikkuna.show();




    }


    public static void main(String[] args) {
        launch(Paaohjelma.class);
    }
}
