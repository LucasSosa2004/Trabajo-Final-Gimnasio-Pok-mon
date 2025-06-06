package armas;

import interfaces.IHostil;
import interfaces.IValuable;
import pokemones.Pokemon;
import java.io.Serializable;

public abstract class Arma implements IHostil, IValuable,Cloneable,Serializable {
	
	protected int costo;
	private static final long serialVersionUID = 1L;


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
