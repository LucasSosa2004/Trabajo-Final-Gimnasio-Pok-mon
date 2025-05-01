package entrenador;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import armas.Arma;
import excepciones.NombreUtilizadoException;
import interfaces.IClasificable;
import interfaces.IHechizable;
import interfaces.IHechizo;
import modelo.Tienda;
import pokemones.Pokemon;
import pokemones.PokemonPiedra;


/**
 * Cada Entrenador tiene un nombre, crÃ©ditos, una lista de pokÃ©mons
 * y un "equipo activo" de hasta 3 para duelos.
 */
public class Entrenador implements Cloneable, IClasificable {
    private String nombre;
    private double creditos = 0;
    private HashMap<String,Pokemon> pokemones = new HashMap<>();
    private Queue<Pokemon> equipoActivo = new LinkedList<>();
    private IHechizo hechizo;

    public Entrenador(String nombre, int creditosIniciales) {
        this.nombre = nombre;
        this.creditos = creditosIniciales;
        this.hechizo=null;
    }
    
    

	public void setHechizo(IHechizo hechizo) {
		this.hechizo = hechizo;
	}




	// â€”â€”â€” Getters / Setters â€”â€”â€”
    public String getNombre() {
    	return this.nombre; 
    }
    public double getCreditos() {
    	return this.creditos;
    }
    
    
    public Queue<Pokemon> getEquipoActivo(){
    	return this.equipoActivo;
    }
    public HashMap<String,Pokemon> getPokemones(){
    	return this.pokemones;
    }
    public void addCreditos(double cant) {
    	this.creditos += cant;
    }
    public void subCreditos(double cant) {
    	this.creditos-=cant;
    }
   
    @Override
	public String toString() {
		return "Entrenador [nombre=" + nombre + ", creditos=" + creditos + ", pokemones=" + pokemones
				+ ", equipoActivo=" + equipoActivo + "]";
	}



   
	@Override
    public int getCategoria() { //Calcula y devuelve la â€œcategorÃ­aâ€� del entrenador, que es la suma de las categorÃ­as de todos sus PokÃ©mones.
        int suma = 0;
        for (Pokemon p : this.pokemones.values()) {
            suma += p.getCategoria();
        }
        return suma;
    }
	
	public void addPokemon(Pokemon p) throws NombreUtilizadoException {
		if (this.pokemones.containsKey(p.getNombre()))
			throw new NombreUtilizadoException(p.getNombre());
		else	
			this.pokemones.put(p.getNombre(), p);
	}
	
	
    // â€”â€”â€” SelecciÃ³n y gestiÃ³n de equipo activo â€”â€”â€”
    public void agregarPokemonEquipo(String seleccion)  {
    	if(equipoActivo.size()<3) {
	        if (seleccion == null)
	            throw new IllegalArgumentException("Debe seleccionar al menos un pokÃ©mon");

	        equipoActivo.add(buscaPokemon(seleccion)); //aÃ±ado a la cola de donde la arena sacara los pokemones para pelear
    	}
    }
    
    public void setEquipo(String p1, String p2, String p3) {
    	try {
    		agregarPokemonEquipo(p1);
    		agregarPokemonEquipo(p2);
    		agregarPokemonEquipo(p3);
    	}
    	catch(IllegalArgumentException e) {
    		throw new IllegalArgumentException();
    	}
    }
    
    public void setEquipo(String p) {
    	try {
    		agregarPokemonEquipo(p);
    	}
    	catch(IllegalArgumentException e) {
    		throw new IllegalArgumentException();
    	}
    }
    
    public void setEquipo(String p1, String p2) {
    	try {
    		agregarPokemonEquipo(p1);
    		agregarPokemonEquipo(p2);

    	}
    	catch(IllegalArgumentException e) {
    		throw new IllegalArgumentException();
    	}
    }
    
    public Pokemon buscaPokemon(String nombre) {
    	Pokemon p = null;
    	if (this.pokemones.containsKey(nombre))
    		p =this.pokemones.get(nombre);
    	else
    		throw new IllegalArgumentException("Pokemon "+ nombre +" no encontrado");
    	return p;
    }
    
    public boolean tienePokemonesActivos() { 
        return !equipoActivo.isEmpty(); 
    }
    public Pokemon proximoPokemon() {
        return equipoActivo.poll();
    }
    public void regresarAlFinal(Pokemon p) {
        equipoActivo.add(p);
    }

    // â€”â€”â€” Lanzamiento de hechizos (Double Dispatch) â€”â€”â€”
    public void hechizar(IHechizable hechizable)  {
    	if(this.hechizo != null)
    		hechizo.hechizar(hechizable);
    }

    // â€”â€”â€” ClonaciÃ³n profunda segÃºn reglas â€”â€”â€”
    @Override
    public Entrenador clone() throws CloneNotSupportedException {
        Entrenador copia = (Entrenador) super.clone();
        copia.pokemones = new HashMap<>();
        for (Pokemon p : this.pokemones.values()) {
            try {
                copia.pokemones.put(p.getNombre(),(Pokemon) p.clone());
            } catch (CloneNotSupportedException ex) {
                // Si algÃºn PokÃ©mon no es clonable, abortamos toda la clonaciÃ³n
                throw new CloneNotSupportedException("No se pudo clonar al PokÃ©mon " + p.getNombre());
            }
        }
        // clonamos tambiÃ©n el equipo activo (en base a la nueva lista)
        copia.equipoActivo = new LinkedList<>();
        for (Pokemon p : this.equipoActivo) {
        	copia.equipoActivo.add((Pokemon)p.clone());
        }
        return copia;
    }

     
    


}
