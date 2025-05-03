package pokemones;

import armas.Arma;
import excepciones.PokemonNoPuedeUsarArmaE;
import interfaces.IClasificable;
import interfaces.IHechizable;
import interfaces.IHostil;
import interfaces.IValuable;

public abstract class Pokemon implements Cloneable,IHostil, IValuable, IHechizable,IClasificable{
	public String nombre;
	public double vitalidad;
	public double escudo;
	public double fuerza;
	public double costo;
	public int experiencia;
	
	
	
	
	public abstract void recibeDano(double danoRecibido);
	public abstract void recargar();  
	public abstract void setArma(Arma a) throws PokemonNoPuedeUsarArmaE;
	
	
	
	public int getCategoria() {
		return this.experiencia;
	}
	/**
     * Incrementa la experiencia del Pokemon en 1.
     */
	public void recibeExp() {
		this.experiencia++;
	}
	
	public double getCosto() {
		return this.costo;
	}
	public double getVitalidad() {
		return this.vitalidad;
	}
	public String getNombre() {
		return this.nombre;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@Override
	public String toString() {
		return " [nombre=" + nombre + ", vitalidad=" + vitalidad + ", escudo=" + escudo + ", fuerza=" + fuerza
				+ ", costo=" + costo + ", experiencia=" + experiencia + "]";
	}
	
} 
  