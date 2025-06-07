package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entrenador.Entrenador;
import excepciones.EntrenadorSinPokemonesException;
import interfaces.IDueloObserver;
import pokemones.Pokemon;

/**
 * Clase que representa un duelo entre dos entrenadores.
 * Implementa Runnable para permitir ejecucion concurrente.
 */
public class Duelo implements Runnable, Serializable {
    private static final long serialVersionUID = 1L;
    
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private ArenaFisica arena;
    private Entrenador ganador;
    private boolean dueloTerminado;
    private transient List<IDueloObserver> observers = new ArrayList<IDueloObserver>();
    
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
            observers = new ArrayList<IDueloObserver>();
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
            for (IDueloObserver obs : observers) {
                obs.notificarInicioDuelo(
                    entrenador1.getNombre(),
                    entrenador2.getNombre(),
                    arena.toString()
                );
            }
        }
    }
    
    private void notifyAtaque(String atacante, String defensor, double danio) {
        if (observers != null) {
            for (IDueloObserver obs : observers) {
                obs.notificarAtaque(atacante, defensor, danio);
            }
        }
    }
    
    private void notifyPokemonDerrotado(String pokemon, String entrenador) {
        if (observers != null) {
            for (IDueloObserver obs : observers) {
                obs.notificarPokemonDerrotado(pokemon, entrenador);
            }
        }
    }
    
    private void notifyFinDuelo() {
        if (observers != null) {
            for (IDueloObserver obs : observers) {
                obs.notificarFinDuelo(
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
        notifyInicioDuelo();
        
        // Lanzar hechizos si los tienen
        if (entrenador1.tieneHechizo()) {
            entrenador1.hechizar(entrenador2);
        }
        if (entrenador2.tieneHechizo()) {
            entrenador2.hechizar(entrenador1);
        }
        
        // Bucle principal del duelo
        while (!dueloTerminado) {
            // Turno entrenador 1
            if (entrenador1.tienePokemonesActivos()) {
                Pokemon p1 = entrenador1.getPokemonActivo();
                Pokemon p2 = entrenador2.getPokemonActivo();
                double vitalidadAntes = p2.getVitalidad();
                p1.atacar(p2);
                double danio = vitalidadAntes - p2.getVitalidad();
                notifyAtaque(p1.getNombre(), p2.getNombre(), danio);
                
                if (p2.getVitalidad() <= 0) {
                    notifyPokemonDerrotado(p2.getNombre(), entrenador2.getNombre());
                    entrenador2.pokemonDerrotado();
                    if (!entrenador2.tienePokemonesActivos()) {
                        ganador = entrenador1;
                        dueloTerminado = true;
                        break;
                    }
                }
            }
            
            // Turno entrenador 2
            if (entrenador2.tienePokemonesActivos()) {
                Pokemon p2 = entrenador2.getPokemonActivo();
                Pokemon p1 = entrenador1.getPokemonActivo();
                double vitalidadAntes = p1.getVitalidad();
                p2.atacar(p1);
                double danio = vitalidadAntes - p1.getVitalidad();
                notifyAtaque(p2.getNombre(), p1.getNombre(), danio);
                
                if (p1.getVitalidad() <= 0) {
                    notifyPokemonDerrotado(p1.getNombre(), entrenador1.getNombre());
                    entrenador1.pokemonDerrotado();
                    if (!entrenador1.tienePokemonesActivos()) {
                        ganador = entrenador2;
                        dueloTerminado = true;
                        break;
                    }
                }
            }
        }
        
        notifyFinDuelo();
        
        // Dar premio al ganador
        ganador.addCreditos(arena.getPremio());
        
        // Dar experiencia a los pokemones del ganador
        ganador.darExperienciaAEquipo();
    }
    
    @Override
    public void run() {
        iniciarDuelo();
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
