package prueba;

import armas.Arma;
import armas.ArmaFactory;
import entrenador.Entrenador;
import entrenador.HechizoNiebla;
import entrenador.HechizoTormenta;
import entrenador.HechizoViento;
import excepciones.ArenaOcupadaException;
import excepciones.CompraImposibleException;
import excepciones.NombreUtilizadoException;
import excepciones.PokemonNoExisteException;
import excepciones.PokemonNoPuedeUsarArmaE;
import excepciones.TipoDesconocidoException;
import interfaces.IHechizo;
import modelo.Duelo;
import modelo.Gimnasio;
import modelo.SistemaPelea;
import pokemones.PokemonFactory;



public class Prueba {

	public static void main(String[] args) {
			
			//Referencia al gimnasio y el sistemaPelea en el main creados mediante singleton 
			Gimnasio gimnasio = Gimnasio.getInstancia();
			SistemaPelea sistemaPelea = SistemaPelea.getInstancia();

			//Factory de pokemones y armas para tener entrenadores que tengan pokemones por defecto
			PokemonFactory pokFac= new PokemonFactory();
			ArmaFactory armFac = new ArmaFactory();
			
			//Hechizos usados por los entrenadores
			IHechizo hT = new HechizoTormenta();
			IHechizo hV = new HechizoViento();
			IHechizo hN = new HechizoNiebla();
			IHechizo hN2 = new HechizoNiebla();


			
			//Entrenadores creados en el main, distintos nombres (es su codigo de identificacion) y con distintos creditos
			Entrenador ash = new Entrenador("ASH", 100);
			Entrenador knekro = new Entrenador("knekro", 0);
			Entrenador brock = new Entrenador("brock", 200,null);
			Entrenador misty = new Entrenador("Misty", 0, hT);
			Entrenador e5 = new Entrenador("ASH", 912);
			Entrenador dawn = new Entrenador("dawn", 570);
			Entrenador oak = new Entrenador("oak",0);
			



			try {
				
				//Armas usadas por los pokemones
				Arma espada1 = armFac.getArma("espada");
				Arma hacha1 = armFac.getArma("hacha");
				Arma hacha2 = armFac.getArma("hacha");
				//**ERROR** intento crear un tipo de arma desconocido
				//Arma arma = armFac.getArma("lanza");

				
				
				//Seteo hechizos en algunos entrenadores
				ash.setHechizo(hN);
				knekro.setHechizo(hV);
				dawn.setHechizo(hN2);

				//Metemos a los entrenadores al gimnasio
				gimnasio.putEntrenador(ash);
				gimnasio.putEntrenador(knekro);
				gimnasio.putEntrenador(misty);
				//**ERROR** entrenador con identificador repetido
				//gimnasio.putEntrenador(e5); 
				gimnasio.putEntrenador(dawn);
				gimnasio.putEntrenador(oak);
				
				
				//Seteo los pokemones iniciales de algunos entrenadores
				ash.putPokemon(pokFac.getPokemon("Agua","Magikarp"));
				ash.putPokemon(pokFac.getPokemon("Piedra","Onix",hacha1));
				ash.putPokemon(pokFac.getPokemon("Fuego","Charizard"));
				
				knekro.putPokemon(pokFac.getPokemon("Fuego","Magmar"));
				knekro.putPokemon(pokFac.getPokemon("Fuego","Ninetales"));
				knekro.putPokemon(pokFac.getPokemon("Fuego","Quilava"));
				
				brock.putPokemon(pokFac.getPokemon("Hielo","Glaceon"));
				brock.putPokemon(pokFac.getPokemon("Agua","Squirtle"));
				//**ERROR** A brock se le intenta asignar un tipo de pokemon desconocido
				//brock.putPokemon(pokFac.getPokemon("Psiquico","Kdravra"));
				
				misty.putPokemon(pokFac.getPokemon("Piedra","Probopass",hacha2));
				//**ERROR** a Misty se le intenta asignar otro pokemon a su lista con un nombre ya utilizado (codigo de id)
				//misty.putPokemon(pokFac.getPokemon("Piedra","Probopass",espada1));
				

				
				//termino de setear
				
				
				// El entrenador dawn compra pokemones ya que no tenia
				gimnasio.getTienda().compraPokemon(dawn, "AGUA", "PIPLUP");
				gimnasio.getTienda().compraPokemon(dawn, "fuego", "Blaziken");
				gimnasio.getTienda().compraPokemon(dawn, "piedRa", "Aerodactyl");
				gimnasio.getTienda().compraPokemon(dawn, "Hielo", "Regice");
				
				gimnasio.getTienda().comprarArma("Espada", dawn,"Aerodactyl");
				
				
				//**ERROR** Brock intenta comprar una espada y asignarla a un pokemon invalido
				//gimnasio.getTienda().comprarArma("Espada", brock,"Glaceon");

				//**ERROR** ASH intenta asignarle un arma a un pokemon desconocido
				//gimnasio.getTienda().comprarArma("Espada", ash,"Raichu");

				
				//COMBATE ENTRE KNEKRO Y ASH 1V2
				//Los entrenadores ash y knekro que van a pelear "eligen" su equipo
				ash.setEquipo("Charizard");
				knekro.setEquipo("Magmar","Ninetales");				
				// Se le asigna un duelo a ash y knekro en la arena 1
				Duelo d1=gimnasio.crearDuelo("ASH","knekro",1);
				System.out.println("Duelo: " + d1.getEntrenador1().getNombre() + " VS " + d1.getEntrenador2().getNombre() );
				sistemaPelea.addDuelo(d1);
				// Comienza el duelo asignado a la arena 1
				sistemaPelea.iniciarCombate(1);
				System.out.println("El ganador/a es: " + d1.getGanador().getNombre());
				
				
				//ash y knekro recargan sus pokemones y vuelven a elegir su equipo
				ash.buscaPokemon("Charizard").recargar();
				knekro.buscaPokemon("Magmar").recargar();
				knekro.buscaPokemon("Ninetales").recargar();
	
				//Ash y knekro eligen su equipo y se les asigna la arena 3
				ash.setEquipo("onix");
				knekro.setEquipo("Magmar","Ninetales","Quilava");		
				Duelo d4=gimnasio.crearDuelo("ASH","knekro",3);

				//COMBATE ENTRE MISTY Y DAWN 1V1
				//Los entrenadores misty y dawn que van a pelear "eligen" su equipo
				misty.setEquipo("Probopass");
				dawn.setEquipo("Aerodactyl");			
				// Se le asigna un duelo a misty y dawn en la arena 3
				Duelo d3=gimnasio.crearDuelo("misty","dawn",3);
				System.out.println("Duelo: " + d3.getEntrenador1().getNombre() + " VS " + d3.getEntrenador2().getNombre() );

				sistemaPelea.addDuelo(d3);
				//**ERROR** Se intenta asignar otro duelo a la misma arena (3) antes de que el duelo anterior comience y finalice
				//sistemaPelea.addDuelo(d4);
				// Comienza el duelo asignado a la arena 3
				sistemaPelea.iniciarCombate(3);
				System.out.println("El ganador/a es: " + d3.getGanador().getNombre());

				
				//**ERROR** dawn intenta comprar en la tienda sin tener creditos
				//gimnasio.getTienda().compraPokemon(dawn, "Hielo", "Rice");
				
				
				//**ERROR** ash y oak intentan comenzar un combate y oak no tiene pokemones
				//ash.setEquipo("onix");
				//Duelo d5=gimnasio.crearDuelo("ash","oak",7);
				
				//**ERROR** COMBATE ENTRE BROCK Y MISTY (brock fuera del gimnasio)
				//Los entrenadores que van a pelear "eligen" su equipo
				
				brock.setEquipo("Glaceon");
				misty.setEquipo("Probopass");
				
				// El entrenador 4 (Misty) se le asigna un duelo contra el entrenador 3 (brock) en la arena 2
				// Al no estar inscripto en el gimnasio el entrenador 3, cuando se lo busca no se encuentra y devuelve null
				// por lo que el duelo no se asigna y cuando se quiere iniciar arroja nullException (no ser null es PRE)
				Duelo d2=gimnasio.crearDuelo("Misty","brock",2);
				sistemaPelea.addDuelo(d2);
				// Comienza el duelo asignado a la arena 2
				sistemaPelea.iniciarCombate(2);
				

			} catch (CompraImposibleException e) {
				System.out.println("Creditos insuficientes, en posesion "+e.getCreditos()+", necesarios "+e.getCosto());
			}catch (NombreUtilizadoException e) {
				System.out.println("El nombre "+e.getNombre()+" ya esta en uso");
			} catch (TipoDesconocidoException e) {
				System.out.println("El tipo "+e.getTipo()+" es desconocido");
			} catch (ArenaOcupadaException e) {
				System.out.println("La arena "+e.getNumArena()+" esta ocupada");
			} catch (PokemonNoPuedeUsarArmaE e) {
				System.out.println("El pokemon " + e.getNombre() + " no puede usar arma");
			} catch (PokemonNoExisteException e) {
				System.out.println("El pokemon "+e.getNombre()+" no existe");
			}
			
		
	}	
	
	
}
