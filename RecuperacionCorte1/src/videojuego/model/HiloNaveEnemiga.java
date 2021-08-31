package videojuego.model;


import java.util.Observable;
import java.util.Random;

public class HiloNaveEnemiga extends Observable implements Runnable {


    int seleccion;
    Random ramsito;
    public HiloNaveEnemiga(int seleccion) {
        this.seleccion = seleccion;
    }

    @Override
    public void run() {
        while (true) {
            ramsito = new Random();
            seleccion = ramsito.nextInt(2);
           // System.out.println("Entro al while del hilo."+ seleccion);
            this.setChanged();
            this.notifyObservers(seleccion);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
