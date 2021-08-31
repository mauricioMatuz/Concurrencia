package classes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Estacionamiento {
    private ArrayList<Integer> lista;
    private Semaphore mutex;
    private Semaphore lleno;
    private Semaphore salir;
    private Semaphore libre;
    private Buffer buffer;
    private int carro = 0, pos = 0;

    public Estacionamiento(Buffer buffer, Semaphore lleno, Semaphore salir, Semaphore libre, Semaphore mutex){
        this.buffer = buffer;
        this.lleno = lleno;
        this.salir = salir;
        this.libre = libre;
        this.mutex = mutex;
        lista = new ArrayList<Integer>();
    }

    public void entrarCarro(int identificador){
        try {
            mutex.acquire();
            lista.add(identificador);
            mutex.release();
            salir.acquire();
            pos = lista.size();
            carro = lista.get(pos-1);
            if(isCarrosEsperando()){
                carro = lista.remove(pos - 1);
            }
            libre.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public boolean isCarrosEsperando(){
        if(lista.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }
}
