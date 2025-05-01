package entrenador;

public class EntrenadorSinPokemonesException extends Exception {
	private String nombre;
	
	public EntrenadorSinPokemonesException(String nombre) {
		super("Entrenador sin pokemones");
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return this.nombre;
	}
}
