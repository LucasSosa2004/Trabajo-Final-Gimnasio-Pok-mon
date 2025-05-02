package excepciones;

public class EntrenadorNoExisteException extends EntrenadorException {
	protected String nombre;

	public EntrenadorNoExisteException(String nombre) {
		super("No existe el entrenador");
		this.nombre=nombre;
	}
	
	public String getNombre() {
		return nombre;
	}	
}
