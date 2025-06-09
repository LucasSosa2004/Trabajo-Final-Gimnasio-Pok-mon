package entrenador;

import interfaces.IHechizable;
import interfaces.IHechizo;
import java.io.Serializable;

/**
 * Hechizo que reduce la vitalidad o fuerza del objetivo.
 */
public class HechizoNiebla implements IHechizo, Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * Aplica el efecto de niebla al objeto hechizable.
     * 
     * Pre: El objeto hechizable no puede ser nulo.
     * 
     * @param hechizable El objeto sobre el que se lanza el hechizo
     */
    @Override
    public void hechizar(IHechizable hechizable) {
        assert hechizable != null : "El objeto hechizable no puede ser nulo";
        hechizable.recibeNiebla();
    }
}
