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
import java.io.Serializable;


/**
 * Cada Entrenador tiene un nombre, creditos, una lista de pokemones
 * y un "equipo activo" de hasta 3 para duelos.
 */
public class Entrenador implements Cloneable, IClasificable, Serializable {
	private static final long serialVersionUID = 1L;
    private String nombre;
    private double creditos = 0;
    private HashMap<String,Pokemon> pokemones = new HashMap<>();
    private Queue<Pokemon> equipoActivo = new LinkedList<>();
    private IHechizo hechizo;

    /**
     * Invariante de clase:
     * - El nombre no puede ser nulo ni vacio.
     * - Los creditos deben ser mayores o iguales a 0.
     */
    private void verificarInvariante() {
        assert nombre != null && !nombre.isEmpty() : "El nombre no puede ser nulo o vacio";
        assert creditos >= 0 : "Los creditos no pueden ser negativos";
    }
    
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
        verificarInvariante();
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
        verificarInvariante();
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
    public int getCategoria() {
        int suma = 0;
        for (Pokemon p : this.pokemones.values()) {
            suma += p.getCategoria();
        }
        return suma;
    }
	
    /**
     * Aniade un Pokemon a la lista de pokemones del entrenador.
     * 
     * <br><b>Pre:</b><br> El Pokemon no puede ser nulo.
     * 
     * <br><b>Postcondicion:</b><br>
     * - El Pokemon se agrega a la lista si su nombre no esta en uso.
     * 
     * @param p El Pokemon a aniadir
     * @throws NombreUtilizadoException Si ya existe un Pokemon con el mismo nombre
     */
	public void putPokemon(Pokemon p) throws NombreUtilizadoException {
        assert p != null : "El Pokemon no puede ser nulo";
		if (this.pokemones.containsKey(p.getNombre()))
			throw new NombreUtilizadoException(p.getNombre());
		else	
			this.pokemones.put(p.getNombre(), p);
	}
	
    /**
     * Aniade un Pokemon al equipo activo del entrenador.
     * 
     * <br><b>Pre:</b><br> seleccion no puede ser null.
     * 
     * <br><b>Postcondicion:</b><br>
     * - El Pokemon se aniade al equipo activo si hay espacio disponible.
     * 
     * @param seleccion Nombre del Pokemon a aniadir
     * @throws EquipoLlenoException Si el equipo activo ya tiene 3 Pokemones
     */
    public void agregarPokemonEquipo(String seleccion)throws EquipoLlenoException  {
        assert seleccion != null && !seleccion.isEmpty() : "El nombre del Pokemon no puede ser nulo o vacio";
    	seleccion=seleccion.toUpperCase();
    	if(equipoActivo.size()<3) {
	        try {
				equipoActivo.add(buscaPokemon(seleccion));
			} catch (PokemonNoExisteException e) {
				System.out.println("El pokemon "+e.getNombre()+" no existe");
			} 
    	}
    	else
    		throw new EquipoLlenoException();
    }
    /**
     * Setea el equipo activo del entrenador.
     * 
     * Pre: p1,p2,p3 no pueden ser null.
     * 
     * Postcondicion:
     * - Los pokemones se aniaden al equipo activo si hay espacio disponible.
     * 
     * @param p1 Nombre del Pokemon a aniadir
     * @param p2 Nombre del Pokemon a aniadir
     * @param p3 Nombre del Pokemon a aniadir
     */
    
    public void setEquipo(String p1, String p2, String p3) {
    	try {
    		agregarPokemonEquipo(p1);
    		agregarPokemonEquipo(p2);
    		agregarPokemonEquipo(p3);
    	} catch (EquipoLlenoException e) {
			System.out.println("El equipo actual esta lleno, no se pueden mas pokemones");
		}
    }
    /**
     * Setea el equipo activo del entrenador.
     * 
     * Pre: p1 y p2 no pueden ser null.
     * 
     * Postcondicion:
     * - Los pokemones se aniaden al equipo activo si hay espacio disponible.
     * 
     * @param p1 Nombre del Pokemon a aniadir
     * @param p2 Nombre del Pokemon a aniadir
     */
    
    public void setEquipo(String p1, String p2) {
    	try {
    		agregarPokemonEquipo(p1);
    		agregarPokemonEquipo(p2);

    	}catch (EquipoLlenoException e) {
			System.out.println("El equipo actual esta lleno, no se pueden mas pokemones");
		}
    }
    /**
     * Setea el equipo activo del entrenador.
     * 
     * Pre: p1 no puede ser null.
     * 
     * Postcondicion:
     * - El pokemon se aniade al equipo activo si hay espacio disponible.
     * 
     * @param p Nombre del Pokemon a aniadir
     */
    
    public void setEquipo(String p) {
    	try {
    		agregarPokemonEquipo(p);
    	}catch (EquipoLlenoException e) {
			System.out.println("El equipo actual esta lleno, no se pueden mas pokemones");
		}
    }

    
    /**
     * Busca un Pokemon por su nombre en la lista de Pokemones del entrenador.
     * 
     * Pre: El nombre no puede ser nulo ni vacio.
     * 
     * Postcondicion:
     * - Devuelve el Pokemon si existe en la lista.
     * 
     * @param nombre Nombre del Pokemon a buscar
     * @return El Pokemon encontrado
     * @throws PokemonNoExisteException Si el Pokemon no existe en la lista
     */
    public Pokemon buscaPokemon(String nombre) throws PokemonNoExisteException {
        assert nombre != null && !nombre.isEmpty() : "El nombre no puede ser nulo o vacio";
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
    
     /**
     * Vacia el equipo activo del entrenador.
     * 
     * Postcondicion:
     * - El equipo activo queda vacio.
     */
    public void vaciarEquipoActivo() {
    	this.equipoActivo.clear();
        assert this.equipoActivo.isEmpty() : "El equipo activo no se vacio correctamente";
    }
    
    /**
     * Lanza un hechizo sobre un objeto hechizable.
     * 
     * Pre: El objeto hechizable no puede ser nulo.
     * 
     * @param hechizable El objeto sobre el que se lanza el hechizo
     */
    public void hechizar(IHechizable hechizable)  {
        assert hechizable != null : "El objeto hechizable no puede ser nulo";
    	if(this.hechizo != null)
    		hechizo.hechizar(hechizable);
    }

    /**
     * Realiza una clonacion profunda del entrenador.
     * 
     * Postcondicion:
     * - Devuelve una nueva instancia de Entrenador con los mismos atributos,
     *   incluyendo copias de los Pokemones y el equipo activo.
     * 
     * @return Una copia profunda del entrenador
     * @throws CloneNotSupportedException Si algun Pokemon o arma no es clonable
     */
    @Override
    public Entrenador clone() throws CloneNotSupportedException {
        Entrenador copia = (Entrenador) super.clone();
        copia.pokemones = new HashMap<>();
        for (Pokemon p : this.pokemones.values()) {
            try {
                copia.pokemones.put(p.getNombre(),(Pokemon) p.clone());
            } catch (CloneNotSupportedException ex) {
                throw new CloneNotSupportedException("No se pudo clonar al PokÃ©mon " + p.getNombre());
            }
        }

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