package excepciones;

public class EntrenadorNoExisteException extends EntrenadorException {
	protected String nombre;

	/**
	 * Constructor de la excepcion EntrenadorNoExisteException.
	 * 
	 * @param nombre Nombre del entrenador que no existe.
	 */
	public EntrenadorNoExisteException(String nombre) {
		super("No existe el entrenador");
		this.nombre=nombre;
	}
	
	public String getNombre() {
		return nombre;
	}	
}
