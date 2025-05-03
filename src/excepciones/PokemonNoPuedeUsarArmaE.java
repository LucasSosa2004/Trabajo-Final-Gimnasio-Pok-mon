package excepciones;

public class PokemonNoPuedeUsarArmaE extends PokemonException {
	
	/**
	 * Constructor de la excepcion PokemonNoPuedeUsarArmaE.
	 * @param nombre Nombre del pokemon que lanza la excepcion.
	 */
	public PokemonNoPuedeUsarArmaE(String nombre) {
		super("Este pokemon no puede usar arma");
		this.nombre = nombre;
	}
}
