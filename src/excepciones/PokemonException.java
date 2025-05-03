package excepciones;

public class PokemonException extends Exception{
	protected String nombre;
	
	/**
	 * Constructor de la excepcion PokemonException.
	 * 
	 * @param nombre Nombre del pokemon que lanza la excepcion.
	 */
	public PokemonException (String arg) {
		super(arg);
	}
	
	public String getNombre() {
		return nombre;
	}	
}
