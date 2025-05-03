package entrenador;

import interfaces.IHechizable;
import interfaces.IHechizo;

/**
 * Hechizo que reduce la vitalidad del objetivo en un 50%.
 */
public class HechizoViento implements IHechizo {

    /**
     * Aplica el efecto de viento al objeto hechizable.
     * 
     * Pre: El objeto hechizable no puede ser nulo.
     * 
     * @param hechizable El objeto sobre el que se lanza el hechizo
     */
    @Override
    public void hechizar(IHechizable hechizable) {
        assert hechizable != null : "El objeto hechizable no puede ser nulo";
        hechizable.recibeViento();
    }
}
