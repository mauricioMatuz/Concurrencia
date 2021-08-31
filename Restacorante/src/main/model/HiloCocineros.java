package main.model;

public class HiloCocineros extends Thread {
    private Cocinero[] cocineros;

    public HiloCocineros(Cocinero[] cocineros) {
        this.cocineros = cocineros;
    }

    @Override
    public void run() {
        Cocinero cocinero;
        for (int c = 0; c < Config.numCocinero; c++) {
            cocinero = cocineros[c];
            new Thread(cocinero, "Chef" + c).start();
        }
    }
}
