package modelo;

public abstract class Arma implements IHostil, Cloneable{
	
	private int costo;
	 
	protected abstract String getNombre();
}
