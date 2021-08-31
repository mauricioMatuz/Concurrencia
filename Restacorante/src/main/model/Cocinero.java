package main.model;

import javafx.scene.image.Image;
import main.model.Monitor;

import java.util.Observable;

public class Cocinero extends Observable implements Runnable {
    private Monitor monitor;
    private Image image;

    public Cocinero(Monitor monitor, Image image) {
        this.monitor = monitor;
        this.image = image;
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

    @Override
    public void run() {
        while (true) {
            monitor.cocinarComida();
            this.setChanged();
            this.notifyObservers("ComidaLista");
        }
    }
}
