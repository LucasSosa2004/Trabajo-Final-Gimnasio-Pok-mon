package modelo;

import java.util.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import entrenador.Entrenador;
import excepciones.EntrenadorSinPokemonesException;
import pokemones.Pokemon;

/**
 * El duelo recibe dos entrenadores, opcionalmente cada uno lanza un hechizo,
 * luego selecciona automaticamente sus primeros 3 pokemons y resuelve
 * el duelo hasta que uno se queda sin equipos activos.
 */
public class Duelo {
    private Entrenador e1, e2, ganador;
    private int clave;
    private boolean dueloTerminado; // Puede ser util cuando se implemente la concurrencia en torneos

    private static final int PREMIO_GANADOR = 500;

    /**
     * Invariante de clase:
     * - Los entrenadores no pueden ser nulos.
     * - El numero de arena (clave) debe ser mayor o igual a 0.
     */
    private void verificarInvariante() {
        assert e1 != null : "El entrenador 1 no puede ser nulo";
        assert e2 != null : "El entrenador 2 no puede ser nulo";
        assert clave >= 0 : "El numero de arena debe ser mayor o igual a 0";
    }

    /**
     * Constructor que inicializa un duelo entre dos entrenadores.
     * 
     * Pre:
     * - Ambos entrenadores deben tener al menos un Pokemon activo.
     * 
     * Postcondicion:
     * - El duelo se inicializa con los entrenadores, el numero de arena y el estado inicial.
     * 
     * @param e1 Entrenador 1
     * @param e2 Entrenador 2
     * @param numArena Numero de arena donde se lleva a cabo el duelo
     * @throws EntrenadorSinPokemonesException Si alguno de los entrenadores no tiene pokemones activos
     */
    public Duelo(Entrenador e1, Entrenador e2, int numArena) throws EntrenadorSinPokemonesException {
        if (e1.getEquipoActivo().isEmpty())
            throw new EntrenadorSinPokemonesException(e1.getNombre());
        if (e2.getEquipoActivo().isEmpty())
            throw new EntrenadorSinPokemonesException(e2.getNombre());
        this.e1 = e1;
        this.e2 = e2;
        this.ganador = null;
        this.clave = numArena;
        this.dueloTerminado = false;
        verificarInvariante();
    }

    public int getClave() {
        return clave;
    }

    public boolean isDueloTerminado() {
        return dueloTerminado;
    }

    public Entrenador getGanador() {
        return ganador;
    }

    public Entrenador getEntrenador1() {
        return e1;
    }

    public Entrenador getEntrenador2() {
        return e2;
    }

    /**
     * Inicia el duelo entre los dos entrenadores.
     * 
     * <b><br>Pre:<br></b>
     * - Las colas de los equipos activos de ambos entrenadores no deben estar vacias.
     * - Los hechizos se lanzan una sola vez por combate y siempre al primer Pokemon invocado por el rival.
     * 
     * <b><br>Post:<br></b>
     * - El duelo se resuelve hasta que uno de los entrenadores se queda sin Pokemones activos.
     * - El ganador recibe el premio en creditos.
     */
    public void iniciarDuelo() {
        assert !e1.getEquipoActivo().isEmpty() : "El equipo activo del entrenador 1 no puede estar vacio";
        assert !e2.getEquipoActivo().isEmpty() : "El equipo activo del entrenador 2 no puede estar vacio";

        boolean turno = true;

        Pokemon p1 = e1.proximoPokemon();
        Pokemon p2 = e2.proximoPokemon();

        // Los hechizos se lanzan al primer Pokemon invocado por el rival
        e1.hechizar(p2);
        e2.hechizar(p1);

        // Resolucion del duelo
        while (p1 != null && p2 != null) {
            if (turno) {
                if (p1.getVitalidad() > 0 && p2.getVitalidad() > 0) {
                    p1.atacar(p2);
                }
            } else {
                if (p2.getVitalidad() > 0 && p1.getVitalidad() > 0) {
                    p2.atacar(p1);
                }
            }
            turno = !turno;

            if (p1 != null && p1.getVitalidad() <= 0) {
                p2.recibeExp();
                p1 = e1.proximoPokemon(); // Devuelve null si no hay mas en la cola
            }
            if (p2 != null && p2.getVitalidad() <= 0) {
                p1.recibeExp();
                p2 = e2.proximoPokemon();
            }
        }

        // Determinacion del ganador
        if (p1 == null) { // Cola 1 vacia
            this.ganador = e2;
        } else {
            this.ganador = e1;
        }
        this.ganador.addCreditos(PREMIO_GANADOR);
        this.dueloTerminado = true;

        assert this.ganador != null : "El ganador no puede ser nulo";
        assert this.dueloTerminado : "El duelo debe estar marcado como terminado";
    }

    @Override
    public String toString() {
        return "Duelo [e1=" + e1 + ", e2=" + e2 + ", ganador=" + ganador + ", clave=" + clave + ", dueloTerminado="
                + dueloTerminado + "]";
    }
}