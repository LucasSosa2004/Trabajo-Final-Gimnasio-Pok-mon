package excepciones;

import java.io.Serializable;

public class ArenaOcupadaException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
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
