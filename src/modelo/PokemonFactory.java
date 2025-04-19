package modelo;

public class PokemonFactory {
	
	
	public Pokemon getPokemon(String tipo, String nombre){
		tipo.toUpperCase();
		switch (tipo) {
			case "AGUA":
				return new PokemonAgua(nombre);
			case "FUEGO":
				return new PokemonFuego(nombre);
			case "HIELO":
				return new PokemonHielo(nombre);
			case "PIEDRA":
				return new PokemonPiedra(nombre);
			default:
				return null;
				
			
		
		}
		
	}

}
