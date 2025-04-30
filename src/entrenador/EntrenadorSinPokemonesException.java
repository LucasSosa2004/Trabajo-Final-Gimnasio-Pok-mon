package entrenador;

public class EntrenadorSinPokemonesException extends Exception {
	private Entrenador e;
	
	public EntrenadorSinPokemonesException(Entrenador e) {
		super("Entrenador sin pokemones");
		this.e = e;
	}
	
	public Entrenador getEntrenador() {
		return this.e;
	}
}
