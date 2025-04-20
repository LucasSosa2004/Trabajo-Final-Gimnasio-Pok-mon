package modelo;

public abstract class Pokemon implements Cloneable,IHostil, IValuable, IHechizable,IClasificable{
	protected String nombre;
	protected double vitalidad;
	protected double escudo;
	protected double fuerza;
	protected double costo;
	protected int experiencia;
	
	
	
	
	protected abstract void recibeDano(double danoRecibido);
	protected abstract void recargar();  

	public int getCategoria() {
		return this.experiencia;
	}
	
	public void recibeExp() {
		this.experiencia++;
	}
	
	public double getCosto() {
		return this.costo;
	}
	@Override
	public String toString() {
		return " [nombre=" + nombre + ", vitalidad=" + vitalidad + ", escudo=" + escudo + ", fuerza=" + fuerza
				+ ", costo=" + costo + ", experiencia=" + experiencia + "]";
	}
	
	

} 
  