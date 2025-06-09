package modelo;

import java.io.Serializable;

/**
 * Clase base para representar una arena en el sistema.
 * Cada arena tiene un identificador único.
 * 
 * <br><b>Invariante de clase:</b><br>
 * - El ID debe ser un número positivo
 */
public class Arena implements Serializable {
	private static final long serialVersionUID = 1L;
    private int id;

    /**
     * Constructor de una nueva arena.
     * 
     * @param id Identificador único de la arena
     */
    public Arena(int id) {
        assert id >= 0 : "El ID de la arena debe ser un número positivo";
        this.id = id;
    }

    /**
     * @return El identificador único de la arena
     */
    public int getId() {
        return id;
    }
} 