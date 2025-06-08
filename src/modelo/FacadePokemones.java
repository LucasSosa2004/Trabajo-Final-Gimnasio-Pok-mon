package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import armas.ArmaFactory;
import entrenador.Entrenador;
import excepciones.ArenaOcupadaException;
import excepciones.CompraImposibleException;
import excepciones.EntrenadorNoExisteException;
import excepciones.EntrenadorSinPokemonesException;
import excepciones.NombreUtilizadoException;
import excepciones.PokemonNoExisteException;
import excepciones.PokemonNoPuedeUsarArmaE;
import excepciones.TipoDesconocidoException;
import pokemones.Pokemon;
import pokemones.PokemonFactory;

public class FacadePokemones {
	private Gimnasio gimnasio;
	private SistemaPelea sistemaPelea;
	private static FacadePokemones instancia;
	private PokemonFactory pokFac;
	private ArmaFactory armFac;
	private int numArenas;
	private EtapaTorneo etapa;

	private FacadePokemones() {
		this.gimnasio = Gimnasio.getInstancia();
		this.sistemaPelea = SistemaPelea.getInstancia();
		this.armFac = new ArmaFactory();
		this.pokFac = new PokemonFactory();
		this.numArenas = 0;
		this.etapa = new EtapaTorneo();
	}

	public static FacadePokemones getInstancia() {
		if (instancia == null) {
			instancia = new FacadePokemones();
		}
		return instancia;
	}

	public Gimnasio getGimnasio() {
		return gimnasio;
	}

	public SistemaPelea getSistemaPelea() {
		return sistemaPelea;
	}

	public PokemonFactory getPokFact() {
		return this.pokFac;
	}

	public ArmaFactory getArmFact() {
		return this.armFac;
	}

	public void comprarPokemon(Entrenador entrenador, String tipoPokemon, String nombrePokemon)
			throws CompraImposibleException, TipoDesconocidoException {
		this.gimnasio.getTienda().compraPokemon(entrenador, tipoPokemon, nombrePokemon);

	}

	public Entrenador crearEntrenador(String nombreEntrenador, int creditos) {
		Entrenador entrenador = new Entrenador(nombreEntrenador, creditos);
		return entrenador;
	}

	public void agregarAlGimnasio(Entrenador entrenador) throws NombreUtilizadoException {
		this.gimnasio.putEntrenador(entrenador);
	}

	public ArenaLogica crearArenaBase(String tipoArena) {
		ArenaLogica base = null;
		switch (tipoArena) {
		case "Bosque":
			base = new ArenaBosque();
			break;
		case "Desierto":
			base = new ArenaDesierto();
			break;
		case "Selva":
			base = new ArenaSelva();
			break;
		}
		return base;
	}

	public ArenaLogica crearArenaDecorada(String dificultadArena, ArenaLogica base) {
		ArenaLogica decorada = null;
		switch (dificultadArena) {
		case "Facil":
			decorada = new ArenaFacil(base);
			break;
		case "Media":
			decorada = new ArenaMedio(base);
			break;
		case "Dificil":
			decorada = new ArenaDificil(base);
			break;
		}
		return decorada;
	}

	public void agregarArena(ArenaLogica decorada) {
		ArenaFisica a = new ArenaFisica(this.numArenas++, decorada);
		this.sistemaPelea.getArenas().put(a.getId(), a);

	}

	public void comprarArma(String tipoArma, Entrenador entrenador, String nombrePokemon)
			throws PokemonNoPuedeUsarArmaE, PokemonNoExisteException, CompraImposibleException,
			TipoDesconocidoException {
		this.gimnasio.getTienda().comprarArma(tipoArma, entrenador, nombrePokemon);
	}

	public Pokemon crearPokemon(String tipoPokemon, String nombrePokemon) throws TipoDesconocidoException {
		return this.pokFac.getPokemon(tipoPokemon, nombrePokemon);
	}

	public void iniciarTorneo() throws EntrenadorNoExisteException, EntrenadorSinPokemonesException {
		List<Thread> duelos = this.sistemaPelea.getDuelos();
		for (Thread hilo : duelos) {
			hilo.start();
		}
		for (Thread hilo : duelos) {
			try {
				hilo.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void agregarDuelo(String entrenador1, String entrenador2)
			throws EntrenadorNoExisteException, EntrenadorSinPokemonesException, ArenaOcupadaException {
		Entrenador e1 = this.gimnasio.getEntrenador(entrenador1);
		Entrenador e2 = this.gimnasio.getEntrenador(entrenador2);
		Random random = new Random();
		int arenaAleatoria = random.nextInt(this.numArenas);

		System.out.println("Num arena: " + arenaAleatoria);

		ArenaFisica arena = this.sistemaPelea.getArenas().get(arenaAleatoria);
		if (arena == null) {
			throw new IllegalStateException("No se encontró una arena con ID " + arenaAleatoria);
		}

		Duelo duelo = new Duelo(e1, e2, arena);

		this.sistemaPelea.addDuelo(duelo);

	}
}
