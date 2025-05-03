package excepciones;

public class ArenaOcupadaException extends Exception {
	protected int numArena;

	/**
	 * Constructor de la excepcion ArenaOcupadaException.
	 * 
	 * @param numArena El numero de arena ocupada.
	 */
	public ArenaOcupadaException(int numArena) {
		super("Arena ocupada");
		this.numArena=numArena;
	}
	
	public int getNumArena() {
		return this.numArena;
	}	
}
