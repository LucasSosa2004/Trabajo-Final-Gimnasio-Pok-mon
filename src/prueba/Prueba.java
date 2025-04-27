package prueba;

import modelo.*;



public class Prueba {

	public static void main(String[] args) {

		try {
			
			
			Gimnasio gimnasio = new Gimnasio();
			PokemonFactory factory = new PokemonFactory();
			Entrenador e1 = new Entrenador("ASH", 0);
			Entrenador e2 = new Entrenador("JJJ", 10);


			gimnasio.addEntrenador(e1);
			gimnasio.addEntrenador(e2);
			
			e1.getPokemones().add(new PokemonAgua("magikarp"));
			e1.getPokemones().add(new PokemonHielo("pikachu"));
			e1.getPokemones().add(new PokemonFuego("charizard"));
			e2.getPokemones().add(new PokemonHielo("vaporeon"));
			e2.getPokemones().add(new PokemonFuego("magikarp2"));
			e2.getPokemones().add(new PokemonAgua("charizard3"));
			e1.getPokemones().add(new PokemonHielo("pikax"));
			e1.getPokemones().add(new PokemonAgua("charis"));


			e1.setEquipo("magikarp", "pikachu", "charizard");
			//e1.setEquipo("pikax");
			e2.setEquipo("vaporeon");

			
			Arena arena = new Arena(e1,e2);

			gimnasio.addArena(arena);
			
			//arena=gimnasio.getArenas().getFirst();
			Entrenador ganador = arena.iniciarDuelo();
			//System.out.println(ganador.toString());
			System.out.println("GANADOR: " + ganador.getEquipoActivo());
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch (EntrenadorSinPokemonesException e) {
			Entrenador sinPokemones = e.getEntrenador();
			System.out.println(sinPokemones.toString());
		}

		
	}

}
