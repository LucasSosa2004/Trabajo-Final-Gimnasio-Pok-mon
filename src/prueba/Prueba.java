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
import modelo.ArenaFisica;         // Importamos la clase ArenaFisica
import pokemones.PokemonFactory;

import java.util.ArrayList;
import java.util.List;

public class Prueba {

    public static void main(String[] args) {

        // Referencia al gimnasio y al sistemaPelea en el main creados mediante singleton 
        Gimnasio gimnasio = Gimnasio.getInstancia();
        SistemaPelea sistemaPelea = SistemaPelea.getInstancia();

        // Inicializamos las arenas antes de crear cualquier duelo
        sistemaPelea.inicializarArenas(3);

        // Factory de pokemones y armas para tener entrenadores que tengan pokemones por defecto
        PokemonFactory pokFac = new PokemonFactory();
        ArmaFactory armFac = new ArmaFactory();

        // Hechizos usados por los entrenadores
        IHechizo hT = new HechizoTormenta();
        IHechizo hV = new HechizoViento();
        IHechizo hN = new HechizoNiebla();
        IHechizo hN2 = new HechizoNiebla();

        // Entrenadores creados en el main, distintos nombres (es su código de identificacion) y con distintos créditos
        Entrenador ash = new Entrenador("ASH", 100);
        Entrenador knekro = new Entrenador("knekro", 0);
        Entrenador brock = new Entrenador("brock", 200, null);
        Entrenador misty = new Entrenador("Misty", 0, hT);
        Entrenador e5 = new Entrenador("ASH", 912);
        Entrenador dawn = new Entrenador("dawn", 570);
        Entrenador oak = new Entrenador("oak", 0);

        try {

            // Armas usadas por los pokemones
            Arma espada1 = armFac.getArma("espada");
            Arma hacha1 = armFac.getArma("hacha");
            Arma hacha2 = armFac.getArma("hacha");
            // **ERROR** intento crear un tipo de arma desconocido
            // Arma arma = armFac.getArma("lanza");

            // Seteo hechizos en algunos entrenadores
            ash.setHechizo(hN);
            knekro.setHechizo(hV);
            dawn.setHechizo(hN2);

            // Metemos a los entrenadores al gimnasio
            gimnasio.putEntrenador(ash);
            gimnasio.putEntrenador(knekro);
            gimnasio.putEntrenador(misty);
            // **ERROR** entrenador con identificador repetido
            // gimnasio.putEntrenador(e5);
            gimnasio.putEntrenador(dawn);
            gimnasio.putEntrenador(oak);

            // Seteo los pokemones iniciales de algunos entrenadores
            ash.putPokemon(pokFac.getPokemon("Agua", "Magikarp"));
            ash.putPokemon(pokFac.getPokemon("Piedra", "Onix", hacha1));
            ash.putPokemon(pokFac.getPokemon("Fuego", "Charizard"));

            knekro.putPokemon(pokFac.getPokemon("Fuego", "Magmar"));
            knekro.putPokemon(pokFac.getPokemon("Fuego", "Ninetales"));
            knekro.putPokemon(pokFac.getPokemon("Fuego", "Quilava"));

            brock.putPokemon(pokFac.getPokemon("Hielo", "Glaceon"));
            brock.putPokemon(pokFac.getPokemon("Agua", "Squirtle"));
            // **ERROR** A brock se le intenta asignar un tipo de pokemon desconocido
            // brock.putPokemon(pokFac.getPokemon("Psiquico", "Kdravra"));

            misty.putPokemon(pokFac.getPokemon("Piedra", "Probopass", hacha2));
            // **ERROR** a Misty se le intenta asignar otro pokemon a su lista con un nombre ya utilizado (codigo de id)
            // misty.putPokemon(pokFac.getPokemon("Piedra", "Probopass", espada1));

            // termino de setear

            // El entrenador dawn compra pokemones ya que no tenia
            gimnasio.getTienda().compraPokemon(dawn, "AGUA", "PIPLUP");
            gimnasio.getTienda().compraPokemon(dawn, "fuego", "Blaziken");
            gimnasio.getTienda().compraPokemon(dawn, "piedRa", "Aerodactyl");
            gimnasio.getTienda().compraPokemon(dawn, "Hielo", "Regice");

            gimnasio.getTienda().comprarArma("Espada", dawn, "Aerodactyl");

            // **ERROR** Brock intenta comprar una espada y asignarla a un pokemon invalido
            // gimnasio.getTienda().comprarArma("Espada", brock,"Glaceon");

            // **ERROR** ASH intenta asignarle un arma a un pokemon desconocido
            // gimnasio.getTienda().comprarArma("Espada", ash,"Raichu");

            // COMBATE ENTRE KNEKRO Y ASH 1V2
            // Los entrenadores ash y knekro que van a pelear "eligen" su equipo
            ash.setEquipo("Charizard");
            knekro.setEquipo("Magmar", "Ninetales");

            // Obtenemos la primera arena libre (id = 1)
            ArenaFisica arena1 = sistemaPelea.asignarArenaLibre();
            Duelo d1 = gimnasio.crearDuelo("ASH", "knekro", arena1);
            System.out.println("Duelo: " + d1.getEntrenador1().getNombre() + " VS " +
                               d1.getEntrenador2().getNombre());
            sistemaPelea.addDuelo(d1);
            // Comienza el duelo asignado a la arena 1
            sistemaPelea.iniciarCombate(arena1.getId());
            System.out.println("El ganador/a es: " + d1.getGanador().getNombre());

            // ash y knekro recargan sus pokemones y vuelven a elegir su equipo
            ash.buscaPokemon("Charizard").recargar();
            knekro.buscaPokemon("Magmar").recargar();
            knekro.buscaPokemon("Ninetales").recargar();

            // Ash y knekro eligen su equipo y obtienen la siguiente arena libre
            ash.setEquipo("Onix");
            knekro.setEquipo("Magmar", "Ninetales", "Quilava");
            ArenaFisica arena2 = sistemaPelea.asignarArenaLibre();
            Duelo d4 = gimnasio.crearDuelo("ASH", "knekro", arena2);
            // NOTA: no iniciamos d4 inmediatamente para simular arena ocupada

            // COMBATE ENTRE MISTY Y DAWN 1V1
            // Los entrenadores misty y dawn que van a pelear "eligen" su equipo
            misty.setEquipo("Probopass");
            dawn.setEquipo("Aerodactyl");
            // Obtenemos la siguiente arena libre (sería la tercera)
            ArenaFisica arena3 = sistemaPelea.asignarArenaLibre();
            Duelo d3 = gimnasio.crearDuelo("Misty", "dawn", arena3);
            System.out.println("Duelo: " + d3.getEntrenador1().getNombre() + " VS " +
                               d3.getEntrenador2().getNombre());

            sistemaPelea.addDuelo(d3);
            // **ERROR** Se intentaba agregar d4 antes de iniciar d3 en la misma arena
            // sistemaPelea.addDuelo(d4);

            // Comienza el duelo asignado a la arena 3
            sistemaPelea.iniciarCombate(arena3.getId());
            System.out.println("El ganador/a es: " + d3.getGanador().getNombre());

            // **ERROR** dawn intenta comprar en la tienda sin tener créditos
            // gimnasio.getTienda().compraPokemon(dawn, "Hielo", "Rice");

            // **ERROR** ash y oak intentan comenzar un combate y oak no tiene pokemones
            // ash.setEquipo("Onix");
            // Duelo d5 = gimnasio.crearDuelo("ash", "oak", 7);

            // **ERROR** COMBATE ENTRE BROCK Y MISTY (brock fuera del gimnasio)
            /*
            brock.setEquipo("Glaceon");
            misty.setEquipo("Probopass");
            // Al no estar inscrito brock en el gimnasio, crearDuelo devuelve null
            Duelo d2 = gimnasio.crearDuelo("Misty", "brock", 2);
            sistemaPelea.addDuelo(d2);
            sistemaPelea.iniciarCombate(2);
            */

            // INICIALIZAR COMPONENTES PARA TORNEO CONCURRENTE
            // (ya habíamos inicializado 3 arenas al inicio)
            List<Duelo> duelosTorneo = new ArrayList<>();
            try {
                // Asignar arenas y crear duelos concurrentes
                for (int i = 0; i < 3; i++) { // 3 duelos de ejemplo, dado que hay 3 arenas
                    ArenaFisica arenaLibre = sistemaPelea.asignarArenaLibre();
                    Duelo duelo = gimnasio.crearDuelo("ASH", "knekro", arenaLibre);
                    duelosTorneo.add(duelo);
                    sistemaPelea.addDuelo(duelo);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sistemaPelea.iniciarTorneoConcurrente(duelosTorneo);

        } catch (CompraImposibleException e) {
            System.out.println("Créditos insuficientes, en posesión " +
                               e.getCreditos() + ", necesarios " + e.getCosto());
        } catch (NombreUtilizadoException e) {
            System.out.println("El nombre " + e.getNombre() + " ya está en uso");
        } catch (TipoDesconocidoException e) {
            System.out.println("El tipo " + e.getTipo() + " es desconocido");
        } catch (ArenaOcupadaException e) {
            System.out.println("La arena " + e.getNumArena() + " está ocupada");
        } catch (PokemonNoPuedeUsarArmaE e) {
            System.out.println("El pokémon " + e.getNombre() + " no puede usar arma");
        } catch (PokemonNoExisteException e) {
            System.out.println("El pokémon " + e.getNombre() + " no existe");
        }

    }

}