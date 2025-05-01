package prueba;

import entrenador.*;
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
				gimnasio.addEntrenador(e1);
				gimnasio.addEntrenador(e2);
				e1.addPokemon(pokFac.getPokemon("Agua","magikarp"));
				e1.addPokemon(pokFac.getPokemon("Agua","pikachu"));
				e1.addPokemon(pokFac.getPokemon("Agua","charizard"));
				e2.addPokemon(pokFac.getPokemon("Agua","vaporeon"));
				e2.addPokemon(pokFac.getPokemon("Agua","magikarp2"));
				e2.addPokemon(pokFac.getPokemon("Agua","charizard3"));
				e1.addPokemon(pokFac.getPokemon("Agua","pikax"));
				e1.addPokemon(pokFac.getPokemon("Agua","charis"));
			} catch (NombreUtilizadoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	


			e1.setEquipo("magikarp", "pikachu", "charizard");
			//e1.setEquipo("pikax");
			e2.setEquipo("vaporeon");

			 try {
				gimnasio.getTienda().compraPokemon(e1, "AGUA", "BULBASUR");
			} catch (CompraImposibleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				sistemaPelea.addDuelo(gimnasio.crearDuelo("ASH","JJJ",1));
				sistemaPelea.iniciarCombate(1);
				System.out.println(e1);
				System.out.println(e2);

			} catch (ArenaOcupadaException e) {
				System.out.println("La arena "+e.getNumArena()+" esta ocupada");
			}
			//sistemaPelea.addDuelo(gimnasio.crearDuelo("z","u",1));
			
			//sistemaPelea.iniciarTorneo();
			
			
			
			//arena=gimnasio.getArenas().getFirst();
			//Entrenador ganador = arena.iniciarDuelo();
			//System.out.println(ganador.toString());
			//System.out.println("GANADOR: " + ganador.getEquipoActivo());


		
	
	}
}
