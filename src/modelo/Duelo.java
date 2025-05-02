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
    private Entrenador e1, e2,ganador;
    private int clave;
    private boolean dueloTerminado; //Puede llegar a ser util cuando se implemente la concurrencia a la hora del torneo
    

    
    private static final int PREMIO_GANADOR = 500;

    public Duelo(Entrenador e1, Entrenador e2, int numArena) throws EntrenadorSinPokemonesException {
        if (e1.getEquipoActivo().isEmpty())
            throw new EntrenadorSinPokemonesException(e1.getNombre());
        if (e2.getEquipoActivo().isEmpty())
            throw new EntrenadorSinPokemonesException(e2.getNombre());
        this.e1 = e1;
        this.e2 = e2;
        this.ganador=null;
        this.clave=numArena;
        this.dueloTerminado=false;
        
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


	/** Pre: las colas no tienen que estar vacias 
     * return: entrenador ganador del duelo;
     * */
    public void iniciarDuelo(){
    	 boolean turno=true;

          
        Pokemon p1 = e1.proximoPokemon();
        Pokemon p2 = e2.proximoPokemon();

        
        //en nuestro modelo el hechizo se puede lanzar una sola vez por combate y siempre se lanza al primer pokemon invocado por el rival
        e1.hechizar(p2);
        e2.hechizar(p1);

        //solo van a ser null si no quedan mas en la cola        
        while(p1!=null && p2!=null) {
        	//System.out.println("TURNO: " + turno);
			if (turno) {
			    if (p1.getVitalidad() > 0 && p2.getVitalidad() > 0) {
			        p1.atacar(p2);
			    }
			} 
			else {
			    if (p2.getVitalidad() > 0 && p1.getVitalidad() > 0) {
			        p2.atacar(p1);
			    }
			}
			turno = !turno;

    		if(p1 != null && p1.getVitalidad() <= 0) {
				p2.recibeExp();
    			p1 = e1.proximoPokemon(); //devuelve null cuando no hay mas en la cola
            }
    		if(p2 != null && p2.getVitalidad() <= 0) {
				p1.recibeExp();
    			p2 = e2.proximoPokemon();
            }

        }

        if (p1 == null) { //cola 1 vacia
        	this.ganador = e2;
        }
        else {
        	this.ganador = e1;
        }
    	this.ganador.addCreditos(PREMIO_GANADOR);
    	this.dueloTerminado=true;
    }


	@Override
	public String toString() {
		return "Duelo [e1=" + e1 + ", e2=" + e2 + ", ganador=" + ganador + ", clave=" + clave + ", dueloTerminado="
				+ dueloTerminado + "]";
	}


    


}