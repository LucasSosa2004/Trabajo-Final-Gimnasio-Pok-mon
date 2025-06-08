package modelo;

import java.io.Serializable;

public class ArenaFisica implements Serializable {
	private static final long serialVersionUID = 1L;
    private boolean ocupada = false;
    private ArenaLogica arenaDecorada;
    private int id;

    public ArenaFisica(int id, ArenaLogica arenaDecorada) {
        this.id = id;
        this.arenaDecorada = arenaDecorada;
    }

    public synchronized void ocupar() throws InterruptedException {
        while (this.ocupada) {
            wait();
        }
        this.ocupada = true;
    }

    public synchronized void liberar() {
        this.ocupada = false;
        notifyAll();
    }

    public synchronized boolean estaOcupada() {
        return ocupada;
    }

    public String getNombre() {
        return arenaDecorada.getNombre();
    }

    public String getDetalle() {
        return arenaDecorada.getDetalle();
    }

    public int getPremio() {
        return arenaDecorada.getPremio();
    }

    public int getId() {
        return id;
    }

    public ArenaLogica getArenaDecorada() {
        return arenaDecorada;
    }
}
