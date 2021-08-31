package classes;

import java.util.Random;

public class Carro implements Runnable {
    private Estacionamiento estacionamiento;
    private Random random;
    private int identificador, tiempo;

    public Carro(int identificador, Estacionamiento estacionamiento){
        this.identificador = identificador;
        this.estacionamiento = estacionamiento;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run(){
        estacionamiento.entrarCarro(identificador);
        tiempo = random.nextInt(400) + 300;
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
