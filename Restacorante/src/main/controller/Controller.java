package main.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.model.*;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Controller implements Observer, Initializable {
    @FXML
    private AnchorPane canvas;

    @FXML
    private VBox izqMesa1;

    @FXML
    private VBox asiento1;

    @FXML
    private VBox derMesa1;

    @FXML
    private VBox asiento2;

    @FXML
    private VBox izqMesa2;

    @FXML
    private VBox asiento3;

    @FXML
    private VBox derMesa2;

    @FXML
    private VBox asiento4;

    @FXML
    private Button iniciar;
    private int numImgComensal = 8;
    private int numImgMesero = 2;
    private int numImgChef = 2;
    private Image[] imagesComensalesss = new Image[numImgComensal];
    private Image[] imagesMesero = new Image[numImgMesero];
    private Image[] imagesChefs = new Image[numImgChef];
    private Cliente[] comensales = new Cliente[Config.numClientes];
    private Mesero[] meseros = new Mesero[(int) Config.numMeseros];
    private Cocinero[] chefs = new Cocinero[(int) Config.numCocinero];
    private ImageView[] comensalesImg = new ImageView[20];
    private ImageView[] reservacionesImg = new ImageView[20];
    private ImageView[] meserosImg = new ImageView[20];
    private ImageView[] chefsImg = new ImageView[2];
    private ImageView[] comidasImg = new ImageView[20];
    private ImageView[] bufferPlatillos = new ImageView[5];
    private ArrayList<ImageView> imagenesComensales = new ArrayList<>();

    @FXML
    void clickEmpezar(MouseEvent event) {

        HiloCliente comensal = new HiloCliente(comensales);
        HiloMesero meseross = new HiloMesero(meseros);
        HiloCocineros cocineross = new HiloCocineros(chefs);
        meseross.start();
        cocineross.start();
        comensal.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Cliente) {
            if (String.valueOf(arg).compareTo("Reservacion") == 0) {
                Image img = new Image(new File("src/main/img/reservacion.png").toURI().toString());
                ImageView valorRe = reservacionesImg[Config.mesaReservada];
                Platform.runLater(() -> {
                    valorRe.setImage(img);
                });
            }
            if (String.valueOf(arg).compareTo("LugarAsignado") == 0) {
                ImageView valor = comensalesImg[((Cliente) o).getLugar()];
                if (((Cliente) o).getReservation()) {
                    ImageView valorR = reservacionesImg[((Cliente) o).getLugar()];
                    Platform.runLater(() -> {
                        valorR.setImage(null);
                    });
                }
                Platform.runLater(() -> {
                    valor.setImage(((Cliente) o).getImage());
                });
            }
            if (String.valueOf(arg).compareTo("OrdenTomada") == 0) {
                ImageView valorC = comidasImg[((Cliente) o).getLugar()];
                Image img = new Image(new File("src/main/img/comida.png").toURI().toString());
                Platform.runLater(() -> {
                    valorC.setImage(img);
                });
            }


            if (String.valueOf(arg).compareTo("Salio") == 0) {

                ImageView valorC = comidasImg[((Cliente) o).getLugar()];
                ImageView valor = comensalesImg[((Cliente) o).getLugar()];
                Platform.runLater(() -> {
                    valor.setImage(null);
                    valorC.setImage(null);

                });

            }
        } else if (o instanceof Cocinero) {
            if (String.valueOf(arg).compareTo("ComidaLista") == 0) {
                Image img = new Image(new File("src/main/img/comida.png").toURI().toString());
                ImageView valor = bufferPlatillos[Config.numComida];
                Platform.runLater(() -> {
                    valor.setImage(img);
                });
            }

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String file = "src/main/img/";
        for (int c = 0; c < numImgComensal; c++) {
            //imagesComensalesss[c] = new Image(file+"comensal"+c+".png");
            imagesComensalesss[c] = new Image(new File(file + "comensal" + c + ".png").toURI().toString());

        }
        for (int m = 0; m < numImgMesero; m++) {
            //imagesMesero[m] = new Image(file+"mesero"+m+".png");
            imagesMesero[m] = new Image(new File(file + "mesero" + m + ".png").toURI().toString());

        }

        int a = 0, b = 0, c = 0;
        for (int i = 0; i < Config.accesoDisponible; i++) {
            comensalesImg[i] = new ImageView();
            meserosImg[i] = new ImageView();
            reservacionesImg[i] = new ImageView();
            comidasImg[i] = new ImageView();
            if (i < 5) {

                comensalesImg[i].setLayoutX(219);
                comensalesImg[i].setLayoutY(160 + (80 * i));
                comensalesImg[i].setFitHeight(45);
                comensalesImg[i].setFitWidth(45);

                meserosImg[i].setLayoutX(219);
                meserosImg[i].setLayoutY(160 + (80 * i));
                meserosImg[i].setFitHeight(45);
                meserosImg[i].setFitWidth(45);

                reservacionesImg[i].setLayoutX(219);
                reservacionesImg[i].setLayoutY(160 + (80 * i));
                reservacionesImg[i].setFitWidth(50);
                reservacionesImg[i].setFitHeight(50);

                comidasImg[i].setLayoutX(245);
                comidasImg[i].setLayoutY(160 + (80 * i));
                comidasImg[i].setFitWidth(30);
                comidasImg[i].setFitHeight(30);


                canvas.getChildren().addAll(comensalesImg[i], meserosImg[i], reservacionesImg[i], comidasImg[i]);

            }
            if (i > 4 && i < 10) {

                comensalesImg[i].setLayoutX(345);
                comensalesImg[i].setLayoutY(159 + (80 * a));
                comensalesImg[i].setFitHeight(45);
                comensalesImg[i].setFitWidth(45);

                meserosImg[i].setLayoutX(345);
                meserosImg[i].setLayoutY(159 + (80 * a));
                meserosImg[i].setFitHeight(45);
                meserosImg[i].setFitWidth(45);

                reservacionesImg[i].setLayoutX(345);
                reservacionesImg[i].setLayoutY(159 + (80 * a));
                reservacionesImg[i].setFitWidth(50);
                reservacionesImg[i].setFitHeight(50);

                comidasImg[i].setLayoutX(355);
                comidasImg[i].setLayoutY(159 + (80 * a));
                comidasImg[i].setFitWidth(30);
                comidasImg[i].setFitHeight(30);


                canvas.getChildren().addAll(comensalesImg[i], meserosImg[i], reservacionesImg[i], comidasImg[i]);
                a++;
            }
            if (i > 9 && i < 15) {

                comensalesImg[i].setLayoutX(452);
                comensalesImg[i].setLayoutY(159 + (80 * b));
                comensalesImg[i].setFitHeight(45);
                comensalesImg[i].setFitWidth(45);

                meserosImg[i].setLayoutX(452);
                meserosImg[i].setLayoutY(159 + (80 * b));
                meserosImg[i].setFitHeight(45);
                meserosImg[i].setFitWidth(45);

                reservacionesImg[i].setLayoutX(452);
                reservacionesImg[i].setLayoutY(159 + (80 * b));
                reservacionesImg[i].setFitWidth(50);
                reservacionesImg[i].setFitHeight(50);


                comidasImg[i].setLayoutX(462);
                comidasImg[i].setLayoutY(159 + (80 * b));
                comidasImg[i].setFitWidth(30);
                comidasImg[i].setFitHeight(30);


                canvas.getChildren().addAll(comensalesImg[i], meserosImg[i], reservacionesImg[i], comidasImg[i]);
                b++;
            }
            if (i > 14 && i < 20) {

                comensalesImg[i].setLayoutX(575);
                comensalesImg[i].setLayoutY(159 + (80 * c));
                comensalesImg[i].setFitHeight(45);
                comensalesImg[i].setFitWidth(45);

                meserosImg[i].setLayoutX(575);
                meserosImg[i].setLayoutY(159 + (80 * c));
                meserosImg[i].setFitHeight(45);
                meserosImg[i].setFitWidth(45);

                reservacionesImg[i].setLayoutX(575);
                reservacionesImg[i].setLayoutY(159 + (80 * c));
                reservacionesImg[i].setFitWidth(50);
                reservacionesImg[i].setFitHeight(50);

                comidasImg[i].setLayoutX(555);
                comidasImg[i].setLayoutY(159 + (80 * c));
                comidasImg[i].setFitWidth(30);
                comidasImg[i].setFitHeight(30);

                canvas.getChildren().addAll(comensalesImg[i], meserosImg[i], reservacionesImg[i], comidasImg[i]);
                c++;
            }
        }

        for (int p = 0; p < 5; p++) {
            bufferPlatillos[p] = new ImageView();
            bufferPlatillos[p].setFitWidth(30);
            bufferPlatillos[p].setFitHeight(30);
            bufferPlatillos[p].setLayoutX(563);
            bufferPlatillos[p].setLayoutY(100);
            canvas.getChildren().add(bufferPlatillos[p]);
        }

        Monitor monitor = new Monitor();
        int numR = 0;
        int numN = 0;
        for (int i = 0; i < Config.numClientes; i++) {
            Cliente cliente = new Cliente("C" + i, getReservacion(), monitor, getComensalImage());
            cliente.addObserver(this);

            comensales[i] = cliente;
        }

        for (int m = 0; m < Config.numMeseros; m++) {

            Mesero mesero = new Mesero(monitor, imagesMesero[m]);
            mesero.addObserver(this);
            meseros[m] = mesero;
        }

        for (int co = 0; co < Config.numCocinero; co++) {
            Cocinero chef = new Cocinero(monitor, imagesChefs[co]);
            chef.addObserver(this);
            chefs[co] = chef;
        }
    }

    public Image getComensalImage() {
        Random random = new Random();
        Image image = imagesComensalesss[random.nextInt(numImgComensal)];
        return image;
    }

    public boolean getReservacion() {
        Random rnd = new Random();
        return rnd.nextBoolean();
    }
}
