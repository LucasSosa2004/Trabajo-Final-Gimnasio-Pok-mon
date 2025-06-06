package excepciones;

import java.io.Serializable;

public class PokemonNoExisteException extends PokemonException implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String nombre;
	
	/**
	 * Constructor de la excepcion PokemonNoExisteException.
	 * 
	 * @param nombre Nombre del pokemon que no existe.
	 */
	public PokemonNoExisteException(String nombre) {
		super("Pokemon "+nombre+" no encontrado");
		this.nombre=nombre;
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}
	

}
