package videojuego.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Observable;

public class HiloDisparos extends Observable implements Runnable {

    Image bala;
    ImageView balaView;
    double Y;
    double Yr;
    boolean bandera = true;


    public HiloDisparos(Image bala, double Y) {
        this.bala = bala;
        this.Y = Y;
    }

    @Override
    public void run() {
        Yr = Y;
        if(Y == -28)
            Y = Yr;
        while (true){
            this.setChanged();
            this.notifyObservers(Y);
            Y = Y - 25;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
