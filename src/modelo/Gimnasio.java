package modelo;

import java.io.Serializable;
import java.util.*;

import entrenador.Entrenador;
import excepciones.EntrenadorNoExisteException;
import excepciones.EntrenadorSinPokemonesException;  
import excepciones.NombreUtilizadoException;

/**
 * Clase Gimnasio que implementa el patr�n Singleton.
 * Contiene entrenadores, una tienda y m�todos para inscribir al torneo.
 */
public class Gimnasio implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Instancia �nica (Singleton) */
    private static Gimnasio instancia;

    /** Entrenadores registrados (clave = nombre en may�sculas) */
    private Map<String, Entrenador> entrenadores = new HashMap<>();

    /** Tienda asociada al gimnasio */
    private Tienda tienda;

    /** Lista de entrenadores inscritos al torneo (m�ximo 8) */
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
     * Devuelve el Entrenador con el nombre dado (en may�sculas).
     *
     * @param nombre Nombre del entrenador (no puede ser nulo ni vac�o)
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

    // --- M�todos para inscripci�n al torneo ---

    /**
     * Inscribe un entrenador al torneo (hasta 8). Lanza excepci�n si:
     * 1) No existe el entrenador
     * 2) Ya est� inscrito
     * 3) Ya hay 8 inscritos
     *
     * @param nombre Nombre del entrenador a inscribir
     * @throws EntrenadorNoExisteException  Si no existe en el gimnasio
     * @throws IllegalStateException        Si ya hay 8 inscritos
     * @throws IllegalArgumentException     Si el entrenador ya est� inscrito
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
     * Crea un Duelo entre dos entrenadores que ya est�n inscritos en el torneo.
     * En caso de error, devuelve null y muestra mensaje por consola.
     * @throws EntrenadorNoExisteException 
     * @throws EntrenadorSinPokemonesException 
     */
    public Duelo crearDuelo(String nombre1, String nombre2, ArenaFisica arena) throws EntrenadorNoExisteException, EntrenadorSinPokemonesException {

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
