package excepciones;

import java.io.Serializable;

public class PokemonNoPuedeUsarArmaE extends PokemonException implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de la excepcion PokemonNoPuedeUsarArmaE.
	 * @param nombre Nombre del pokemon que lanza la excepcion.
	 */
	public PokemonNoPuedeUsarArmaE(String nombre) {
		super("Este pokemon no puede usar arma");
		this.nombre = nombre;
	}
}
