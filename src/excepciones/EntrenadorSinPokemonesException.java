package excepciones;

public class EntrenadorSinPokemonesException extends EntrenadorException {
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
