package entrenador;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import excepciones.EquipoLlenoException;
import excepciones.NombreUtilizadoException;
import excepciones.PokemonNoExisteException;
import interfaces.IClasificable;
import interfaces.IHechizable;
import interfaces.IHechizo;
import pokemones.Pokemon;


/**
 * Cada Entrenador tiene un nombre, creditos, una lista de pokemones
 * y un "equipo activo" de hasta 3 para duelos.
 */
public class Entrenador implements Cloneable, IClasificable {
    private String nombre;
    private double creditos = 0;
    private HashMap<String,Pokemon> pokemones = new HashMap<>();
    private Queue<Pokemon> equipoActivo = new LinkedList<>();
    private IHechizo hechizo;

    
    /**
     * Constructor que inicializa un entrenador con nombre y creditos iniciales.
     * El nombre se almacena en mayusculas y el hechizo se inicializa como nulo.
     * 
     * @param nombre Nombre del entrenador
     * @param creditosIniciales Cantidad inicial de creditos
     */
    
    public Entrenador(String nombre, int creditosIniciales) {
        this.nombre = nombre.toUpperCase();
        this.creditos = creditosIniciales;
        this.hechizo=null;
    }
    
    
    /**
     * Constructor que inicializa un entrenador con nombre, creditos iniciales y un hechizo.
     * El nombre se almacena en mayusculas.
     * 
     * @param nombre Nombre del entrenador
     * @param creditosIniciales Cantidad inicial de creditos
     * @param hechizo Hechizo inicial del entrenador
     */
    
    
    public Entrenador(String nombre, int creditosIniciales,IHechizo hechizo) {
        this.nombre = nombre.toUpperCase();
        this.creditos = creditosIniciales;
        this.hechizo=hechizo;
    }
    

	public void setHechizo(IHechizo hechizo) {
		this.hechizo = hechizo;
	}
	
    public String getNombre() {
    	return this.nombre; 
    }
    
    public double getCreditos() {
    	return this.creditos;
    }
    public void addCreditos(double cant) {
    	this.creditos += cant;
    }
    public void subCreditos(double cant) {
    	this.creditos-=cant;
    }
    
    public Queue<Pokemon> getEquipoActivo(){
    	return this.equipoActivo;
    }    
    public HashMap<String,Pokemon> getPokemones(){
    	return this.pokemones;
    }
   
   
    /**
     * Implementacion del metodo getCategoria de la interfaz IClasificable.
     * Calcula y devuelve la categoria del entrenador, que es la suma de las categorias
     * de todos sus Pokemones.
     * 
     * @return La suma de las categorias de todos los Pokemones del entrenador
     */
    
   
	@Override
    public int getCategoria() { //Calcula y devuelve la categoria del entrenador, que es la suma de las categorias de todos sus Pokemones.
        int suma = 0;
        for (Pokemon p : this.pokemones.values()) {
            suma += p.getCategoria();
        }
        return suma;
    }
	
	public void putPokemon(Pokemon p) throws NombreUtilizadoException {
		if (this.pokemones.containsKey(p.getNombre()))
			throw new NombreUtilizadoException(p.getNombre());
		else	
			this.pokemones.put(p.getNombre(), p);
	}
	
	// Precondicion seleccion nunca puede ser null
    public void agregarPokemonEquipo(String seleccion)throws EquipoLlenoException  {
    	seleccion=seleccion.toUpperCase();
    	if(equipoActivo.size()<3) {
	        try {
				equipoActivo.add(buscaPokemon(seleccion));
			} catch (PokemonNoExisteException e) {
				System.out.println("El pokemon "+e.getNombre()+" no existe");
			} //aniado a la cola de donde la arena sacara los pokemones para pelear
    	}
    	else
    		throw new EquipoLlenoException();
    }
    
    public void setEquipo(String p1, String p2, String p3) {
    	try {
    		agregarPokemonEquipo(p1);
    		agregarPokemonEquipo(p2);
    		agregarPokemonEquipo(p3);
    	} catch (EquipoLlenoException e) {
			System.out.println("El equipo actual esta lleno, no se pueden mas pokemones");
		}
    }
    
    public void setEquipo(String p) {
    	try {
    		agregarPokemonEquipo(p);
    	}catch (EquipoLlenoException e) {
			System.out.println("El equipo actual esta lleno, no se pueden mas pokemones");
		}
    }
    
    public void setEquipo(String p1, String p2) {
    	try {
    		agregarPokemonEquipo(p1);
    		agregarPokemonEquipo(p2);

    	}catch (EquipoLlenoException e) {
			System.out.println("El equipo actual esta lleno, no se pueden mas pokemones");
		}
    }
    
    public Pokemon buscaPokemon(String nombre) throws PokemonNoExisteException {
    	Pokemon p = null;
    	nombre=nombre.toUpperCase();
    	if (this.pokemones.containsKey(nombre))
    		p =this.pokemones.get(nombre);
    	else
    		throw new PokemonNoExisteException(nombre);
    	return p;
    }
    
    public boolean tienePokemonesActivos() { 
        return !equipoActivo.isEmpty(); 
    }
    public Pokemon proximoPokemon() {
        return equipoActivo.poll();
    }
    
    public void vaciarEquipoActivo() {
    	this.equipoActivo.clear();
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

     
    
    
    @Override
	public String toString() {
		return "Entrenador [nombre=" + nombre + ", creditos=" + creditos + ", pokemones=" + pokemones
				+ ", equipoActivo=" + equipoActivo + "]";
	}


}
