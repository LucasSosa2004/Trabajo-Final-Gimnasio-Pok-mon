package modelo;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import excepciones.ArenaOcupadaException;

public class SistemaPelea {
    /** Instancia única (Singleton) de SistemaPelea */
    private static SistemaPelea instancia;
    
    /** Mapeo de duelos activos: clave = id de arena, valor = Duelo */
    private HashMap<Integer, Duelo> duelos = new HashMap<>();
    
    /** Lista de arenas físicas disponibles (recursos compartidos) */
    private List<ArenaFisica> arenas = new ArrayList<>();
    
    private int cantidadArenas = 3; // Por defecto; se puede parametrizar

    private SistemaPelea() {
        // Constructor privado para Singleton
    }

    public static SistemaPelea getInstancia() {
        if (instancia == null) {
            instancia = new SistemaPelea();
        }
        assert instancia != null : "La instancia no puede ser nula";
        return instancia;
    }

    public HashMap<Integer, Duelo> getDuelos() {
        assert this.duelos != null : "El HashMap de duelos no puede ser nulo";
        return this.duelos;
    }

    public Duelo getDuelo(int numArena) {
        assert numArena >= 0 : "El número de arena debe ser mayor o igual a 0";
        return this.duelos.get(numArena);
    }

    public void removeDuelo(int numArena) {
        assert numArena >= 0 : "El número de arena debe ser mayor o igual a 0";
        this.duelos.remove(numArena);
        assert !this.duelos.containsKey(numArena) : "El duelo no fue eliminado correctamente";
    }

    /**
     * Agrega un duelo al sistema de peleas.
     * 
     * @param duelo El duelo a agregar (no puede ser nulo)
     * @throws ArenaOcupadaException Si ya existe un duelo en esa misma clave (arena)
     */
    public void addDuelo(Duelo duelo) throws ArenaOcupadaException {
        assert duelo != null : "El duelo no puede ser nulo";
        if (this.duelos.containsKey(duelo.getClave())) {
            throw new ArenaOcupadaException(duelo.getClave());
        } else {
            this.duelos.put(duelo.getClave(), duelo);
        }
        assert this.duelos.containsKey(duelo.getClave()) : "El duelo no fue agregado correctamente";
    }

    /**
     * Inicia el combate en una arena específica.
     *  
     * @param numArena Número de la arena donde ya existe un duelo en espera.
     */
    public void iniciarCombate(int numArena) {
        assert this.duelos.containsKey(numArena) : "El número de arena no corresponde a un duelo existente";
        Duelo duelo = this.getDuelo(numArena);
        duelo.iniciarDuelo();
        duelo.getEntrenador1().vaciarEquipoActivo();
        duelo.getEntrenador2().vaciarEquipoActivo();
        this.removeDuelo(numArena);
        assert !this.duelos.containsKey(numArena) : "El duelo no fue eliminado correctamente";
    }

    /**
     * Inicializa la lista de arenas físicas con la cantidad especificada.
     * Por defecto, cada arena se crea como Bosque+Fácil.
     */
    public void inicializarArenas(int cantidad) {
        this.cantidadArenas = cantidad;
        arenas.clear();
        for (int i = 0; i < cantidad; i++) {
            ArenaLogica base = new ArenaBosque();
            ArenaLogica decorada = new ArenaFacil(base);
            arenas.add(new ArenaFisica(i + 1, decorada));
        }
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

    /**
     * Inicia todos los duelos de la lista en paralelo (cada Duelo implementa Runnable).
     */
    public void iniciarTorneoConcurrente(List<Duelo> duelos) {
        for (Duelo duelo : duelos) {
            Thread hilo = new Thread(duelo);
            hilo.start();
        }
    }

    @Override
    public String toString() {
        String resultado = "SistemaPelea [duelos=" + duelos + "]";
        assert resultado != null : "El resultado de toString no puede ser nulo";
        return resultado;
    }
}
