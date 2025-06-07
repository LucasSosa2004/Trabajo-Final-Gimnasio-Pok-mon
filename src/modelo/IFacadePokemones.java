package modelo;

import armas.ArmaFactory;
import entrenador.Entrenador;
import excepciones.*;
import pokemones.Pokemon;
import pokemones.PokemonFactory;

/**
 * Interfaz que define el contrato para la fachada del sistema de Pokemones.
 * Proporciona una interfaz unificada para todas las operaciones del sistema.
 */
public interface IFacadePokemones {
    
    /**
     * Obtiene la instancia del gimnasio
     */
    Gimnasio getGimnasio();
    
    /**
     * Obtiene la instancia del sistema de pelea
     */
    SistemaPelea getSistemaPelea();
    
    /**
     * Obtiene la fábrica de pokemones
     */
    PokemonFactory getPokFact();
    
    /**
     * Obtiene la fábrica de armas
     */
    ArmaFactory getArmFact();
    
    /**
     * Crea un nuevo entrenador
     * @throws NombreUtilizadoException si el nombre ya existe
     */
    void crearEntrenador(String nombre, int creditos) throws NombreUtilizadoException;
    
    /**
     * Crea un nuevo pokemon para un entrenador
     * @throws TipoDesconocidoException si el tipo no es válido
     * @throws NombreUtilizadoException si el nombre ya existe
     */
    void crearPokemon(Entrenador entrenador, String tipo, String nombre) throws TipoDesconocidoException, NombreUtilizadoException;
    
    /**
     * Compra un pokemon para un entrenador
     * @throws CompraImposibleException si no hay suficientes créditos
     * @throws TipoDesconocidoException si el tipo no es válido
     */
    void comprarPokemon(Entrenador entrenador, String tipo, String nombre) throws CompraImposibleException, TipoDesconocidoException;
    
    /**
     * Compra un arma para un pokemon
     * @throws CompraImposibleException si no hay suficientes créditos
     * @throws TipoDesconocidoException si el tipo no es válido
     * @throws PokemonNoPuedeUsarArmaE si el pokemon no puede usar armas
     */
    void comprarArma(Entrenador entrenador, String nombrePokemon, String tipoArma) throws CompraImposibleException, TipoDesconocidoException, PokemonNoPuedeUsarArmaE, PokemonNoExisteException;
    
    /**
     * Recarga un pokemon
     */
    void recargarPokemon(Entrenador entrenador, String nombrePokemon) throws PokemonNoExisteException;
    
    /**
     * Agrega un pokemon al equipo activo de un entrenador
     * @throws EquipoLlenoException si el equipo ya tiene 3 pokemones
     */
    void agregarPokemonAEquipo(Entrenador entrenador, String nombrePokemon) throws EquipoLlenoException;
    
    /**
     * Crea un nuevo duelo entre dos entrenadores
     * @throws EntrenadorSinPokemonesException si algún entrenador no tiene pokemones
     * @throws ArenaOcupadaException si no hay arenas disponibles
     */
    void crearDuelo(String nombreEntrenador1, String nombreEntrenador2) throws EntrenadorNoExisteException, EntrenadorSinPokemonesException, ArenaOcupadaException;
} 