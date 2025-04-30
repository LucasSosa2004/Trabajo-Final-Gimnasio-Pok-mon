package armas;

import pokemones.Pokemon;

public class Espada extends Arma {

	public Espada() {
		this.costo=50;
	}
	
	@Override
	public void atacar(Pokemon adversario) {
		adversario.recibeDano(100);

	}

	@Override
	public Object clone()  {
		Espada nArma=null;
		try {
			nArma = (Espada)super.clone();
		} catch (CloneNotSupportedException e) {
			
		}
		return nArma;
	}

	@Override
	public String toString() {
		return "Espada []"+super.toString();
	}
	
}
