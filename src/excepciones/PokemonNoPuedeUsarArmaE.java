package excepciones;

public class PokemonNoPuedeUsarArmaE extends PokemonException {
	
	public PokemonNoPuedeUsarArmaE(String nombre) {
		super("Este pokemon no puede usar arma");
		this.nombre = nombre;
	}
}
