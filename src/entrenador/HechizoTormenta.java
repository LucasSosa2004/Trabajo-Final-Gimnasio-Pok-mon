package entrenador;

import interfaces.IHechizable;
import interfaces.IHechizo;

public class HechizoTormenta implements IHechizo {

	@Override
	public void hechizar(IHechizable hechizable) {
		hechizable.recibeTormenta();

	}

}
