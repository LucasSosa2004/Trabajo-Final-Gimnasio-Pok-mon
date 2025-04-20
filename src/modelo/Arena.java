package modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
Expandir
message.txt
4 KB
﻿
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * La Arena recibe dos entrenadores, opcionalmente cada uno lanza un hechizo,
 * luego selecciona automaticamente sus primeros 3 pokemons y resuelve
 * el duelo hasta que uno se queda sin equipos activos.
 */
public class Arena {
    private Entrenador e1, e2;

    
    
    
    private static final double PREMIO_GANADOR = 500.0;

    public Arena(Entrenador e1, Entrenador e2) throws EntrenadorSinPokemonesException {
        if (e1.getPokemones().isEmpty())
            throw new EntrenadorSinPokemonesException(e1);
        if (e2.getPokemones().isEmpty())
            throw new EntrenadorSinPokemonesException(e2);
        this.e1 = e1;
        this.e2 = e2;
    }

    /** Duelo sin hechizos */
    public Entrenador iniciarDuelo() throws EntrenadorSinPokemonesException {
        return iniciarDuelo(null, null);
    }

    /** Pre: las colas no tienen que estar vacias */
    public void iniciarDuelo(){
    	 boolean turno=true,vacias=true;
        // 1) Lanzan sus hechizos
        e1.lanzarHechizo();
        e2.lanzarHechizo();
       

        
        Pokemon p1 = e1.proximoPokemon();
        Pokemon p2 = e2.proximoPokemon();

        while(!vacias) {

    		if (turno) {
    			p1.atacar(p2);
    			turno=false;
    		}
    		else {
    			p2.atacar(p1);
    			turno=true;
    		}
    		if(p1.getVitalidad()<0) {
    			p2.addExperiencia();
    			e1.eliminarCola(p1);
    			p1 = e1.proximoPokemon();
            }
      	   if(p2.getVitalidad()<0) {
      		    p1.addExperiencia();
       			e2.eliminarCola(p2);
       			p2 = e2.proximoPokemon();        		   
     	   }
      	   if(e1.getCola().isempty()&&p1==null) {
      		   ganador=2;
      		   vacias=1;
      	   }
      	   else if(e2.getCola().isempty()&&p2==null) {
      		   ganador=1;
      		   vacias=1;
      	   }
        }
    }
    

    private void ganador(Entrenador e) {
        e.addCreditos(PREMIO_GANADOR);
        System.out.println(e.getNombre() + " gano el duelo y recibe " + PREMIO_GANADOR+ " creditos!");
    }
}