package Modelo;

import java.util.Observable;
import java.util.concurrent.Semaphore;

public class RentarLibro extends Observable implements Runnable {
    private Semaphore rentarLibro;
    private Semaphore turno;
    private Semaphore entregaLibro;

    public RentarLibro(Semaphore rentarLibro, Semaphore turno, Semaphore entregaLibro) {
        this.rentarLibro = rentarLibro;
        this.turno = turno;
        this.entregaLibro = entregaLibro;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            setChanged();
            notifyObservers("Quieren rentar un libro");
            rentarLibro.release();
            turno.acquire();
        } catch (InterruptedException e) {

        }
    }
}

