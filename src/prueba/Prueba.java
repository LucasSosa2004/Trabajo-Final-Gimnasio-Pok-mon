package prueba;



import modelo.PokemonAgua;
import modelo.PokemonFactory;
import modelo.PokemonFuego;

public class Prueba {

	public static void main(String[] args) {
		PokemonFactory factoria= new PokemonFactory();
		
		PokemonAgua p1=(PokemonAgua)factoria.getPokemon("aGua", "Magikarp");
		PokemonFuego p2=(PokemonFuego)factoria.getPokemon("fuEgo", "Charizard");
	
		
		System.out.println(p1.toString());
		System.out.println(p2.toString());

		
		
		p1.atacar(p2);
		p2.atacar(p1);
		
		p1.toString();
		p2.toString();
		
		System.out.println(p1.toString());
		System.out.println(p2.toString());

		
	}

}
