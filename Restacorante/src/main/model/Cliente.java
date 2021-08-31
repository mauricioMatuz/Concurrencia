package main.model;

import javafx.scene.image.Image;
import main.model.Config;
import main.model.Monitor;

import java.util.Observable;
import java.util.Random;

public class Cliente extends Observable implements Runnable {
    private String name;
    private Boolean isReservation;
    private Random random;
    private Monitor monitor;
    private int mesa;
    private Image image;

    public Cliente(String name, Boolean isReservation, Monitor monitor, Image image) {
        this.name = name;
        this.isReservation = isReservation;
        this.monitor = monitor;
        this.image = image;
        random = new Random(System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getReservation() {
        return isReservation;
    }

    public void setReservation(Boolean reservation) {
        isReservation = reservation;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getLugar() {
        return mesa;
    }

    @Override
    public void run() {
        if (isReservation) {
            try {
                monitor.reservaciones(name);
                this.setChanged();
                this.notifyObservers("Reservacion");

                Thread.sleep(random.nextInt(3000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            monitor.recibirComensal();
        }
        Config.conEntrada++;
        Config.clientesAfuera--;
        monitor.asignarMesa(isReservation, name);
        mesa = Config.mesa;
        this.setChanged();
        this.notifyObservers("LugarAsignado");
        try {
            Thread.sleep(random.nextInt(2000) + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        monitor.ordenTomada(mesa);
        setChanged();
        notifyObservers("OrdenTomada");
        try {
            Thread.sleep(random.nextInt(5000) + 2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Config.conSalida++;
        monitor.salirCliente(isReservation, name, mesa);

        this.setChanged();
        this.notifyObservers("Salio");
    }
}
