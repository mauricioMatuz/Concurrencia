package Modelo;

import java.util.Observable;
import java.util.concurrent.Semaphore;

public class Bibliotecaria extends Observable implements Runnable{
    private Semaphore rentarLibro;
    private Semaphore turno;
    private Semaphore entregarLibro;

    public Bibliotecaria(Semaphore rentarLibro, Semaphore turno, Semaphore entregarLibro){
        this.rentarLibro = rentarLibro;
        this.turno = turno;
        this.entregarLibro = entregarLibro;
    }

    @Override
    public void run(){
        while(true){
            try{
                if(entregarLibro.availablePermits()>=1){
                    entregarLibro.acquire();
                    setChanged();
                    notifyObservers("Van a depositar un libro");
                    setChanged();
                    notifyObservers("Han dejado el libro en su lugar");
                    Thread.sleep(2000);
                    turno.release();
                    setChanged();
                    notifyObservers("Se va despues de depositar");
                }else{
                    if(rentarLibro.availablePermits()>=1){
                        rentarLibro.acquire();
                        setChanged();
                        notifyObservers("Buscando libro");
                        setChanged();
                        notifyObservers("Libro encontrado");
                        Thread.sleep(2000);
                        turno.release();
                        setChanged();
                        notifyObservers("Se va con libro rentado");
                    }
                }
                Thread.sleep(1000);
            }catch (InterruptedException e) {

            }
        }
    }
}