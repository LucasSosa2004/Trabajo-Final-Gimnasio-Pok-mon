package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;

import entrenador.Entrenador;
import excepciones.EntrenadorSinPokemonesException;
import pokemones.Pokemon;

/**
 * El duelo recibe dos entrenadores, opcionalmente cada uno lanza un hechizo,
 * luego selecciona autom�ticamente sus primeros Pok�mons y resuelve el duelo
 * hasta que uno se queda sin equipo activo.
 */
public class Duelo extends Observable implements Runnable, Serializable {
	private static final long serialVersionUID = 1L;

	private Entrenador e1, e2, ganador;
	private int clave;
	private boolean dueloTerminado;
	private ArenaFisica arena;
	private Queue<Pokemon> colaAuxE1 = new LinkedList<>();
	private Queue<Pokemon> colaAuxE2 = new LinkedList<>();

	/**
	 * Crea un nuevo Duelo en la arena dada.
	 *
	 * @param e1    Entrenador 1 (debe tener al menos 1 Pok�mon activo)
	 * @param e2    Entrenador 2 (debe tener al menos 1 Pok�mon activo)
	 * @param arena Arena f�sica donde se librar� este duelo
	 * @throws EntrenadorSinPokemonesException si alguno no tiene Pok�mons activos
	 */
	public Duelo(Entrenador e1, Entrenador e2, ArenaFisica arena) throws EntrenadorSinPokemonesException {
		if (e1.getEquipoActivo().isEmpty())
			throw new EntrenadorSinPokemonesException(e1.getNombre());
		if (e2.getEquipoActivo().isEmpty())
			throw new EntrenadorSinPokemonesException(e2.getNombre());

		this.e1 = e1;
		this.e2 = e2;
		this.ganador = null;
		this.clave = arena.getId();
		this.dueloTerminado = false;
		this.arena = arena;
	}

	public int getClave() {
		return clave;
	}

	public boolean isDueloTerminado() {
		return dueloTerminado;
	}

	public Entrenador getGanador() {
		return ganador;
	}

	public Entrenador getEntrenador1() {
		return e1;
	}

	public Entrenador getEntrenador2() {
		return e2;
	}

	/** Nuevo m�todo para obtener la arena asociada */
	public ArenaFisica getArena() {
		return arena;
	}

	/**
	 * Resuelve el duelo de forma s�ncrona (se invoca dentro de run()). Al terminar,
	 * otorga el premio y recarga los Pok�mons del ganador.
	 */
	public void iniciarDuelo() {
		assert !e1.getEquipoActivo().isEmpty() : "El equipo activo del entrenador 1 no puede estar vac�o";
		assert !e2.getEquipoActivo().isEmpty() : "El equipo activo del entrenador 2 no puede estar vac�o";
		String informacion;
		boolean turno = true;
		informacion=("COMIENZA_DUELO: " + e1.getNombre() + " vs " + e2.getNombre());
		setChanged();
		notifyObservers();
		System.out.println(informacion);

		Pokemon p1 = e1.proximoPokemon();
		Pokemon p2 = e2.proximoPokemon();
		Pokemon p;
		colaAuxE1.add(p1);
		colaAuxE2.add(p2);

		// Hechizos iniciales: cada entrenador lanza sobre el primer Pok�mon del rival
		e1.hechizar(p2);
		e2.hechizar(p1);


		System.out.println("va a empezar el combate");

		// Bucle hasta que uno se quede sin Pok�mon
		while (p1 != null && p2 != null) {
			if (turno) {
				if (p1.getVitalidad() > 0 && p2.getVitalidad() > 0) {
					p1.atacar(p2);
				}
			} else {
				if (p2.getVitalidad() > 0 && p1.getVitalidad() > 0) {
					p2.atacar(p1);
				}
			}
			turno = !turno;

			if (p1 != null && p1.getVitalidad() <= 0) {
				p2.recibeExp();
				p1 = e1.proximoPokemon();

				if (p1 != null)
					colaAuxE1.add(p1);
			}
			if (p2 != null && p2.getVitalidad() <= 0) {
				p1.recibeExp();
				p2 = e2.proximoPokemon();
				if (p2 != null)
					colaAuxE2.add(p2);

			}
		}
		System.out.println("se determino un ganador");

		// Determinar ganador
		if (p1 == null) {
			this.ganador = e2;
			while (!colaAuxE2.isEmpty()) {
				p = colaAuxE2.poll();
				p.recargar();
				ganador.getEquipoActivo().add(p);
			}
		} else {
			this.ganador = e1;
			while (!colaAuxE1.isEmpty()) {
				p = colaAuxE1.poll();
				p.recargar();
				ganador.getEquipoActivo().add(p);
			}
		}

		// Otorgar premio seg�n la arena decorada
		this.ganador.addCreditos(arena.getPremio());
		this.dueloTerminado = true;

		// Recargar la vitalidad de los Pok�mons del ganador

		assert this.ganador != null : "El ganador no puede ser nulo";
		assert this.dueloTerminado : "El duelo debe estar marcado como terminado";

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		informacion = "RESULTADO: Ganó " + this.getGanador().getNombre() + "!";
		setChanged();
		notifyObservers(informacion);
		System.out.println("RESULTADO: " + informacion);
	}

	@Override
	public void run() {
		try {
			this.arena.ocupar();
			this.iniciarDuelo();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.arena.liberar();
		}
	}

	@Override
	public String toString() {
		return "Duelo [e1=" + e1.getNombre() + ", e2=" + e2.getNombre() + ", ganador="
				+ (ganador != null ? ganador.getNombre() : "N/A") + ", clave=" + clave + ", dueloTerminado="
				+ dueloTerminado + "]";
	}
}
