package main.model;

import java.util.Random;

public class HiloCliente extends Thread {
    private Cliente[] comensales;
    private Random random;

    public HiloCliente(Cliente[] comensales) {
        this.comensales = comensales;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        Cliente cliente;
        for (int a = 0; a < Config.numClientes; a++) {
            cliente = comensales[a];
            new Thread(cliente, "C" + a).start();
            try {
                Thread.sleep(random.nextInt(400) + 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
