package modelo;

public abstract class Pokemon implements Cloneable,IHostil, IValuable, IHechizable,IClasificable{
	protected String nombre;
	protected int vitalidad;
	protected int escudo;
	protected int fuerza;
	protected int costo;
	protected int experiencia;
	
	
	
	protected abstract void recibeDano(int danoRecibido); 
	
	

} 
  