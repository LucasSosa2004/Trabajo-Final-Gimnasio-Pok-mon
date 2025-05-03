package excepciones;

public class PokemonNoExisteException extends PokemonException {
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
