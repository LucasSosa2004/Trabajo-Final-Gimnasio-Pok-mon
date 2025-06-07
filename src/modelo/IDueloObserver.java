package modelo;

import entrenador.Entrenador;
import pokemones.Pokemon;

/**
 * Interfaz para observadores de duelos.
 * Los observadores serán notificados de los eventos importantes durante un duelo.
 */
public interface IDueloObserver {
    /**
     * Notifica el inicio de un duelo
     */
    void onDueloIniciado(Duelo duelo);
    
    /**
     * Notifica cuando un pokemon ataca a otro
     */
    void onAtaque(Pokemon atacante, Pokemon defensor, double danio);
    
    /**
     * Notifica cuando un pokemon es derrotado
     */
    void onPokemonDerrotado(Pokemon pokemon);
    
    /**
     * Notifica cuando un pokemon es reemplazado
     */
    void onPokemonReemplazado(Pokemon pokemonAnterior, Pokemon pokemonNuevo);
    
    /**
     * Notifica cuando un entrenador gana el duelo
     */
    void onDueloFinalizado(Entrenador ganador, int creditos);
} 