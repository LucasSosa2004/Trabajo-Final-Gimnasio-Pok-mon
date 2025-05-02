package modelo;

import java.util.HashMap;

import excepciones.ArenaOcupadaException;

public class SistemaPelea {
    /** Lista de arenas disponibles en el gimnasio */
	private static SistemaPelea instancia;
    private HashMap<Integer,Duelo> duelos = new HashMap<>();
    
    
   
    
    public static SistemaPelea getInstancia() {
    	if(instancia==null) {
    		instancia= new SistemaPelea();
    	}
    	return instancia;
    }
    
    /**
     * Devuelve el hashMap de todos los duelos disponibles en el gimnasio.
     * 
     * @return HashMap<Duelo,Integer> con los duelos del gimnasio
     */
    public HashMap<Integer,Duelo> getDuelos() {
        return this.duelos;
    }
    
    public Duelo getDuelo(int numArena) {
    	return this.duelos.get(numArena);
    }
    
    public void removeDuelo(int numArena) {
    	this.duelos.remove(numArena);
    }
     
    /**
     * Aniade un duelo a la lista de duelos del sistema de peleas, si el duelo se intenta iniciar en una "Arena" ya utilizada este arroja una excepcion (mas adelante se trabajara con threads y no exception).
     * 
     * Pre: el duelo no puede ser null
     * 
     * @param a El duelo a aniadir
     */

    public void addDuelo(Duelo duelo) throws ArenaOcupadaException {
    	if(this.duelos.containsKey(duelo.getClave()))
    		throw new ArenaOcupadaException(duelo.getClave());
    	else
    		this.duelos.put(duelo.getClave(),duelo);      
    }
    
    
    public void iniciarCombate(int numArena) {
    	Duelo duelo=this.getDuelo(numArena);
    	
    	duelo.iniciarDuelo();
    	System.out.println("El entrenador "+duelo.getGanador().getNombre()+" gano el duelo.");
	
    	this.removeDuelo(numArena);
    }

	@Override
	public String toString() {
		return "SistemaPelea [duelos=" + duelos + "]";
	}
    
    
}
