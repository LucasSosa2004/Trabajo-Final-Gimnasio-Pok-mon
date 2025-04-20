package modelo;
import java.util.*;


/**
 * Cada Entrenador tiene un nombre, créditos, una lista de pokémons
 * y un "equipo activo" de hasta 3 para duelos.
 */
public class Entrenador implements Cloneable, IClasificable {
    private String nombre;
    private int creditos = 0;
    private List<Pokemon> pokemones = new ArrayList<>();
    private Queue<Pokemon> equipoActivo = new LinkedList<>();

    public Entrenador(String nombre, double creditosIniciales) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede ser vacío");
        this.nombre = nombre;
        this.creditos = creditosIniciales;
    }
    
  


    // ——— Getters / Setters ———
    public String getNombre() {
    	return this.nombre; 
    }
    public double getCreditos() {
    	return this.creditos;
    }
   
    @Override
    public int getCategoria() { //Calcula y devuelve la “categoría” del entrenador, que es la suma de las categorías de todos sus Pokémones.
        int suma = 0;
        for (int i = 0; i < pokemones.size(); i++) {
            suma += pokemones.get(i).getCategoria();
        }
        return suma;
    }
    //gimnasio.getEntrenador(a).comprarPokemon(gimnasio.getTienda());
    // ——— Gestión de compras ———
    public void comprarPokemon(Pokemon p, Tienda tienda) throws CompraImposibleException {
        double costo = p.getCosto();
        if (this.creditos < costo)
            throw new CompraImposibleException(this.creditos, costo);
        this.creditos -= costo;
        this.pokemones.add(p); //añado el polemon a la lista pokemones del entrenador
        // podríamos avisar a la tienda: tienda.registrarVenta(p);
    }
    public void comprarArma(PokemonPiedra p, Arma a, Tienda tienda) throws CompraImposibleException {
        double costo = a.getCosto();
        if (this.creditos < costo)
            throw new CompraImposibleException(this.creditos, costo);
        this.creditos -= costo;
        p.setArma(a);
        // tienda.registrarVenta(a);
    }

    // ——— Selección y gestión de equipo activo ———
    public void seleccionarEquipo(String seleccion)  {
    	if(equipoActivo.size()!=3) {
	        if (seleccion == null)
	            throw new IllegalArgumentException("Debe seleccionar al menos un pokémon");

	        equipoActivo.addAll(seleccion); //añado a la cola de donde la arena sacara los pokemones para pelear
    	}
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

    // ——— Lanzamiento de hechizos (Double Dispatch) ———
    public void lanzarHechizo(IHechizo hechizo, Entrenador rival) {
        if (hechizo == null) return;
        // aplicamos al equipo completo (podrías cambiar a solo activos)
        for (Pokemon p : rival.pokemones) {
            hechizo.hechizar(p);
        }
    }

    // ——— Clonación profunda según reglas ———
    @Override
    public Entrenador clone() throws CloneNotSupportedException {
        Entrenador copia = (Entrenador) super.clone();
        copia.pokemones = new ArrayList<>();
        for (Pokemon p : this.pokemones) {
            try {
                copia.pokemones.add((Pokemon) p.clone());
            } catch (CloneNotSupportedException ex) {
                // Si algún Pokémon no es clonable, abortamos toda la clonación
                throw new CloneNotSupportedException(
                    "No se pudo clonar al Pokémon " + p.getNombre()
                );
            }
        }
        // clonamos también el equipo activo (en base a la nueva lista)
        copia.equipoActivo = new LinkedList<>();
        for (Pokemon p : this.equipoActivo) {
            // buscamos el clon correspondiente por posición o por nombre
            for (Pokemon pc : copia.pokemones) {
                if (pc.getNombre().equals(p.getNombre())) {
                    copia.equipoActivo.add(pc);
                    break;
                }
            }
        }
        return copia;
    }
}
