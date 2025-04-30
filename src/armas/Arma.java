package armas;

import interfaces.IHostil;
import interfaces.IValuable;
import pokemones.Pokemon;

public abstract class Arma implements IHostil, IValuable,Cloneable{
	
	protected int costo;

	@Override
	public void atacar(Pokemon adversario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		 throw new InternalError();
	}

	@Override
	public String toString() {
		return " [costo=" + costo + "]";
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}
	
	
}
