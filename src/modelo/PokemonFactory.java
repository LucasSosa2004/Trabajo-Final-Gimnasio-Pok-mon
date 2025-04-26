package modelo;

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
			default:{
				System.out.println("devuelve null");
				return null;
			}
						
		}
		
	}

	@Override
	public String toString() {
		return "PokemonFactory []";
	}
	

}
