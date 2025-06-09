package excepciones;

import java.io.Serializable;

public class EntrenadorNoExisteException extends EntrenadorException implements Serializable {
	private static final long serialVersionUID = 1L;
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
