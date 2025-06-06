package modelo;

import java.io.Serializable;

public class Arena implements Serializable {
	private static final long serialVersionUID = 1L;
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