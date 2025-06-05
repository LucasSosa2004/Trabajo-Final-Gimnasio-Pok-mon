package modelo;

import entrenador.Entrenador;
import excepciones.EntrenadorSinPokemonesException;
import pokemones.Pokemon;

/**
 * El duelo recibe dos entrenadores, opcionalmente cada uno lanza un hechizo,
 * luego selecciona automáticamente sus primeros Pokémons y resuelve
 * el duelo hasta que uno se queda sin equipo activo.
 * 
 * 
 */
public class Duelo implements Runnable {
    private Entrenador e1, e2, ganador;
    private int clave;
    private boolean dueloTerminado;
    private ArenaFisica arena;

    private static final int PREMIO_GANADOR = 500; // 

    /**
     * Crea un nuevo Duelo en la arena dada.
     * 
     * @param e1    Entrenador 1 (debe tener al menos 1 Pokémon activo)
     * @param e2    Entrenador 2 (debe tener al menos 1 Pokémon activo)
     * @param arena Arena física donde se librará este duelo
     * @throws EntrenadorSinPokemonesException si alguno no tiene Pokémons activos
     */
    public Duelo(Entrenador e1, Entrenador e2, ArenaFisica arena) throws EntrenadorSinPokemonesException {
        if (e1.getEquipoActivo().isEmpty())
            throw new EntrenadorSinPokemonesException(e1.getNombre());
        if (e2.getEquipoActivo().isEmpty())
            throw new EntrenadorSinPokemonesException(e2.getNombre());

        this.e1 = e1;
        this.e2 = e2;
        this.ganador = null;
        this.clave = arena.getId();
        this.dueloTerminado = false;
        this.arena = arena;
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
     * Resuelve el duelo de forma síncrona (se invoca dentro de run()).
     * Al terminar, otorga el premio y recarga los Pokémons del ganador.
     */
    public void iniciarDuelo() {
        assert !e1.getEquipoActivo().isEmpty() : "El equipo activo del entrenador 1 no puede estar vacío";
        assert !e2.getEquipoActivo().isEmpty() : "El equipo activo del entrenador 2 no puede estar vacío";

        boolean turno = true;

        Pokemon p1 = e1.proximoPokemon();
        Pokemon p2 = e2.proximoPokemon();

        // Hechizos iniciales: cada entrenador lanza sobre el primer Pokémon del rival
        e1.hechizar(p2);
        e2.hechizar(p1);

        // Listas de participantes para recargar luego
        java.util.List<Pokemon> participantesE1 = new java.util.ArrayList<>();
        java.util.List<Pokemon> participantesE2 = new java.util.ArrayList<>();
        if (p1 != null) participantesE1.add(p1);
        if (p2 != null) participantesE2.add(p2);

        // Bucle hasta que uno se quede sin Pokémon
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
                p1 = e1.proximoPokemon();
                if (p1 != null) participantesE1.add(p1);
            }
            if (p2 != null && p2.getVitalidad() <= 0) {
                p1.recibeExp();
                p2 = e2.proximoPokemon();
                if (p2 != null) participantesE2.add(p2);
            }
        }

        // Determinar ganador
        java.util.List<Pokemon> participantesGanador;
        if (p1 == null) {
            this.ganador = e2;
            participantesGanador = participantesE2;
        } else {
            this.ganador = e1;
            participantesGanador = participantesE1;
        }

        // Otorgar premio según la arena decorada
        this.ganador.addCreditos(arena.getPremio());
        this.dueloTerminado = true;

        // Recargar la vitalidad de los Pokémons del ganador
        for (Pokemon p : participantesGanador) {
            if (p != null) p.recargar();
        }

        assert this.ganador != null : "El ganador no puede ser nulo";
        assert this.dueloTerminado : "El duelo debe estar marcado como terminado";
    }

    @Override
    public void run() {
        try {
            iniciarDuelo();
        } finally {
            arena.liberar();
        }
    }

    @Override
    public String toString() {
        return "Duelo [e1=" + e1 + ", e2=" + e2 + ", ganador=" + ganador +
               ", clave=" + clave + ", dueloTerminado=" + dueloTerminado + "]";
    }
}