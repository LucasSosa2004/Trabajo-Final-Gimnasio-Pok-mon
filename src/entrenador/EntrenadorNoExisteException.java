package entrenador;

public class EntrenadorNoExisteException extends Exception {
	protected String nombre;

	public EntrenadorNoExisteException(String nombre) {
		super("No existe el entrenador");
		this.nombre=nombre;
	}
	
	public String getNombre() {
		return nombre;
	}	
}
