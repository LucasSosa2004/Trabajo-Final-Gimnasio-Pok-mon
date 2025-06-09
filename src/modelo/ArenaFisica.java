package modelo;

import java.io.Serializable;
import java.util.concurrent.Semaphore;

public class ArenaFisica implements Serializable {
	private static final long serialVersionUID = 1L;
	private Semaphore semaforo;
	private ArenaLogica arenaDecorada;
	private int id;

	public ArenaFisica(int id, ArenaLogica arenaDecorada) {
		this.id = id;
		this.arenaDecorada = arenaDecorada;
		this.semaforo = new Semaphore(1);
	}

	public void ocupar() throws InterruptedException {

		System.out.println("Arena fisica" + this.id + " ocupada, hilo esperando...");

		System.out.println("Arena fisica " + this.id + " ocupada por hilo " + Thread.currentThread().getName());
		this.semaforo.acquire();

	}

	public void liberar() {
		System.out.println("Arena fisica " + this.id + " liberada por hilo " + Thread.currentThread().getName());
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

	public ArenaLogica getArenaDecorada() {
		return arenaDecorada;
	}
}
