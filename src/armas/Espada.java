package armas;

import pokemones.Pokemon;

public class Espada extends Arma {

	protected double dano;

	
	public Espada() {
		super();
		this.costo=50;
		this.dano=100;
	}
	
	public double getDano() {
		return dano;
	}
	
	@Override
	public void atacar(Pokemon adversario) {
		adversario.recibeDano(this.dano);

	}

	@Override
	public Object clone()  {
		
		try {
			Espada nArma;
			return nArma = (Espada)super.clone();
		} catch (CloneNotSupportedException e) {
			//no entra nunca aca
			return null;
		}
		
	}

	@Override
	public String toString() {
		return "Espada []"+super.toString();
	}
	
}
