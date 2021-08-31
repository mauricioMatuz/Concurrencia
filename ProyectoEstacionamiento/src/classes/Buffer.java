package classes;

import java.util.Arrays;

public class Buffer {
    private boolean[] buffer;
    private final int MAX = 20;

    public Buffer(){
        buffer = new boolean[MAX];
        for (int i=0; i<buffer.length; i++){
            buffer[i] = false;
        }
    }

    public boolean[] getEstados(){
        return buffer;
    }

    public void setEstado(int posicion, boolean estado){
        buffer[posicion] = estado;
    }

    @Override
    public String toString() {
        return "Buffer{" +
                "buffer=" + Arrays.toString(buffer) +
                '}';
    }

    public void estados(){
        System.out.println(Arrays.toString(buffer));
    }
}
