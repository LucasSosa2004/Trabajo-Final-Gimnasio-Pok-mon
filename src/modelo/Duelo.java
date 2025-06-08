package modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import entrenador.Entrenador;
import excepciones.EntrenadorSinPokemonesException;
import pokemones.Pokemon;

/**
 * El duelo recibe dos entrenadores, opcionalmente cada uno lanza un hechizo,
 * luego selecciona autom�ticamente sus primeros Pok�mons y resuelve
 * el duelo hasta que uno se queda sin equipo activo.
 */
public class Duelo implements Runnable, Serializable {
    private static final long serialVersionUID = 1L;

    private Entrenador e1, e2, ganador;
    private int clave;
    private boolean dueloTerminado;
    private ArenaFisica arena;
    private Queue<Pokemon> colaAuxE1 = new LinkedList<>();
    private Queue<Pokemon> colaAuxE2 = new LinkedList<>();
    /**
     * Crea un nuevo Duelo en la arena dada.
     *
     * @param e1    Entrenador 1 (debe tener al menos 1 Pok�mon activo)
     * @param e2    Entrenador 2 (debe tener al menos 1 Pok�mon activo)
     * @param arena Arena f�sica donde se librar� este duelo
     * @throws EntrenadorSinPokemonesException si alguno no tiene Pok�mons activos
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

    /** Nuevo m�todo para obtener la arena asociada */
    public ArenaFisica getArena() {
        return arena;
    }

    /**
     * Resuelve el duelo de forma s�ncrona (se invoca dentro de run()).
     * Al terminar, otorga el premio y recarga los Pok�mons del ganador.
     */
    public void iniciarDuelo() {
        assert !e1.getEquipoActivo().isEmpty() : "El equipo activo del entrenador 1 no puede estar vac�o";
        assert !e2.getEquipoActivo().isEmpty() : "El equipo activo del entrenador 2 no puede estar vac�o";

        boolean turno = true;

        Pokemon p1 = e1.proximoPokemon();
        Pokemon p2 = e2.proximoPokemon();
        Pokemon p;
        colaAuxE1.add(p1);
        colaAuxE2.add(p2);
        
        // Hechizos iniciales: cada entrenador lanza sobre el primer Pok�mon del rival
        e1.hechizar(p2);
        e2.hechizar(p1);

        // Listas de participantes para recargar luego
        java.util.List<Pokemon> participantesE1 = new java.util.ArrayList<>();
        java.util.List<Pokemon> participantesE2 = new java.util.ArrayList<>();
        if (p1 != null) participantesE1.add(p1);
        if (p2 != null) participantesE2.add(p2);

        // Bucle hasta que uno se quede sin Pok�mon
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
                colaAuxE1.add(p1);
                if (p1 != null) participantesE1.add(p1);
            }
            if (p2 != null && p2.getVitalidad() <= 0) {
                p1.recibeExp();
                p2 = e2.proximoPokemon();
                colaAuxE2.add(p2);
                if (p2 != null) participantesE2.add(p2);
            }
        }

        // Determinar ganador
        if (p1 == null) {
            this.ganador = e2;
            while(!colaAuxE2.isEmpty()) {
            	p = colaAuxE2.poll();
            	p.recargar();
            	ganador.getEquipoActivo().add(p);
            	System.out.println("E2: " + p);
            }
        } else {
            this.ganador = e1;
            while(!colaAuxE1.isEmpty()) {
            	p = colaAuxE1.poll();
            	p.recargar();
            	ganador.getEquipoActivo().add(p);
            	System.out.println("E1: " + p);
            }
        }

        // Otorgar premio seg�n la arena decorada
        this.ganador.addCreditos(arena.getPremio());
        this.dueloTerminado = true;

        // Recargar la vitalidad de los Pok�mons del ganador
        
        
        /*for (Pokemon p : participantesGanador) {
            if (p != null) p.recargar();
        }*/
        
        assert this.ganador != null : "El ganador no puede ser nulo";
        assert this.dueloTerminado : "El duelo debe estar marcado como terminado";
    }

    @Override
    public void run() {
        try {
            iniciarDuelo();
        } finally {
            // Aseguramos que la arena siempre se libere, incluso si hay una excepción
            SistemaPelea.getInstancia().liberarArena(this.arena);
            this.dueloTerminado = true;
        }
    }

    @Override
    public String toString() {
        return "Duelo [e1=" + e1.getNombre() + ", e2=" + e2.getNombre() +
               ", ganador=" + (ganador != null ? ganador.getNombre() : "N/A") +
               ", clave=" + clave + ", dueloTerminado=" + dueloTerminado + "]";
    }
}
