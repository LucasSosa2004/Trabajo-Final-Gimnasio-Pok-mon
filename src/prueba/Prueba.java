package prueba;

import modelo.*;

public class Prueba {

	public static void main(String[] args) {
		PokemonFactory factoria= new PokemonFactory();
		
		PokemonAgua p1=(PokemonAgua)factoria.getPokemon("aGua", "Magikarp");
		PokemonFuego p2=(PokemonFuego)factoria.getPokemon("fuEgo", "Charizard");
	
	}

}
