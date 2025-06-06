package entrenador;

import interfaces.IHechizable;
import interfaces.IHechizo;
import java.io.Serializable;

/**
 * Hechizo que reduce el escudo y/o la fuerza del objetivo.
 */
public class HechizoTormenta implements IHechizo, Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * Aplica el efecto de tormenta al objeto hechizable.
     * 
     * Pre: El objeto hechizable no puede ser nulo.
     * 
     * @param hechizable El objeto sobre el que se lanza el hechizo
     */
    @Override
    public void hechizar(IHechizable hechizable) {
        assert hechizable != null : "El objeto hechizable no puede ser nulo";
        hechizable.recibeTormenta();
    }
}
