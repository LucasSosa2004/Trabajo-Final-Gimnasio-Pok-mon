package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entrenador.Entrenador;
import excepciones.EntrenadorSinPokemonesException;
import interfaces.IDueloObserver;
import pokemones.Pokemon;

/**
 * El duelo recibe dos entrenadores, opcionalmente cada uno lanza un hechizo,
 * luego selecciona automáticamente sus primeros Pokémons y resuelve
 * el duelo hasta que uno se queda sin equipo activo.
 * Implementa el patrón Observable para notificar eventos durante el enfrentamiento
 * y Runnable para permitir la ejecución concurrente de múltiples duelos.
 */
public class Duelo implements Runnable, Serializable {
    private static final long serialVersionUID = 1L;
    
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private ArenaFisica arena;
    private Entrenador ganador;
    private boolean dueloTerminado;
    private transient List<IDueloObserver> observers = new ArrayList<>();
    
    /**
     * Crea un nuevo Duelo en la arena dada.
     *
     * @param e1 Entrenador 1 (debe tener al menos 1 Pokémon activo)
     * @param e2 Entrenador 2 (debe tener al menos 1 Pokémon activo)
     * @param arena Arena física donde se librará este duelo
     * @throws EntrenadorSinPokemonesException si alguno no tiene Pokémons activos
     */
    public Duelo(Entrenador e1, Entrenador e2, ArenaFisica arena) throws EntrenadorSinPokemonesException {
        if (!e1.tienePokemonesActivos() || !e2.tienePokemonesActivos()) {
            throw new EntrenadorSinPokemonesException(
                !e1.tienePokemonesActivos() ? e1.getNombre() : e2.getNombre()
            );
        }
        this.entrenador1 = e1;
        this.entrenador2 = e2;
        this.arena = arena;
        this.dueloTerminado = false;
    }
    
    public void addObserver(IDueloObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }
    
    public void removeObserver(IDueloObserver observer) {
        if (observers != null) {
            observers.remove(observer);
        }
    }
    
    private void notifyInicioDuelo() {
        if (observers != null) {
            for (IDueloObserver observer : observers) {
                observer.notificarInicioDuelo(
                    entrenador1.getNombre(),
                    entrenador2.getNombre(),
                    arena.getNombre()
                );
            }
        }
    }
    
    private void notifyAtaque(Pokemon atacante, Pokemon defensor, double daño) {
        if (observers != null) {
            for (IDueloObserver observer : observers) {
                observer.notificarAtaque(
                    atacante.getNombre(),
                    defensor.getNombre(),
                    daño
                );
            }
        }
    }
    
    private void notifyPokemonDerrotado(Pokemon pokemon, Entrenador entrenador) {
        if (observers != null) {
            for (IDueloObserver observer : observers) {
                observer.notificarPokemonDerrotado(
                    pokemon.getNombre(),
                    entrenador.getNombre()
                );
            }
        }
    }
    
    private void notifyFinDuelo() {
        if (observers != null) {
            for (IDueloObserver observer : observers) {
                observer.notificarFinDuelo(
                    ganador.getNombre(),
                    (ganador == entrenador1 ? entrenador2 : entrenador1).getNombre()
                );
            }
        }
    }
    
    /**
     * Resuelve el duelo de forma síncrona.
     * Al terminar, otorga el premio y notifica a los observadores.
     */
    public void iniciarDuelo() {
        try {
            notifyInicioDuelo();
            
            // Cada entrenador lanza su hechizo sobre el rival
            entrenador1.hechizar(entrenador2);
            entrenador2.hechizar(entrenador1);
            
            Pokemon pokemon1 = entrenador1.proximoPokemon();
            Pokemon pokemon2 = entrenador2.proximoPokemon();
            
            while (pokemon1 != null && pokemon2 != null) {
                // Ataque del primer Pokémon
                double vitalidadAntes = pokemon2.getVitalidad();
                pokemon1.atacar(pokemon2);
                double daño = vitalidadAntes - pokemon2.getVitalidad();
                notifyAtaque(pokemon1, pokemon2, daño);
                
                // Verificar si el segundo Pokémon fue derrotado
                if (pokemon2.getVitalidad() <= 0) {
                    notifyPokemonDerrotado(pokemon2, entrenador2);
                    pokemon1.recibeExp();
                    pokemon2 = entrenador2.proximoPokemon();
                    if (pokemon2 == null) {
                        ganador = entrenador1;
                        break;
                    }
                }
                
                // Ataque del segundo Pokémon
                vitalidadAntes = pokemon1.getVitalidad();
                pokemon2.atacar(pokemon1);
                daño = vitalidadAntes - pokemon1.getVitalidad();
                notifyAtaque(pokemon2, pokemon1, daño);
                
                // Verificar si el primer Pokémon fue derrotado
                if (pokemon1.getVitalidad() <= 0) {
                    notifyPokemonDerrotado(pokemon1, entrenador1);
                    pokemon2.recibeExp();
                    pokemon1 = entrenador1.proximoPokemon();
                    if (pokemon1 == null) {
                        ganador = entrenador2;
                        break;
                    }
                }
            }
            
            // Aplicar efectos de la arena al ganador
            if (ganador != null) {
                ganador.addCreditos(arena.getPremio());
                notifyFinDuelo();
            }
        } finally {
            dueloTerminado = true;
        }
    }
    
    @Override
    public void run() {
        try {
            iniciarDuelo();
        } finally {
            // Aseguramos que la arena se libere incluso si hay una excepción
            if (arena != null) {
                arena.liberar();
            }
        }
    }
    
    public Entrenador getEntrenador1() {
        return entrenador1;
    }
    
    public Entrenador getEntrenador2() {
        return entrenador2;
    }
    
    public ArenaFisica getArena() {
        return arena;
    }
    
    public Entrenador getGanador() {
        return ganador;
    }
    
    public boolean isDueloTerminado() {
        return dueloTerminado;
    }
    
    public int getClave() {
        return arena.getId();
    }
    
    @Override
    public String toString() {
        return "Duelo [entrenador1=" + entrenador1.getNombre() + 
               ", entrenador2=" + entrenador2.getNombre() + 
               ", arena=" + arena.getNombre() + 
               ", terminado=" + dueloTerminado + "]";
    }
}
