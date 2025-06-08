package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entrenador.Entrenador;
import excepciones.ArenaOcupadaException;
import excepciones.EntrenadorNoExisteException;
import excepciones.EntrenadorSinPokemonesException;

/**
 * SistemaPelea maneja la asignaci�n/ocupaci�n de arenas y el registro de duelos activos.
 */
public class SistemaPelea implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Instancia �nica (Singleton) de SistemaPelea */
    private static SistemaPelea instancia;

    /** Mapeo de duelos activos: clave = id de arena, valor = Duelo */
    private List<Thread> listaDuelos = new ArrayList<>();

    /** Lista de arenas f�sicas disponibles (recursos compartidos) */
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

    public List<Thread> getDuelos() {
        return this.listaDuelos;
    }


    /**
     * Agrega un duelo al sistema de peleas.
     *
     * @param duelo El duelo a agregar (no puede ser nulo)
     * @throws ArenaOcupadaException Si ya existe un duelo en esa misma clave (arena)
     */
    public synchronized void addDuelo(Duelo duelo) throws ArenaOcupadaException {
            this.listaDuelos.add(new Thread(duelo));
    
    }




    public Map<Integer,ArenaFisica> getArenas() {
        return this.arenas;
    }

    public synchronized void liberarArena(ArenaFisica arena) {
        arena.liberar();
        notifyAll();
    }

	@Override
	public String toString() {
		return "SistemaPelea [listaDuelos=" + listaDuelos + ", arenas=" + arenas + "]";
	}


    

   
}
