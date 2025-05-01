package entrenador;

public class NombreUtilizadoException extends Exception {
	protected String nombre;

	public NombreUtilizadoException(String nombre) {
		super("Nombre ya utilizado");
		this.nombre=nombre;
	}
	
	public String getNombre() {
		return this.nombre;
	}	
}

