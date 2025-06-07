package interfaces;

/**
 * Interfaz que define los métodos que debe implementar un observador de duelos.
 * Sigue el patrón Observer para recibir notificaciones de eventos durante un duelo.
 */
public interface IDueloObserver {
    
    /**
     * Notifica el inicio de un duelo.
     * @param entrenador1 Nombre del primer entrenador
     * @param entrenador2 Nombre del segundo entrenador
     * @param arena Nombre de la arena donde se realiza el duelo
     */
    void notificarInicioDuelo(String entrenador1, String entrenador2, String arena);
    
    /**
     * Notifica un ataque durante el duelo.
     * @param atacante Nombre del Pokémon atacante
     * @param defensor Nombre del Pokémon que recibe el ataque
     * @param daño Cantidad de daño realizado
     */
    void notificarAtaque(String atacante, String defensor, double daño);
    
    /**
     * Notifica cuando un Pokémon es derrotado.
     * @param pokemon Nombre del Pokémon derrotado
     * @param entrenador Nombre del entrenador del Pokémon
     */
    void notificarPokemonDerrotado(String pokemon, String entrenador);
    
    /**
     * Notifica el fin del duelo.
     * @param ganador Nombre del entrenador ganador
     * @param perdedor Nombre del entrenador perdedor
     */
    void notificarFinDuelo(String ganador, String perdedor);
} 