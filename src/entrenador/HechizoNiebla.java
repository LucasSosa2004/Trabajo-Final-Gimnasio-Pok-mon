package entrenador;

import interfaces.IHechizable;
import interfaces.IHechizo;

/**
 * Hechizo que reduce la vitalidad del objetivo a la mitad.
 */
public class HechizoNiebla implements IHechizo {

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
