package excepciones;

public class PokemonException extends Exception{
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
