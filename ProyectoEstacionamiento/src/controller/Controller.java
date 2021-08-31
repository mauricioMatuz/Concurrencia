package controller;

import classes.Buffer;
import classes.Carro;
import classes.Estacionamiento;
import classes.Lugar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Controller implements Observer {
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img4;
    @FXML
    private ImageView img5;
    @FXML
    private ImageView img6;
    @FXML
    private ImageView img7;
    @FXML
    private ImageView img8;
    @FXML
    private ImageView img9;
    @FXML
    private ImageView img10;
    @FXML
    private ImageView img11;
    @FXML
    private ImageView img12;
    @FXML
    private ImageView img13;
    @FXML
    private ImageView img14;
    @FXML
    private ImageView img15;
    @FXML
    private ImageView img16;
    @FXML
    private ImageView img17;
    @FXML
    private ImageView img18;
    @FXML
    private ImageView img19;
    @FXML
    private ImageView img20;
    @FXML
    private ImageView img21;
    @FXML
    private ImageView img22;
    @FXML
    private Label lblSalidos;

    ImageView carros[] = new ImageView[20];
    private Semaphore lleno, salir,libre, mutex;
    Buffer buffer = new Buffer();
    private boolean[] lugaresCarros;
    int carSal;

    @FXML
    public void initialize(){
        img21.setVisible(false);
        img22.setVisible(false);
        carros[0] = img1;
        carros[1] = img2;
        carros[2] = img3;
        carros[3] = img4;
        carros[4] = img5;
        carros[5] = img6;
        carros[6] = img7;
        carros[7] = img8;
        carros[8] = img9;
        carros[9] = img10;
        carros[10] = img11;
        carros[11] = img12;
        carros[12] = img13;
        carros[13] = img14;
        carros[14] = img15;
        carros[15] = img16;
        carros[16] = img17;
        carros[17] = img18;
        carros[18] = img19;
        carros[19] = img20;

        for(int i=0; i<carros.length; i++){
            carros[i].setVisible(false);
        }

        carSal=0;

        lleno = new Semaphore(0);
        salir = new Semaphore(0);
        libre = new Semaphore(0);
        mutex = new Semaphore(1);
    }


    public void iniciar(){

        Lugar[] lugar = new Lugar[20];
        Carro[] carro = new Carro[100];
        Estacionamiento estacionamiento = new Estacionamiento(buffer, lleno, salir, libre, mutex);

        for(int i=0; i<100; i++){
            Thread t;
            carro[i] = new Carro(i+1, estacionamiento);
            t = new Thread(carro[i]);
            t.setDaemon(true);
            t.start();
        }

        for(int j=0; j<20; j++){
            Thread t;
            lugar[j] = new Lugar(j+1, estacionamiento, buffer, lleno, salir, libre, mutex);
            lugar[j].addObserver(this);
            t = new Thread(lugar[j]);
            t.setDaemon(true);
            t.start();
        }

        for(int l=0; l<20; l++){
            try {
                new Thread(lugar[l]).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int k=0; k<100; k++){
            try {
                new Thread(carro[k]).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void update(Observable observable, Object o) {
        Lugar l = (Lugar) o;
        int num = l.getIdentificador();
        lugaresCarros = buffer.getEstados();
        Platform.runLater(() -> {
            if(lugaresCarros[num-1]){
                carros[num-1].setVisible(true);
                img21.setVisible(true);
                img22.setVisible(false);
            }
            else {
                carros[num-1].setVisible(false);
                carSal++;
                lblSalidos.setText("Carros retirados: "+carSal);
                img21.setVisible(false);
                img22.setVisible(true);
                lleno.release();
            }
        });

        if(carSal == 99){
            System.out.println("carros retirados 100");
            for (int i = 0; i < lugaresCarros.length; i++){
                if(lugaresCarros[i]){
                    carros[i].setVisible(false);
                }
            }
            for (int i = 0; i < lugaresCarros.length; i++){
                if(lugaresCarros[i]){
                    carros[i].setVisible(false);
                }
            }
        }
    }
}
