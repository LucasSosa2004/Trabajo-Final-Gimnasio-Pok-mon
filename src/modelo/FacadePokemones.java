package modelo;

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
        this.numArenas=0;
        this.etapa = new EtapaTorneo();
    }

    public static FacadePokemones getInstancia() {
        if (instancia==null) {
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

	public void comprarPokemon(Entrenador entrenador, String tipoPokemon, String nombrePokemon) throws CompraImposibleException, TipoDesconocidoException {
		this.gimnasio.getTienda().compraPokemon(entrenador, tipoPokemon, nombrePokemon);
		
	}


	public Entrenador crearEntrenador(String nombreEntrenador, int creditos){
		Entrenador entrenador= new Entrenador(nombreEntrenador,creditos);
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

	public ArenaLogica crearArenaDecorada(String dificultadArena,ArenaLogica base) {
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
		ArenaFisica arena = new ArenaFisica(this.numArenas++, decorada);
		this.sistemaPelea.getArenas().add(arena);
	}

	public void liberarTodasLasArenas() {
		for (ArenaFisica arena : this.sistemaPelea.getArenas()) {
			if (arena.estaOcupada()) {
				this.sistemaPelea.liberarArena(arena);
			}
		}
	}

	public void comprarArma(String tipoArma, Entrenador entrenador, String nombrePokemon) throws PokemonNoPuedeUsarArmaE, PokemonNoExisteException, CompraImposibleException, TipoDesconocidoException {
		this.gimnasio.getTienda().comprarArma(tipoArma, entrenador, nombrePokemon);
	}

	public Pokemon crearPokemon(String tipoPokemon, String nombrePokemon) throws TipoDesconocidoException {
		return this.pokFac.getPokemon(tipoPokemon, nombrePokemon);
	}

	public void inscribirTorneo(String nombre) throws EntrenadorNoExisteException {
		this.gimnasio.inscribirAlTorneo(nombre);
	}

	public void iniciarTorneo() throws EntrenadorNoExisteException, EntrenadorSinPokemonesException {
		try {
			this.sistemaPelea.iniciarTorneo(this.gimnasio, this.etapa);
		} finally {
			// Aseguramos que todas las arenas queden liberadas al terminar el torneo
			liberarTodasLasArenas();
		}
	}
}

