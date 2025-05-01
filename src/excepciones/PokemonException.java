package excepciones;

public class PokemonException extends Exception{
	protected String nombre;
	public PokemonException (String arg) {
		super(arg);
	}
	
	public String getNombre() {
		return nombre;
	}	
}
