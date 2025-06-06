package excepciones;

import java.io.Serializable;

public class EntrenadorException extends Exception implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la excepcion EntrenadorException.
	 * 
	 * @param message Mensaje de la excepcion.
	 */
	public EntrenadorException(String message) {
		super(message);
	}
	
	
	

}
