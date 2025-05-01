package modelo;

import java.util.*;

import armas.Arma;
import armas.ArmaFactory;
import entrenador.Entrenador;
import excepciones.CompraImposibleException;
import excepciones.NombreUtilizadoException;
import excepciones.PokemonNoPuedeUsarArmaE;
import pokemones.Pokemon;
import pokemones.PokemonFactory;

public class Tienda {
	public ArmaFactory armaFactory;
	public PokemonFactory pokemonFactory;
	/*public void comprarArma(String nombre,Entrenador e, PokemonPiedra p) throws CompraImposibleException{
		// Arma arma = ArmaFactory.crearArma(nombre); // Revisar
		if(e.getCreditos() < arma.getCosto())
			throw new CompraImposibleException(e.getCreditos(),arma.getCosto());
		else
			p.setArma(arma);
	}*/
	//como aceptan el arma los pokemones
	
	
	
    public void comprarArma(String tipo,Entrenador e, Pokemon p) throws CompraImposibleException, PokemonNoPuedeUsarArmaE{
        Arma a = armaFactory.crearArma(tipo);
        if(e.getCreditos() < a.getCosto())
        	throw new CompraImposibleException(e.getCreditos(),a.getCosto());
		else {
			try {
				p.setArma(a);
				e.subCreditos(a.getCosto());
			} catch (PokemonNoPuedeUsarArmaE e1) {
				System.out.println("El pokemon" + e1.getNombre() + " no puede usar arma");
			}
		}
    }
    
    public Tienda() {
		this.armaFactory = new ArmaFactory();
		this.pokemonFactory = new PokemonFactory();
	}

	public void compraPokemon(Entrenador e, String tipo, String nombre) throws CompraImposibleException{
    	 Pokemon p = pokemonFactory.getPokemon(tipo, nombre); 
    	 if(e.getCreditos() < p.getCosto())
    		 throw new CompraImposibleException(e.getCreditos(),p.getCosto());
    	 try {
			e.addPokemon(p);
			e.subCreditos(p.getCosto());
		} catch (NombreUtilizadoException e1) {
			System.out.println("El nombre "+e1.getNombre()+" ya esta siendo utilizado");
		}
    }

}

