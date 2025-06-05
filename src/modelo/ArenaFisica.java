package modelo;

public class ArenaFisica {
    private boolean ocupada = false;
    private ArenaLogica arenaDecorada;
    private int id;

    public ArenaFisica(int id, ArenaLogica arenaDecorada) {
        this.id = id;
        this.arenaDecorada = arenaDecorada;
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
