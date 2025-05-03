package modelo;

import java.util.ArrayList;
import java.util.HashMap;

import entrenador.Entrenador;
import excepciones.EntrenadorNoExisteException;
import excepciones.EntrenadorSinPokemonesException;
import excepciones.NombreUtilizadoException;

/**
 * Clase Gimnasio que implementa el patron de disenio Singleton.
 * Representa un gimnasio con entrenadores, arenas y una tienda.
 * Solo puede existir una instancia de esta clase en toda la aplicacion.
 */
public class Gimnasio {
    /** Instancia unica de la clase */
    private static Gimnasio instancia;
    
    /** HashMap de entrenadores registrados en el gimnasio */
    private HashMap<String,Entrenador> entrenadores = new HashMap<>();
    
    /** Tienda asociada al gimnasio */
    private Tienda tienda;
    
    /**
     * Constructor privado que evita la instanciacion desde fuera de la clase.
     */
    private Gimnasio() {
    	this.tienda=new Tienda();
    }
    
    /**
     * Metodo estatico que devuelve la unica instancia de la clase Gimnasio.
     * Si la instancia no existe, la crea.
     * 
     * @return La unica instancia de Gimnasio
     */
    public static Gimnasio getInstancia() {
        if (instancia == null) {
            instancia = new Gimnasio();
        }
        return instancia;
    }
    
    /**
     * <b>Pre:</b><br>
     * El nombre del entrenador no puede ser nulo ni vacio.<br><br>
     * <b>Post:</b><br>
     * Devuelve el entrenador asociado al nombre.<br>
     * 
     * @param nombre Nombre del entrenador
     * @return El entrenador asociado
     * @throws EntrenadorNoExisteException Si el entrenador no existe
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
     * Aniade un entrenador al HashMap de entrenadores del gimnasio.
     * 
     * @param e El entrenador a aniadir
     */
    public void putEntrenador(Entrenador e) throws NombreUtilizadoException {
		if (this.entrenadores.containsKey(e.getNombre()))
			throw new NombreUtilizadoException(e.getNombre());
		else	
			this.entrenadores.put(e.getNombre(), e);
	}

    
    public HashMap<String,Entrenador> getEntrenadores() {
        return this.entrenadores;
    }
    

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Tienda getTienda() {
        return this.tienda;
    }
    
    /**
     * Crea un duelo entre dos entrenadores en una arena específica.
     * 
     * @param nombre1 Nombre del primer entrenador
     * @param nombre2 Nombre del segundo entrenador
     * @param numArena Número de la arena
     * @return El duelo creado, o null si ocurre un error
     */
    public Duelo crearDuelo(String nombre1, String nombre2, int numArena) {
    	try {
	    	Entrenador e1=this.getEntrenador(nombre1);
	    	Entrenador e2=this.getEntrenador(nombre2);
	    	return new Duelo(e1,e2,numArena);
    	}
    	catch(EntrenadorNoExisteException e) {
    		System.out.println("El entrenador "+e.getNombre()+" no existe.");
    		return null;

    	} catch (EntrenadorSinPokemonesException e) {
    		System.out.println("El entrenador "+e.getNombre()+" no tiene pokemones para iniciar un duelo.");
    		return null;
    	}
    	
    	
    }

	@Override
	public String toString() {
		return "Gimnasio [entrenadores=" + entrenadores + ", tienda=" + tienda + "]";
	}
    
    

}