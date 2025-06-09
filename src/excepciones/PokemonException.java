package excepciones;

import java.io.Serializable;

public class PokemonException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String nombre;
	
	/**
	 * Constructor de la excepcion PokemonException.
	 * 
	 * @param arg Descripcion de la excepcion.
	 */
	public PokemonException (String arg) {
		super(arg);
	}
	
	public String getNombre() {
		return nombre;
	}	
}
