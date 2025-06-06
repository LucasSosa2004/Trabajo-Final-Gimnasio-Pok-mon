package excepciones;

import java.io.Serializable;

public class EquipoLlenoException extends EntrenadorException implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor de la excepcion EquipoLlenoException.
	 * 
	 * 
	 */
	public EquipoLlenoException() {
		super("La cola esta llena");
		// TODO Auto-generated constructor stub
	}
	

}
