package pokemones;

public class PokemonFactory {
	
	
	public Pokemon getPokemon(String tipo, String nombre){
		tipo=tipo.toUpperCase();
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
                throw new IllegalArgumentException("Tipo de pokemon desconocido: " + tipo);
			
						
		}
		
	}

	@Override
	public String toString() {
		return "PokemonFactory []";
	}
	

}
