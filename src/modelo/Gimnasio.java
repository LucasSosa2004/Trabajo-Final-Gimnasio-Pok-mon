package modelo;

import java.util.ArrayList;
import java.util.HashMap;

import entrenador.Entrenador;
import excepciones.EntrenadorNoExisteException;
import excepciones.EntrenadorSinPokemonesException;
import excepciones.NombreUtilizadoException;

/**
 * Clase Gimnasio que implementa el patrón de diseño Singleton.
 * Representa un gimnasio con entrenadores, arenas y una tienda.
 * Solo puede existir una instancia de esta clase en toda la aplicación.
 */
public class Gimnasio {
    /** Instancia única de la clase */
    private static Gimnasio instancia;
    
    /** HashMap de entrenadores registrados en el gimnasio */
    private HashMap<String,Entrenador> entrenadores = new HashMap<>();
    
    /** Tienda asociada al gimnasio */
    private Tienda tienda;
    
    /**
     * Constructor privado que evita la instanciación desde fuera de la clase.
     */
    private Gimnasio() {
    	this.tienda=new Tienda();
    }
    
    /**
     * Método estático que devuelve la única instancia de la clase Gimnasio.
     * Si la instancia no existe, la crea.
     * 
     * @return La única instancia de Gimnasio
     */
    public static Gimnasio getInstancia() {
        if (instancia == null) {
            instancia = new Gimnasio();
        }
        return instancia;
    }
    
    public Entrenador getEntrenador(String nombre) throws EntrenadorNoExisteException{
    	Entrenador e= this.entrenadores.get(nombre);
    	if (e==null) {
    		throw new EntrenadorNoExisteException(nombre);
    	}
    	else
    		return e;
    }
    
    /**
     * Añade un entrenador al HashMap de entrenadores del gimnasio.
     * 
     * @param e El entrenador a añadir
     */
    public void addEntrenador(Entrenador e) throws NombreUtilizadoException {
		if (this.entrenadores.containsKey(e.getNombre()))
			throw new NombreUtilizadoException(e.getNombre());
		else	
			this.entrenadores.put(e.getNombre(), e);
	}
    
    /**
     * Devuelve el HashMap de todos los entrenadores registrados en el gimnasio.
     * 
     * @return ArrayList con los entrenadores del gimnasio
     */
    public HashMap<String,Entrenador> getEntrenadores() {
        return this.entrenadores;
    }
    
    
    /**
     * Establece la tienda asociada al gimnasio.
     * 
     * @param tienda La tienda a asociar con el gimnasio
     */
    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
    /**
     * Devuelve la tienda asociada al gimnasio.
     * 
     * @return La tienda del gimnasio
     */
    public Tienda getTienda() {
        return this.tienda;
    }
    
    
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

}