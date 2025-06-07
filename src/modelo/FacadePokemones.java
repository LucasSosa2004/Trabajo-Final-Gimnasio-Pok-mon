package modelo;

import armas.ArmaFactory;
import entrenador.Entrenador;
import excepciones.*;
import pokemones.Pokemon;
import pokemones.PokemonFactory;

/**
 * Implementación del patrón Facade para el sistema de Pokemones.
 * Proporciona una interfaz unificada para todas las operaciones del sistema.
 */
public class FacadePokemones implements IFacadePokemones {
    private Gimnasio gimnasio;
    private SistemaPelea sistemaPelea;
    private static FacadePokemones instancia;
    private PokemonFactory pokFac;
    private ArmaFactory armFac;

    private FacadePokemones() {
        this.gimnasio = Gimnasio.getInstancia();
        this.sistemaPelea = SistemaPelea.getInstancia();
        this.armFac = new ArmaFactory();
        this.pokFac = new PokemonFactory();
    }

    public static FacadePokemones getInstancia() {
        if (instancia == null) {
            instancia = new FacadePokemones();
        }
        return instancia;
    }

    @Override
    public Gimnasio getGimnasio() {
        return gimnasio;
    }

    @Override
    public SistemaPelea getSistemaPelea() {
        return sistemaPelea;
    }

    @Override
    public PokemonFactory getPokFact() {
        return this.pokFac;
    }

    @Override
    public ArmaFactory getArmFact() {
        return this.armFac;
    }

    @Override
    public void crearEntrenador(String nombre, int creditos) throws NombreUtilizadoException {
        Entrenador entrenador = new Entrenador(nombre, creditos);
        gimnasio.putEntrenador(entrenador);
    }

    @Override
    public void crearPokemon(Entrenador entrenador, String tipo, String nombre) throws TipoDesconocidoException, NombreUtilizadoException {
        Pokemon pokemon = pokFac.getPokemon(tipo, nombre);
        entrenador.putPokemon(pokemon);
    }

    @Override
    public void comprarPokemon(Entrenador entrenador, String tipo, String nombre) throws CompraImposibleException, TipoDesconocidoException {
        gimnasio.getTienda().compraPokemon(entrenador, tipo, nombre);
    }

    @Override
    public void comprarArma(Entrenador entrenador, String nombrePokemon, String tipoArma) throws CompraImposibleException, TipoDesconocidoException, PokemonNoPuedeUsarArmaE, PokemonNoExisteException {
        gimnasio.getTienda().compraArma(tipoArma, entrenador, nombrePokemon);
    }

    @Override
    public void recargarPokemon(Entrenador entrenador, String nombrePokemon) throws PokemonNoExisteException {
        entrenador.buscaPokemon(nombrePokemon).recargar();
    }

    @Override
    public void agregarPokemonAEquipo(Entrenador entrenador, String nombrePokemon) throws EquipoLlenoException {
        entrenador.agregarPokemonEquipo(nombrePokemon);
    }

    @Override
    public void crearDuelo(String nombreEntrenador1, String nombreEntrenador2) throws EntrenadorNoExisteException, EntrenadorSinPokemonesException, ArenaOcupadaException {
        Entrenador e1 = gimnasio.getEntrenador(nombreEntrenador1);
        Entrenador e2 = gimnasio.getEntrenador(nombreEntrenador2);
        
        if (!e1.tienePokemonesActivos() || !e2.tienePokemonesActivos()) {
            throw new EntrenadorSinPokemonesException(
                !e1.tienePokemonesActivos() ? nombreEntrenador1 : nombreEntrenador2);
        }
        
        Duelo duelo = new Duelo(e1, e2);
        sistemaPelea.addDuelo(duelo);
    }
}

