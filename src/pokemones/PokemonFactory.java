package pokemones;

import armas.Arma;
import excepciones.PokemonNoPuedeUsarArmaE;
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
	public Pokemon getPokemon(String tipo, String nombre, Arma arma) throws TipoDesconocidoException,PokemonNoPuedeUsarArmaE{
		tipo=tipo.toUpperCase();
		nombre=nombre.toUpperCase();
		switch (tipo) {
			case "PIEDRA":
				return new PokemonPiedra(nombre,arma);
			default:{
				if(tipo!="FUEGO" && tipo!="AGUA" && tipo!="HIELO")
                	throw new TipoDesconocidoException(tipo);
				else
					throw new PokemonNoPuedeUsarArmaE(nombre); 
			}
			
						
		}
		
	}

	@Override
	public String toString() {
		return "PokemonFactory []";
	}
	

}
