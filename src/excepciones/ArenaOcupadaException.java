package excepciones;

public class ArenaOcupadaException extends Exception {
	protected int numArena;

	public ArenaOcupadaException(int numArena) {
		super("Arena ocupada");
		this.numArena=numArena;
	}
	
	public int getNumArena() {
		return this.numArena;
	}	
}
