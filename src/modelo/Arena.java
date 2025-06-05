package modelo;

public class Arena {
    private int id;
    private boolean ocupada;

    public Arena(int id) {
        this.id = id;
        this.ocupada = false;
    }

    public synchronized void ocupar() {
        this.ocupada = true;
    }

    public synchronized void liberar() {
        this.ocupada = false;
    }

    public synchronized boolean estaOcupada() {
        return ocupada;
    }

    public int getId() {
        return id;
    }
} 