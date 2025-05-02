package excepciones;

public class EntrenadorSinPokemonesException extends EntrenadorException {
	protected String nombre;
	
	public EntrenadorSinPokemonesException(String nombre) {
		super("Entrenador sin pokemones");
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return this.nombre;
	}
}
