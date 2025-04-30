package modelo;

import java.util.*;

import armas.Arma;
import armas.ArmaFactory;
import entrenador.CompraImposibleException;
import entrenador.Entrenador;
import pokemones.Pokemon;
import pokemones.PokemonFactory;
import pokemones.PokemonNoPuedeUsarArmaE;

public class Tienda {
	public ArmaFactory arma;
	public PokemonFactory pokemon;
	/*public void comprarArma(String nombre,Entrenador e, PokemonPiedra p) throws CompraImposibleException{
		// Arma arma = ArmaFactory.crearArma(nombre); // Revisar
		if(e.getCreditos() < arma.getCosto())
			throw new CompraImposibleException(e.getCreditos(),arma.getCosto());
		else
			p.setArma(arma);
	}*/
	//como aceptan el arma los pokemones
    public void comprarArma(String nombre,Entrenador e, Pokemon p) throws CompraImposibleException, PokemonNoPuedeUsarArmaE{
        Arma a = arma.crearArma(nombre);
        if(e.getCreditos() < a.getCosto())
        	throw new CompraImposibleException(e.getCreditos(),a.getCosto());
		else {
			try {
				p.setArma(a);
			} catch (PokemonNoPuedeUsarArmaE e1) {
				System.out.println("El pokemon" + e1.getNombre() + " no puede usar arma");
			}
		}
    }
    
    public void compraPokemon(Entrenador e, String tipo, String nombre) throws CompraImposibleException{
    	 Pokemon p = pokemon.getPokemon(tipo, nombre); 
    	 if(e.getCreditos() < p.getCosto())
    		 throw new CompraImposibleException(e.getCreditos(),p.getCosto());
    	 
    }

}

