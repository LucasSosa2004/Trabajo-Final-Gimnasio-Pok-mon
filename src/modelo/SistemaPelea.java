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
    private Map<Integer, Duelo> duelos = new HashMap<>();

    /** Lista de arenas f�sicas disponibles (recursos compartidos) */
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
     * Inicia el combate en una arena espec�fica.
     *
     * @param numArena N�mero de la arena donde ya existe un duelo en espera.
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
     * Inicializa la lista de arenas f�sicas con la cantidad especificada.
     * Por defecto, cada arena se crea como Bosque+F�cil.
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
     * Obtiene la lista interna de arenas f�sicas (se asume que ya se llam� a inicializarArenas).
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
        	System.out.println("asignar arena libre \n");
            for (ArenaFisica arena : arenas) {
            	System.out.println("FOR asignar arena libre \n");

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
    
    public Entrenador iniciarTorneo(Gimnasio gimnasio, EtapaTorneo etapa) throws EntrenadorNoExisteException, EntrenadorSinPokemonesException {
        System.out.println("\n--- INICIO DEL TORNEO (Eliminación directa) ---");
        List<Entrenador> participantes = new ArrayList<>(gimnasio.getInscriptosTorneo());
        List<Entrenador> ganadoresCuartos;
        Entrenador ganadorFinal = null;
        try {
            // ---------- CUARTOS DE FINAL (4 duelos simultáneos) ----------
            System.out.println("\n[CUARTOS DE FINAL]");
            ganadoresCuartos = ejecutarRonda("Cuartos", participantes, gimnasio);
            System.out.println("\ndespues de eejecuttar ronda cuartos");
            if (ganadoresCuartos.size() < 2) {
                System.out.println("No hay suficientes ganadores de cuartos para semifinales. Torneo finalizado.");
                return null;
            }

            // ---------- SEMIFINALES (2 duelos simultáneos) ----------
            System.out.println("\n[SEMIFINALES]");
            List<Entrenador> ganadoresSemi = ejecutarRonda("Semifinal", ganadoresCuartos, gimnasio);

            if (ganadoresSemi.size() < 2) {
                System.out.println("No hay dos ganadores de semifinales para la final. Torneo finalizado.");
                return null;
            }

            // ---------- FINAL (1 duelo) ----------
            System.out.println("\n[FINAL]");
            ArenaFisica arenaFinal = asignarArenaLibre();
            Duelo dFinal = gimnasio.crearDuelo(ganadoresSemi.get(0).getNombre(), ganadoresSemi.get(1).getNombre(),arenaFinal);
            if (dFinal != null) {
                try {
                    addDuelo(dFinal);
                    dFinal.iniciarDuelo();

                    ganadorFinal = dFinal.getGanador();
                    System.out.println("Ganador Final: " + ganadorFinal.getNombre());
                } catch (ArenaOcupadaException ae) {
                    System.out.println("Error: la arena está ocupada en la final.");
                } finally {
                    liberarArena(arenaFinal);
                }
            } else {
                System.out.println("No se pudo crear el duelo final. Torneo finalizado.");
                arenaFinal.liberar();
            }

            etapa.avanzarEtapa(); // pasa a FINALIZADO
            System.out.println("\n--- TORNEO FINALIZADO: ETAPA FINALIZADO ---");
            gimnasio.clearInscriptos();

        } catch (InterruptedException ie) {
            System.out.println("Error al asignar arena: " + ie.getMessage());
        }

        return ganadorFinal;
    }

    /**
     * Ejecuta una ronda de duelos en paralelo.
     * 
     * @param nombreRonda Nombre de la ronda ("Cuartos", "Semifinal", etc)
     * @param participantes Lista de entrenadores participantes
     * @param gimnasio Gimnasio para crear los duelos
     * @return Lista de ganadores de la ronda
     * @throws InterruptedException Si se interrumpe esperando arenas o duelos
     * @throws EntrenadorSinPokemonesException 
     * @throws EntrenadorNoExisteException 
     */
    private List<Entrenador> ejecutarRonda(String nombreRonda, List<Entrenador> participantes, Gimnasio gimnasio) 
        throws InterruptedException, EntrenadorNoExisteException, EntrenadorSinPokemonesException {
        List<Duelo> duelosRonda = new ArrayList<>();
        List<Entrenador> ganadores = new ArrayList<>();
        List<Thread> hilos = new ArrayList<>();

        // Crear los duelos de la ronda
        for (int i = 0; i < participantes.size(); i += 2) {
            if (i + 1 >= participantes.size()) break;
            
            ArenaFisica arena = null;
            try {
                arena = asignarArenaLibre();
                Duelo d = gimnasio.crearDuelo(participantes.get(i).getNombre(), participantes.get(i + 1).getNombre(), arena);
                if (d != null) {
                    addDuelo(d);
                    duelosRonda.add(d);
                    Thread t = new Thread(d);
                    hilos.add(t);
                    t.start();
                } else {
                    System.out.println("No se pudo crear duelo de " + nombreRonda + " entre " + 
                                     participantes.get(i).getNombre() + " y " + participantes.get(i + 1).getNombre());
                    liberarArena(arena);
                }
            } catch (ArenaOcupadaException ae) {
                System.out.println("Arena ocupada para duelo de " + nombreRonda + " entre " + 
                                 participantes.get(i).getNombre() + " y " + participantes.get(i + 1).getNombre());
                if (arena != null) liberarArena(arena);
            } catch (Exception e) {
                System.out.println("Error al crear duelo: " + e.getMessage());
                if (arena != null) liberarArena(arena);
            }
        }

        // Esperar a que terminen todos los duelos
        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Hilo interrumpido mientras esperaba duelo");
            }
        }

        // Recolectar ganadores y limpiar
        for (Duelo d : duelosRonda) {
            if (d.getGanador() != null) {
                ganadores.add(d.getGanador());
                d.getGanador().addCreditos(d.getArena().getPremio());
            }
            removeDuelo(d.getClave());
        }

        return ganadores;
    }
}
