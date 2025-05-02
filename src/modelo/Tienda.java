package modelo;

import armas.*;
import entrenador.Entrenador;
import excepciones.*;
import pokemones.*;

public class Tienda {
	public ArmaFactory armaFactory;
	public PokemonFactory pokemonFactory;	
	
    public Tienda() {
		this.armaFactory = new ArmaFactory();
		this.pokemonFactory = new PokemonFactory();
	}
	
	
    public void comprarArma(String tipo,Entrenador e, Pokemon p) throws CompraImposibleException, PokemonNoPuedeUsarArmaE{
        Arma a;
		try {
			a = armaFactory.crearArma(tipo);
	        if(e.getCreditos() < a.getCosto())
	        	throw new CompraImposibleException(e.getCreditos(),a.getCosto());
			else {
				p.setArma(a);
				e.subCreditos(a.getCosto());
			}
		} catch (TipoDesconocidoException e1) {
			System.out.println("El tipo de arma "+e1.getTipo()+" es desconocido");
		} catch (PokemonNoPuedeUsarArmaE e1) {
			System.out.println("El pokemon" + e1.getNombre() + " no puede usar arma");
		}

    }
    


	public void compraPokemon(Entrenador e, String tipo, String nombre) throws CompraImposibleException{  	 
    	 try {
    		Pokemon p = pokemonFactory.getPokemon(tipo, nombre); 
        	if(e.getCreditos() < p.getCosto())
        		throw new CompraImposibleException(e.getCreditos(),p.getCosto());
			e.putPokemon(p);
			e.subCreditos(p.getCosto());
		} catch (NombreUtilizadoException e1) {
			System.out.println("El nombre "+e1.getNombre()+" ya esta siendo utilizado");
		} catch (TipoDesconocidoException e1) {
			System.out.println("El tipo de pokemon "+e1.getTipo()+" es desconocido");

		}
    }


	@Override
	public String toString() {
		return "Tienda [armaFactory=" + armaFactory + ", pokemonFactory=" + pokemonFactory + "]";
	}
	
	

}

