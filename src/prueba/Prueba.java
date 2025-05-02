package prueba;

import entrenador.*;
import excepciones.ArenaOcupadaException;
import excepciones.CompraImposibleException;
import excepciones.NombreUtilizadoException;
import excepciones.TipoDesconocidoException;
import interfaces.*;
import modelo.*;
import pokemones.*;



public class Prueba {

	public static void main(String[] args) {
			
			
			Gimnasio gimnasio = Gimnasio.getInstancia();
			PokemonFactory pokFac= new PokemonFactory();
			SistemaPelea sistemaPelea = SistemaPelea.getInstancia();
			Entrenador e1 = new Entrenador("ASH", 100);
			Entrenador e2 = new Entrenador("JJJ", 10);
			IHechizo hT = new HechizoTormenta();
			IHechizo hV = new HechizoViento();
			IHechizo hN = new HechizoNiebla();
			
			e1.setHechizo(hN);
			e2.setHechizo(hV);

			 try {
				 
				gimnasio.putEntrenador(e1);
				gimnasio.putEntrenador(e2);
				e1.putPokemon(pokFac.getPokemon("Agua","magikarp"));
				e1.putPokemon(pokFac.getPokemon("Piedra","OinIX"));
				e1.putPokemon(pokFac.getPokemon("Agua","charizard"));
				e2.putPokemon(pokFac.getPokemon("Agua","vaporeon"));
				e2.putPokemon(pokFac.getPokemon("Agua","magikarp2"));
				e2.putPokemon(pokFac.getPokemon("Agua","charizard3"));
				e1.putPokemon(pokFac.getPokemon("Agua","pikax"));
				e1.putPokemon(pokFac.getPokemon("Agua","charis")); 
				e1.setEquipo("magikarp", "pikachu", "charizard");
				e2.setEquipo("vaporeon");
				
				gimnasio.getTienda().compraPokemon(e1, "AGUA", "BULBASUR");
				sistemaPelea.addDuelo(gimnasio.crearDuelo("ASH","JJJ",1));
				
				sistemaPelea.iniciarCombate(1);
				
				System.out.println(e1);
				System.out.println(e2);
			} catch (CompraImposibleException e) {
				System.out.println("Creditos insuficientes, en posesion "+e.getCreditos()+", necesarios "+e.getCosto());
			}catch (NombreUtilizadoException e) {
				System.out.println("El nombre "+e.getNombre()+" ya esta en uso");
			} catch (TipoDesconocidoException e) {
				System.out.println("El tipo "+e.getTipo()+" es desconocido");
			} catch (ArenaOcupadaException e) {
				System.out.println("La arena "+e.getNumArena()+" esta ocupada");
			}
			
		
	}	
	
	
}
