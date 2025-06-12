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
import interfaces.IArena;
import persistencia.GimnasioManager;
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
	private GimnasioManager manager = new GimnasioManager();

	private boolean puedeGuardarEstado;

	private FacadePokemones() {
		this.gimnasio = Gimnasio.getInstancia();
		this.sistemaPelea = SistemaPelea.getInstancia();
		this.armFac = new ArmaFactory();
		this.pokFac = new PokemonFactory();
		this.numArenas = 0;
		this.etapa = new EtapaTorneo();
		this.puedeGuardarEstado=true;
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
		assert nombreEntrenador != null && !nombreEntrenador.isEmpty() : "El nombre del entrenador no puede ser nulo o vacío";
		assert creditos >= 0 : "Los créditos iniciales no pueden ser negativos";
		Entrenador entrenador = new Entrenador(nombreEntrenador, creditos);
		return entrenador;
	}

	public void agregarAlGimnasio(Entrenador entrenador) throws NombreUtilizadoException {
		this.gimnasio.putEntrenador(entrenador);
	}

	public IArena crearArenaBase(String tipoArena) {
	    IArena base = null;
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

	public IArena crearArenaDecorada(String dificultadArena, IArena base) {
	    IArena decorada = null;
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

	public void agregarArena(IArena decorada) {
		ArenaFisica a = new ArenaFisica(this.numArenas++, decorada);
		this.sistemaPelea.getArenas().put(a.getId(), a);
	}

	public void comprarArma(String tipoArma, Entrenador entrenador, String nombrePokemon)throws PokemonNoPuedeUsarArmaE, PokemonNoExisteException, CompraImposibleException,TipoDesconocidoException {
		this.gimnasio.getTienda().comprarArma(tipoArma, entrenador, nombrePokemon);
	}

	public Pokemon crearPokemon(String tipoPokemon, String nombrePokemon) throws TipoDesconocidoException {
		return this.pokFac.getPokemon(tipoPokemon, nombrePokemon);
	}

	public void ejecutarRonda() throws EntrenadorNoExisteException, EntrenadorSinPokemonesException, ArenaOcupadaException {
	    this.puedeGuardarEstado = false;


	    this.sistemaPelea.ejecutarRonda();


	    this.puedeGuardarEstado = true;
	}
	public Duelo crearDuelo(String entrenador1, String entrenador2)
			throws EntrenadorNoExisteException, EntrenadorSinPokemonesException, ArenaOcupadaException {
		Entrenador e1 = this.gimnasio.getEntrenador(entrenador1);
		Entrenador e2 = this.gimnasio.getEntrenador(entrenador2);
		Random random = new Random();
		int arenaAleatoria = random.nextInt(this.numArenas);

		ArenaFisica arena = this.sistemaPelea.getArenas().get(arenaAleatoria);
		if (arena == null) {
			throw new IllegalStateException("No se encontró una arena con ID " + arenaAleatoria);
		}

		Duelo duelo = new Duelo(e1, e2, arena);


		
		return duelo;
	}
	
	public void agregarDuelo(Duelo duelo) throws ArenaOcupadaException {
		this.sistemaPelea.addDuelo(duelo);
	}
	public EtapaTorneo getEtapa() {
        return etapa;
    }
	public boolean puedeGuardarEstado() {
        return puedeGuardarEstado;
    }
	
	public void actualizarReferencias() {
	    this.gimnasio = Gimnasio.getInstancia();
	    this.sistemaPelea = SistemaPelea.getInstancia();
	}


	public void guardarArch() {
        this.manager.guardarEstado(this.gimnasio, this.getSistemaPelea(), this.etapa);	
	}

	public Object cargarEstado() {
		return this.manager.cargarEstado();
	}

	public  List<Duelo> getListaDuelosAux() throws EntrenadorNoExisteException, EntrenadorSinPokemonesException, ArenaOcupadaException {
		return this.sistemaPelea.getListaDuelosAux();
	}
	
	public boolean hayArenas() {
		return !this.getSistemaPelea().getArenas().isEmpty();
	}
	
	
	public void setNumArenas() {
		this.numArenas = this.getSistemaPelea().getArenas().size();
	}
}
