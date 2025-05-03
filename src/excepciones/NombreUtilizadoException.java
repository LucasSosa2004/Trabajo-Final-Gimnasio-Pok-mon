package excepciones;

public class NombreUtilizadoException extends Exception {
	protected String nombre;

	/**
	 * Constructor de la excepcion NombreUtilizadoException.
	 * 
	 * @param nombre Nombre que ya ha sido utilizado.
	 */
	public NombreUtilizadoException(String nombre) {
		super("Nombre ya utilizado");
		this.nombre=nombre;
	}
	
	public String getNombre() {
		return this.nombre;
	}	
}

