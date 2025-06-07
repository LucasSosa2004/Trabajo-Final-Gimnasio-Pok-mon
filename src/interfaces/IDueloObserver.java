package interfaces;

/**
 * Interfaz para observar eventos durante un duelo Pokemon.
 */
public interface IDueloObserver {
    /**
     * Notifica el inicio de un duelo.
     * 
     * @param entrenador1 Nombre del primer entrenador
     * @param entrenador2 Nombre del segundo entrenador
     * @param arena Nombre de la arena donde se realiza el duelo
     */
    void notificarInicioDuelo(String entrenador1, String entrenador2, String arena);
    
    /**
     * Notifica un ataque durante el duelo.
     * 
     * @param atacante Nombre del Pokemon atacante
     * @param defensor Nombre del Pokemon que recibe el ataque
     * @param danio Cantidad de danio realizado
     */
    void notificarAtaque(String atacante, String defensor, double danio);
    
    /**
     * Notifica cuando un Pokemon es derrotado.
     * 
     * @param pokemon Nombre del Pokemon derrotado
     * @param entrenador Nombre del entrenador del Pokemon
     */
    void notificarPokemonDerrotado(String pokemon, String entrenador);
    
    /**
     * Notifica el fin del duelo.
     * 
     * @param ganador Nombre del entrenador ganador
     * @param perdedor Nombre del entrenador perdedor
     */
    void notificarFinDuelo(String ganador, String perdedor);
} 