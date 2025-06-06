package excepciones;

import java.io.Serializable;

public class EntrenadorSinPokemonesException extends EntrenadorException implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String nombre;
	
	/**
	 * Constructor de la excepcion EntrenadorSinPokemonesException.
	 * 
	 * @param nombre Nombre del entrenador sin pokemones.
	 */
	public EntrenadorSinPokemonesException(String nombre) {
		super("Entrenador sin pokemones");
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return this.nombre;
	}
}
