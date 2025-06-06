package modelo;

import java.io.Serializable;
import java.util.*;

import excepciones.ArenaOcupadaException;

/**
 * SistemaPelea maneja la asignación/ocupación de arenas y el registro de duelos activos.
 */
public class SistemaPelea implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Instancia única (Singleton) de SistemaPelea */
    private static SistemaPelea instancia;

    /** Mapeo de duelos activos: clave = id de arena, valor = Duelo */
    private Map<Integer, Duelo> duelos = new HashMap<>();

    /** Lista de arenas físicas disponibles (recursos compartidos) */
    private List<ArenaFisica> arenas = new ArrayList<>();

    /** Cantidad total de arenas (se inicializa con default 3 si no se configura) */
    private int cantidadArenas = 3;

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

    public Map<Integer, Duelo> getDuelos() {
        return this.duelos;
    }

    public Duelo getDuelo(int numArena) {
        return this.duelos.get(numArena);
    }

    public void removeDuelo(int numArena) {
        this.duelos.remove(numArena);
    }

    /**
     * Agrega un duelo al sistema de peleas.
     *
     * @param duelo El duelo a agregar (no puede ser nulo)
     * @throws ArenaOcupadaException Si ya existe un duelo en esa misma clave (arena)
     */
    public synchronized void addDuelo(Duelo duelo) throws ArenaOcupadaException {
        if (duelo == null) {
            throw new IllegalArgumentException("El duelo no puede ser nulo");
        }
        int clave = duelo.getClave();
        if (this.duelos.containsKey(clave)) {
            throw new ArenaOcupadaException(clave);
        } else {
            this.duelos.put(clave, duelo);
        }
    }

    /**
     * Inicia el combate en una arena específica.
     *
     * @param numArena Número de la arena donde ya existe un duelo en espera.
     */
    public void iniciarCombate(int numArena) {
        Duelo duelo = this.getDuelo(numArena);
        if (duelo == null) {
            throw new IllegalArgumentException("No existe duelo activo en la arena " + numArena);
        }
        duelo.iniciarDuelo();
        duelo.getEntrenador1().vaciarEquipoActivo();
        duelo.getEntrenador2().vaciarEquipoActivo();
        this.removeDuelo(numArena);
    }

    /**
     * Inicializa la lista de arenas físicas con la cantidad especificada.
     * Por defecto, cada arena se crea como Bosque+Fácil.
     */
    public synchronized void inicializarArenas(int cantidad) {
        this.cantidadArenas = cantidad;
        arenas.clear();
        for (int i = 0; i < cantidad; i++) {
            ArenaLogica base = new ArenaBosque();
            ArenaLogica decorada = new ArenaFacil(base);
            arenas.add(new ArenaFisica(i + 1, decorada));
        }
    }

    /**
     * Obtiene la lista interna de arenas físicas (se asume que ya se llamó a inicializarArenas).
     */
    public List<ArenaFisica> getArenas() {
        return this.arenas;
    }

    /**
     * Asigna la primera arena libre que encuentre. Si no hay ninguna, espera.
     *
     * @return arena libre ya marcada como ocupada.
     * @throws InterruptedException Si se interrumpe el hilo mientras espera.
     */
    public synchronized ArenaFisica asignarArenaLibre() throws InterruptedException {
        while (true) {
            for (ArenaFisica arena : arenas) {
                if (!arena.estaOcupada()) {
                    arena.ocupar();
                    return arena;
                }
            }
            wait();
        }
    }

    /**
     * Libera una arena (la marca como disponible) y notifica a hilos en espera.
     */
    public synchronized void liberarArena(ArenaFisica arena) {
        arena.liberar();
        notifyAll();
    }

    @Override
    public String toString() {
        return "SistemaPelea [duelosActivos=" + duelos.keySet() +
               ", #arenas=" + arenas.size() + "]";
    }
}
