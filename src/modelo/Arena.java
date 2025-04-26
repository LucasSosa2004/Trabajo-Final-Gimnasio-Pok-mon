package modelo;

import java.util.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * La Arena recibe dos entrenadores, opcionalmente cada uno lanza un hechizo,
 * luego selecciona automaticamente sus primeros 3 pokemons y resuelve
 * el duelo hasta que uno se queda sin equipos activos.
 */
public class Arena {
    private Entrenador e1, e2;

    
    private static final int PREMIO_GANADOR = 500;

    public Arena(Entrenador e1, Entrenador e2) throws EntrenadorSinPokemonesException {
        if (e1.getPokemones().isEmpty())
            throw new EntrenadorSinPokemonesException(e1);
        if (e2.getPokemones().isEmpty())
            throw new EntrenadorSinPokemonesException(e2);
        this.e1 = e1;
        this.e2 = e2;
    }


    /** Pre: las colas no tienen que estar vacias 
     * return: entrenador ganador del duelo;
     * */
    public Entrenador iniciarDuelo(){
    	 boolean turno=true,vacias=true;
        // 1) Lanzan sus hechizos
    	
        //e1.lanzarHechizo(Niebla,e2);
        //e2.lanzarHechizo(Tormenta, e1);
        
        Entrenador ganador = null;
        
        Pokemon p1 = e1.proximoPokemon();
        Pokemon p2 = e2.proximoPokemon();

        Queue<Pokemon> equipo1 = e1.getEquipoActivo();
        Queue<Pokemon> equipo2 = e2.getEquipoActivo();
        
        //solo van a ser null si no quedan mas en la cola        
        while(p1!=null && p2!=null) {
			if (turno) {
			    if (p1.getVitalidad() > 0 && p2.getVitalidad() > 0)
			        p1.atacar(p2);
			} 
			else {
			    if (p2.getVitalidad() > 0 && p1.getVitalidad() > 0)
			        p2.atacar(p1);
			}
			turno = !turno;

    		if(p1 != null && p1.getVitalidad() <= 0) {
				p2.recibeExp();
    			p1 = e1.proximoPokemon(); //devuelve null cuando no hay mas en la cola
            }
    		if(p2 != null && p2.getVitalidad() > 0) {
				p1.recibeExp();
    			p2 = e2.proximoPokemon();
            }
      	   //System.out.println(equipo1);
      	   //System.out.println(equipo2);
        }

        if (p1 == null) //cola 1 vacia
        	ganador = e2;
        else 
        	ganador = e1;
        return ganador;
    }


    public boolean entrenadorPerdio(Entrenador e, Pokemon p) {
    	return e.getEquipoActivo().isEmpty() && p == null;
    }
/*
    public Pokemon chequearVitalidad(Pokemon atacado, Pokemon atacante, Entrenador e) {
    	if(atacado.getVitalidad()<0) {
			atacante.recibeExp();
			e.getEquipoActivo().remove(atacado);
			return e.proximoPokemon();
        }
    	else 
    		return atacado;
    }
    
*/

}