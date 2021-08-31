package Modelo;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Observable;
import java.util.concurrent.Semaphore;

public class EntregarLibro extends Observable implements Runnable{
    private Semaphore entregaLibro;
    private Semaphore turno;
    private Semaphore rentarLibro;

    public EntregarLibro(Semaphore entregaLibro, Semaphore turno,Semaphore rentarLibro){
        this.entregaLibro = entregaLibro;
        this.turno = turno;
        this.rentarLibro = rentarLibro;
    }

    @Override
    public void run(){
        try{
            Thread.sleep(1000);
            setChanged();
            notifyObservers("Quieren entregar un libro");
            entregaLibro.release();
            turno.acquire();
        }catch (InterruptedException e) {

        }
    }
}
