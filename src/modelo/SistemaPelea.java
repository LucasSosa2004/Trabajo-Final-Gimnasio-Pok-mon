package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import entrenador.Entrenador;
import excepciones.ArenaOcupadaException;

/**
 * SistemaPelea maneja la asignación/ocupación de arenas y el registro de duelos
 * activos.
 * 
 * <br>
 * <b>Invariante de clase:</b><br>
 * - La lista de duelos no puede ser nula - El mapa de arenas no puede ser nulo
 * - Cada arena debe tener un ID único
 */
public class SistemaPelea implements Serializable {
	private static final long serialVersionUID = 1L;


	/** Instancia única (Singleton) de SistemaPelea */
	private static SistemaPelea instancia;

	/** Mapeo de duelos activos: clave = id de arena, valor = Duelo */
	private List<Duelo> listaDuelos = new ArrayList<>();
	
	/** Mapeo de duelos auxiliares: clave = id de arena, valor = Duelo */
	private List<Duelo> listaDuelosAux = new ArrayList<>();

	/** Lista de arenas físicas disponibles (recursos compartidos) */
	private HashMap<Integer, ArenaFisica> arenas = new HashMap<>();

	private SistemaPelea() {
		// Constructor privado para Singleton
	}

	/** Permite reasignar la instancia Singleton al cargar desde disco */
	public static void setInstancia(SistemaPelea sp) {
		instancia = sp;
	}

	public static SistemaPelea getInstancia() {
		if (instancia == null) {
			instancia = new SistemaPelea();
		}
		return instancia;
	}

	public List<Duelo> getDuelos() {
		return this.listaDuelos;
	}

	/**
	 * Agrega un duelo al sistema de peleas.
	 *
	 * @param duelo El duelo a agregar
	 * @throws ArenaOcupadaException Si ya existe un duelo en esa misma clave
	 *                               (arena)
	 */
	public void addDuelo(Duelo duelo) throws ArenaOcupadaException {
		this.listaDuelos.add(duelo);
	}

	public Map<Integer, ArenaFisica> getArenas() {
		return this.arenas;
	}

	@Override
	public String toString() {
		return "SistemaPelea [listaDuelos=" + listaDuelos + ", arenas=" + arenas + "]";
	}

	public void ejecutarRonda() {
		List<Thread> hilos = new ArrayList<>();
		
		this.listaDuelosAux = new ArrayList<>(this.listaDuelos);

		for (Duelo duelo : this.listaDuelos) {
			hilos.add(new Thread(duelo));
		}

		for (Thread hilo : hilos) {
			hilo.start();
		}

		// Esperar a que terminen
		for (Thread hilo : hilos) {
			try {
				hilo.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	    this.listaDuelos.clear();

	
	}

	public List<Duelo> getListaDuelosAux() {
		return listaDuelosAux;
	}
}
