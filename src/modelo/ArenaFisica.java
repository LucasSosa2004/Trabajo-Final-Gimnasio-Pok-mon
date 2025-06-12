package modelo;

import java.io.Serializable;
import java.util.concurrent.Semaphore;
import interfaces.IArena;

public class ArenaFisica implements Serializable {
    private static final long serialVersionUID = 1L;
    private Semaphore semaforo;
    private final IArena arenaDecorada;
    private int id;

    public ArenaFisica(int id, IArena arenaDecorada) {
        this.id = id;
        this.arenaDecorada = arenaDecorada;
        this.semaforo = new Semaphore(1);
    }

    public void ocupar() throws InterruptedException {
        this.semaforo.acquire();

    }

    public void liberar() {
        this.semaforo.release();
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

    public IArena getArenaDecorada() {
        return arenaDecorada;
    }
}