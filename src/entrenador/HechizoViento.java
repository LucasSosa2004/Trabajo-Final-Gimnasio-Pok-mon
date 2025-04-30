package entrenador;

import interfaces.IHechizable;
import interfaces.IHechizo;

public class HechizoViento implements IHechizo {

	@Override
	public void hechizar(IHechizable hechizable) {
		hechizable.recibeViento();
	}

}
