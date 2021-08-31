package classes;

import java.util.Observable;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Lugar extends Observable implements Runnable{
    private int identificador;
    private Estacionamiento estacionamiento;
    private Buffer buffer;
    private Random random;
    private Semaphore salir;
    private Semaphore libre;
    private Semaphore lleno;
    private Semaphore mutex;

    public Lugar(int identificador, Estacionamiento estacionamiento, Buffer buffer, Semaphore lleno, Semaphore salir, Semaphore libre, Semaphore mutex){
        this.identificador = identificador;
        this.estacionamiento = estacionamiento;
        this.buffer = buffer;
        this.lleno = lleno;
        this.salir = salir;
        this.libre = libre;
        this.mutex = mutex;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        int retardo;

        while (estacionamiento.isCarrosEsperando()){
                try {
                    buffer.setEstado((identificador-1),true);
                    retardo = random.nextInt(4000) + 2000;
                    mutex.acquire();
                    setChanged();
                    notifyObservers(this);
                    mutex.release();

                    Thread.sleep(retardo);

                    salir.release();
                    libre.acquire();
                    mutex.acquire();
                    buffer.setEstado((identificador-1),false);
                    setChanged();
                    notifyObservers(this);
                    lleno.acquire();
                    mutex.release();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    public int getIdentificador(){
        return identificador;
    }
}
