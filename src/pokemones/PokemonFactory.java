package pokemones;

import excepciones.TipoDesconocidoException;

public class PokemonFactory {
	
	
	public Pokemon getPokemon(String tipo, String nombre) throws TipoDesconocidoException{
		tipo=tipo.toUpperCase();
		nombre=nombre.toUpperCase();
		switch (tipo) {
			case "AGUA":
				return new PokemonAgua(nombre);
			case "FUEGO":
				return new PokemonFuego(nombre);
			case "HIELO":
				return new PokemonHielo(nombre);
			case "PIEDRA":
				return new PokemonPiedra(nombre);
			default://crear excepcion
                throw new TipoDesconocidoException(tipo);
			
						
		}
		
	}

	@Override
	public String toString() {
		return "PokemonFactory []";
	}
	

}
