package armas;

import interfaces.IHostil;
import interfaces.IValuable;
import pokemones.Pokemon;

public abstract class Arma implements IHostil, IValuable,Cloneable{
	
	protected int costo;



	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return " [costo=" + costo + "]";
	}

	public double getCosto() {
		return costo;
	}
	
	
}
