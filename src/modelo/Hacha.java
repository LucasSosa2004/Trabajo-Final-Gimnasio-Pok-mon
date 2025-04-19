package modelo;

import java.util.Random;

public class Hacha extends Arma {

	public Hacha() {
		this.costo=80;
	}
	
	@Override
	public void atacar(Pokemon adversario) {
		Random random = new Random();
		adversario.recibeDano(random.nextDouble(50,150));
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new InternalError("uwunocloneanalble");
	}

}
 