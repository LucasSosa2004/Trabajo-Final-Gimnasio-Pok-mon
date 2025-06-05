package modelo;

import java.util.HashMap;

import entrenador.Entrenador;
import excepciones.EntrenadorNoExisteException;
import excepciones.EntrenadorSinPokemonesException;
import excepciones.NombreUtilizadoException;

/**
 * Clase Gimnasio que implementa el patrón Singleton.
 * Contiene entrenadores, una tienda y métodos para crear duelos.
 */
public class Gimnasio {
    /** Instancia única (Singleton) */
    private static Gimnasio instancia;

    /** Entrenadores registrados (clave = nombre en mayúsculas) */
    private HashMap<String, Entrenador> entrenadores = new HashMap<>();

    /** Tienda asociada al gimnasio */
    private Tienda tienda;

    private Gimnasio() {
        this.tienda = new Tienda();
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
        nombre = nombre.toUpperCase();
        Entrenador e = this.entrenadores.get(nombre);
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
        if (this.entrenadores.containsKey(e.getNombre())) {
            throw new NombreUtilizadoException(e.getNombre());
        } else {
            this.entrenadores.put(e.getNombre(), e);
        }
    }

    public HashMap<String, Entrenador> getEntrenadores() {
        return this.entrenadores;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Tienda getTienda() {
        return this.tienda;
    }

    
    public Duelo crearDuelo(String nombre1, String nombre2, ArenaFisica arena) {
        try {
            Entrenador e1 = this.getEntrenador(nombre1);
            Entrenador e2 = this.getEntrenador(nombre2);
            return new Duelo(e1, e2, arena);
        } catch (EntrenadorNoExisteException ex) {
            System.out.println("El entrenador " + ex.getNombre() + " no existe.");
            return null;
        } catch (EntrenadorSinPokemonesException ex) {
            System.out.println("El entrenador " + ex.getNombre() + " no tiene pokemones para iniciar un duelo.");
            return null;
        }
    }

    @Override
    public String toString() {
        return "Gimnasio [entrenadores=" + entrenadores + ", tienda=" + tienda + "]";
    }
}