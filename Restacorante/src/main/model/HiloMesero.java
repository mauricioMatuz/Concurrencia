package main.model;

import main.model.Config;
import main.model.Mesero;

public class HiloMesero extends Thread {
    private Mesero[] meseros;

    public HiloMesero(Mesero[] meseros) {
        this.meseros = meseros;
    }

    @Override
    public void run() {
        Mesero mesero;
        for (int a = 0; a < Config.numMeseros; a++) {
            mesero = meseros[a];
            new Thread(mesero, "Mesero" + a).start();

        }
    }
}
