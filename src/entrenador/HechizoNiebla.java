package entrenador;

import interfaces.IHechizable;
import interfaces.IHechizo;

public class HechizoNiebla implements IHechizo {

	@Override
	public void hechizar(IHechizable hechizable) {
		hechizable.recibeNiebla();
	}

}
