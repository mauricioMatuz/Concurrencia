package videojuego.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import videojuego.model.HiloDisparos;
import videojuego.model.HiloNaveEnemiga;

import java.util.*;
//solo comento para resubir el programa xd
public class Controller implements Observer {

    @FXML
    private Button boton_izquierda;

    @FXML
    private ImageView bala_jugador;

    @FXML
    private ImageView gameOver;

    @FXML
    private Button boton_derecha;

    @FXML
    private Button boton_disparar;

    @FXML
    private ImageView nave_jugador;

    @FXML
    private ImageView nave_enemiga;

    @FXML
    private AnchorPane anchorXD;

    @FXML
    void activarJuego(ActionEvent event) {
        moverNaveEnemiga();
    }

    @FXML
    void disparar(ActionEvent event) {
        bala_jugador.setVisible(true);
        disparos();
    }

    @FXML
    void moverDerecha(ActionEvent event) {
        double posicion = nave_jugador.getLayoutX();
        if (posicion != 477.0) {
            nave_jugador.setLayoutX(posicion + 30);
        } else {
        }
    }

    @FXML
    void moverIzquierda(ActionEvent event) {
        double posicion = nave_jugador.getLayoutX();
        if (posicion != 57) {
            nave_jugador.setLayoutX(posicion - 30);
        } else {

        }
    }

    double posicionEnemigo;
    double guardarPosicion;
    int valor;

    void moverNaveEnemiga() {
        posicionEnemigo = nave_enemiga.getLayoutX();
        Random numA = new Random();
        valor = numA.nextInt(2);
        HiloNaveEnemiga hiloNave = new HiloNaveEnemiga(valor);
        hiloNave.addObserver(this);
        Thread enemigo = new Thread(hiloNave);
        enemigo.start();
    }

    double ubicacionBalaX;
    double ubicacionBalaY;
    double Y = 272;

    void disparos() {
        Image img = new Image("videojuego/recursos/Bala.png",44,39,false,false);
        ubicacionBalaX = nave_jugador.getLayoutX() + 13.0;
        ubicacionBalaY = nave_jugador.getLayoutY() - 25.0;
        HiloDisparos hiloDisparos = new HiloDisparos(img, Y);
        hiloDisparos.addObserver(Controller.this);
        Thread balaS = new Thread(hiloDisparos);
        balaS.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof HiloNaveEnemiga) {
            int positionUwu = (int) arg;
            if (positionUwu == 1) {
                if (posicionEnemigo >= 450) {
                } else {
                    posicionEnemigo = posicionEnemigo + 25;
                    nave_enemiga.setLayoutX(posicionEnemigo);
                }
            } else {
                if (posicionEnemigo <= 50) {
                } else {
                    posicionEnemigo = posicionEnemigo - 25;
                    nave_enemiga.setLayoutX(posicionEnemigo);
                }
            }
        } else {
            if (o instanceof HiloDisparos) {
                double balasa = (double) arg;
                if (balasa == -28){
                    balasa = 272 ;
                }
                bala_jugador.setLayoutY(balasa);
                bala_jugador.setLayoutX(ubicacionBalaX);
                if(nave_enemiga.getLayoutX() == ubicacionBalaX-33){
                    gameOver.setVisible(true);
                }

            }
        }

    }

    @FXML
    void initialize() {
        bala_jugador.setVisible(false);
        gameOver.setVisible(false);
    }

    /*
    para hacer comparaciones , javafx tiene una clase interception
    este metodo, permite pasar un objeto como parametro, si coincide el parametro da como
    resultado un booleano (True o false)
     */
}
