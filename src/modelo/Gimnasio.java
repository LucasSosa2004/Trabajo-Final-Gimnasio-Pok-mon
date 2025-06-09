package modelo;

import java.io.Serializable;
import java.util.*;

import entrenador.Entrenador;
import excepciones.EntrenadorNoExisteException;
import excepciones.EntrenadorSinPokemonesException;  
import excepciones.NombreUtilizadoException;

/**
 * Clase Gimnasio que implementa el patrón Singleton.
 * Contiene entrenadores, una tienda y métodos para inscribir al torneo.
 * 
 * <br><b>Invariante de clase:</b><br>
 * - La tienda no puede ser nula
 * - No puede haber más de 8 entrenadores inscritos en el torneo
 * - Los nombres de los entrenadores deben ser únicos
 */
public class Gimnasio implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Instancia única (Singleton) */
    private static Gimnasio instancia;

    /** Entrenadores registrados (clave = nombre en mayúsculas) */
    private Map<String, Entrenador> entrenadores = new HashMap<>();

    /** Tienda asociada al gimnasio */
    private Tienda tienda;

    /** Lista de entrenadores inscritos al torneo (máximo 8) */
    private List<Entrenador> inscritosTorneo = new ArrayList<>();

    private Gimnasio() {
        this.tienda = new Tienda();
    }

    /** Permite reasignar la instancia Singleton al cargar desde disco */
    public static void setInstancia(Gimnasio gim) {
        instancia = gim;
    }

    public static Gimnasio getInstancia() {
        if (instancia == null) {
            instancia = new Gimnasio();
        }
        return instancia;
    }

    /**
     * Devuelve el Entrenador con el nombre dado (en mayúsculas).
     *
     * @param nombre Nombre del entrenador (no puede ser nulo ni vacío)
     * @return El entrenador existente
     * @throws EntrenadorNoExisteException Si no existe
     */
    public Entrenador getEntrenador(String nombre) throws EntrenadorNoExisteException {
        String key = nombre.toUpperCase();
        Entrenador e = this.entrenadores.get(key);
        if (e == null) {
            throw new EntrenadorNoExisteException(nombre);
        }
        return e;
    }

    /**
     * Agrega un Entrenador al gimnasio.
     *
     * @param e Entrenador a agregar (su nombre no puede estar repetido)
     * @throws NombreUtilizadoException Si ya existe un entrenador con ese nombre
     */
    public void putEntrenador(Entrenador e) throws NombreUtilizadoException {
        String key = e.getNombre().toUpperCase();
        if (this.entrenadores.containsKey(key)) {
            throw new NombreUtilizadoException(e.getNombre());
        } else {
            this.entrenadores.put(key, e);
        }
    }

    public Map<String, Entrenador> getEntrenadores() {
        return this.entrenadores;
    }

    public Tienda getTienda() {
        return this.tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    // --- Métodos para inscripción al torneo ---

    /**
     * Inscribe un entrenador al torneo (hasta 8). Lanza excepción si:
     * 1) No existe el entrenador
     * 2) Ya está inscrito
     * 3) Ya hay 8 inscritos
     *
     * @param nombre Nombre del entrenador a inscribir
     * @throws EntrenadorNoExisteException  Si no existe en el gimnasio
     * @throws IllegalStateException        Si ya hay 8 inscritos
     * @throws IllegalArgumentException     Si el entrenador ya está inscrito
     */
    public void inscribirAlTorneo(String nombre) throws EntrenadorNoExisteException{
    	Entrenador e = getEntrenador(nombre);
        if (inscritosTorneo.contains(e)) {
            throw new IllegalArgumentException("El entrenador " + nombre + " ya esta inscrito.");
        }
        if (inscritosTorneo.size() >= 8) {
            throw new IllegalStateException("Ya hay 8 entrenadores inscritos.");
        }
        inscritosTorneo.add(e);
    }

    /**
     * Devuelve la lista de entrenadores inscritos actualmente (0 a 8).
     */
    public List<Entrenador> getInscriptosTorneo() {
        return this.inscritosTorneo;
    }

    /** Limpia la lista de inscritos (para reiniciar un nuevo torneo). */
    public void clearInscriptos() {
        this.inscritosTorneo.clear();
    }

    /**
     * Crea un Duelo entre dos entrenadores que ya están inscritos en el torneo.
     * En caso de error, devuelve null y muestra mensaje por consola.
     * 
     * @param nombre1 Nombre del primer entrenador
     * @param nombre2 Nombre del segundo entrenador
     * @param arena Arena donde se realizará el duelo
     * @return El duelo creado
     * @throws EntrenadorNoExisteException Si alguno de los entrenadores no existe
     * @throws EntrenadorSinPokemonesException Si alguno no tiene Pokémons activos
     */
    public Duelo crearDuelo(String nombre1, String nombre2, ArenaFisica arena) throws EntrenadorNoExisteException, EntrenadorSinPokemonesException {
        assert nombre1 != null && !nombre1.isEmpty() : "El nombre del primer entrenador no puede ser nulo o vacío";
        assert nombre2 != null && !nombre2.isEmpty() : "El nombre del segundo entrenador no puede ser nulo o vacío";
        assert arena != null : "La arena no puede ser nula";
        Entrenador e1 = this.getEntrenador(nombre1);
        Entrenador e2 = this.getEntrenador(nombre2);

        return new Duelo(e1, e2, arena);
    }

    @Override
    public String toString() {
        return "Gimnasio [entrenadores=" + entrenadores.keySet() +
               ", inscritosTorneo=" + inscritosTorneo.size() + ", tienda=" + tienda + "]";
    }
}
