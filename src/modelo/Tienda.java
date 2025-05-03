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
	
	/**
     * Permite a un entrenador comprar un arma para uno de sus Pokemones.
     * 
     * @param tipo Tipo de arma a comprar
     * @param e Entrenador que realiza la compra
     * @param nombre Nombre del Pokémon que usará el arma
     * @throws CompraImposibleException Si el entrenador no tiene suficientes créditos
	 * @throws PokemonNoPuedeUsarArmaE Si el Pokémon no puede usar el arma
     */
    public void comprarArma(String tipo,Entrenador e, String nombre) throws CompraImposibleException, PokemonNoPuedeUsarArmaE{
        Arma a;
        Pokemon p;
		try {
			p=e.buscaPokemon(nombre);
			a = armaFactory.getArma(tipo);
	        if(e.getCreditos() < a.getCosto())
	        	throw new CompraImposibleException(e.getCreditos(),a.getCosto());
			else {
				p.setArma(a);
				e.subCreditos(a.getCosto());
			}
		} catch (TipoDesconocidoException e1) {
			System.out.println("El tipo de arma "+e1.getTipo()+" es desconocido");
		} catch (PokemonNoPuedeUsarArmaE e1) {
			System.out.println("El pokemon " + e1.getNombre() + " no puede usar arma");
		} catch (PokemonNoExisteException e1) {
			System.out.println("El pokemon "+e1.getNombre()+" no existe");
		}

    }
    

	/**
	 * Permite a un entrenador comprar un Pokemon de un tipo específico.
	 * 
	 * @param e Entrenador que realiza la compra
	 * @param tipo Tipo de Pokemon a comprar
	 * @param nombre Nombre del Pokemon a comprar
	 * @throws CompraImposibleException Si el entrenador no tiene suficientes creditos
	 */
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

