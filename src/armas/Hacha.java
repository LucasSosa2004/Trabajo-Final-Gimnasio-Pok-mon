package armas;

import java.util.Random;

import pokemones.Pokemon;

public class Hacha extends Arma {

	public Hacha() {
		super();
		this.costo=80;
	}
	
	@Override
	public void atacar(Pokemon adversario) {
		Random random = new Random();
		adversario.recibeDano(random.nextDouble(50,150));
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	@Override
	public String toString() {
		return "Hacha []"+super.toString();
	}
	
	
}
 