package main.model;

import java.util.ArrayList;
import java.util.Random;

public class Monitor {
    private Mesa[] mesas;
    private ArrayList<Pedido> ordenes = new ArrayList();
    private ArrayList<Pedido> comidas = new ArrayList();
    private Random random;
    private boolean comidaLista;
    private boolean esperando;
    private boolean esLlamado;
    private Pedido pedido;

    public Monitor() {
        mesas = new Mesa[Config.numClientes];
        for (int i = 0; i < Config.numClientes; i++) {
            mesas[i] = new Mesa("", "Disponible");
        }
        random = new Random(System.currentTimeMillis());
        comidaLista = false;
        esperando = false;
    }

    public synchronized void recibirComensal() {
        if (Config.nClientes == Config.accesoDisponible) {
            Config.conEsperando++;
        }
        while (Config.numClientes == Config.accesoDisponible) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (Config.conEsperando > 0) {

            Config.conEsperando--;
        }
        Config.nClientes++;
    }


    public synchronized void reservaciones(String name) {
        if (Config.cantReservacion == Config.totalReservaciones || Config.nClientes == Config.accesoDisponible) {
            Config.conEsperando++;
        }
        while (Config.cantReservacion == Config.totalReservaciones || Config.nClientes == Config.accesoDisponible) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < Config.accesoDisponible; i++) {
            if (mesas[i].getStatus().equals("Disponible")) {
                mesas[i].setStatus("Reservado");
                mesas[i].setName(name);
                Config.mesaReservada = i;
                Config.cantReservacion++;
                break;
            }
        }
        if (Config.conEsperando > 0) {
            Config.conEsperando--;
        }
        Config.nClientes++;
    }


    public synchronized void asignarMesa(boolean isReservation, String name) {
        if (isReservation) {
            for (int i = 0; i < Config.accesoDisponible; i++) {
                if (mesas[i].getName().equals(name)) {
                    mesas[i].setStatus("Ocupado");
                    Config.mesa = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < Config.accesoDisponible; i++) {
                if (mesas[i].getStatus().equals("Disponible")) {
                    mesas[i].setStatus("Ocupado");
                    mesas[i].setName(name);
                    Config.mesa = i;
                    break;
                }
            }
        }
        this.notifyAll();
    }

    public synchronized void atenderComensal() {
        while (Config.nClientes == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < Config.accesoDisponible; i++) {
            if (mesas[i].getStatus().equals("Ocupado")) {
                Config.irAMesa = i;
                mesas[i].setStatus("Atendido");
                String name;
                name = mesas[i].getName();
                pedido = new Pedido(name, "En proceso");
                ordenes.add(pedido);
                this.notifyAll();
                break;
            }
        }
    }


    public synchronized void ordenTomada(int mesa) {
        while (!mesas[mesa].getStatus().equals("Servido")) {
            try {
                this.wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Config.numOrden++;

    }

    public synchronized void verificarOrden() {

        if (comidaLista == true) {
            for (int i = 0; i < comidas.size(); i++) {
                if (comidas.get(i).getStatus().equals("En proceso")) {
                    comidas.get(i).setStatus("Listo");
                    Pedido pedido;
                    pedido = comidas.get(i);
                    comidas.remove(i);
                    Config.numComida--;
                    for (int j = 0; j < Config.accesoDisponible; j++) {
                        if (mesas[j].getStatus().equals("Atendido")) {
                            if (mesas[j].getName().equals(pedido.getName())) {
                                mesas[j].setStatus("Servido");
                                this.notifyAll();
                                Config.mesaServida = j;
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }


    public synchronized void cocinarComida() {
        while (ordenes.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (comidas.isEmpty()) {
            comidaLista = false;
        }
        for (int i = 0; i < ordenes.size(); i++) {
            if (ordenes.get(i).getStatus().equals("En proceso")) {
                Pedido pedido;
                pedido = ordenes.get(i);
                ordenes.remove(i);
                comidas.add(pedido);
                Config.numComida++;
                comidaLista = true;
                break;
            }
        }
    }

    public synchronized void salirCliente(boolean isReservation, String name, int l) {
        if (mesas[l].getName().equals(name)) {
            mesas[l].setStatus("Disponible");
            mesas[l].setName("");
            Config.mesa = l;
            Config.nClientes--;
            if (isReservation) {
                Config.cantReservacion--;
            }
        }
        if (Config.nClientes < Config.accesoDisponible) {
            this.notifyAll();
        }
        if (Config.nClientes == 0) {
        }
    }
}
